package exercise1and2;


import exercise1and2.exceptions.PartnerAlreadyRegisteredException;
import exercise1and2.exceptions.PartnerDoesNotExistException;
import exercise1and2.models.*;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/prova-java/partners")
public class PartnerController {

    private static List<Partner> partners = new ArrayList<>();
    private static List<PartnerCampaign> partnerCampaigns = new ArrayList<>();

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    @RequestMapping(value = "/init", method = POST)
    @ResponseBody
    public String init() throws ParseException {
        partners.add(
                new Partner(1, "Thiago Bardella", "thiago.bardella@gmail.com", formatter.parse("22-05-1990"))
        );
        partners.add(
                new Partner(2, "Jane Doe", "jane.doe@gmail.com", formatter.parse("01-01-1990"))
        );
        return "partners added!!";
    }

    @GetMapping("/{id}/campaigns")
    @ResponseBody
    public List<Campaign> getAllCampaigns(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) return new ArrayList<>();
        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
        if (partnerCampaign == null) return new ArrayList<>();
        return partnerCampaign.getCampaigns();
    }

    @GetMapping("/{id}/new")
    @ResponseBody
    public List<Campaign> getNewCampaigns(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) return new ArrayList<>();
        List<Campaign> availableCampaigns = CampaignController.campaigns.stream()
                .filter(campaign -> campaign.isActiveAfter(new Date()) &&
                        campaign.getTeamId() == partner.getTeamId())
                .collect(Collectors.toList());

        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
        if (partnerCampaign != null) availableCampaigns.removeAll(partnerCampaign.getCampaigns());
        return availableCampaigns;
    }

    @PostMapping("/{id}/join")
    @ResponseBody
    public List<Campaign> join(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) return new ArrayList<>();
        List<Campaign> availableCampaigns =
                CampaignController.campaigns.stream()
                        .filter(campaign -> campaign.isActiveAfter(new Date()) &&
                                campaign.getTeamId() == partner.getTeamId())
                        .collect(Collectors.toList());
        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
        if (partnerCampaign == null) {
            partnerCampaign = new PartnerCampaign(partner, new ArrayList<>());
        }

        List<Campaign> joinedCampaigns = partnerCampaign.getCampaigns();
        for (Campaign availableCampaign : availableCampaigns) {
            if (!joinedCampaigns.contains(availableCampaign))
                joinedCampaigns.add(availableCampaign);
        }
        partnerCampaigns.add(partnerCampaign);

        return partnerCampaign.getCampaigns();
    }

    //TODO (criar API que descobre se um partner tem novas campanhas)
    @PostMapping(value = "/add")
    @ResponseBody
    //TODO (varificar se posso trocar por um ResponseEntity)
    public List<Campaign> add(@RequestBody PartnerInput partnerInput) {
        // TODO (create exception handler)
        Partner partner = getPartnerBy(partnerInput.getEmail());
        if (partner != null) {
            PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
            if (partnerCampaign != null) {
                if (partnerCampaign.getCampaigns().isEmpty()) {
                    List<Campaign> activeCampaigns = CampaignController.campaigns.stream().filter(campaign -> campaign.isActiveAfter(new Date())).collect(Collectors.toList());
                }
                else {
                    List<Campaign> campaignsAlreadyAssigned = partnerCampaign.getCampaigns();
                }
            } else {
                List<Campaign> activeCampaigns = CampaignController.campaigns.stream().filter(campaign -> campaign.isActiveAfter(new Date())).collect(Collectors.toList());
            }
            throw new PartnerAlreadyRegisteredException();
        }

        Partner newPartner = new Partner(   partnerInput.getTeamId(),
                                            partnerInput.getFullName(),
                                            partnerInput.getEmail(),
                                            partnerInput.getBirthDate());
        partners.add(newPartner);
        List<Campaign> availableCampaigns =
                CampaignController.campaigns.stream()
                        .filter(campaign -> campaign.isActiveAfter(new Date()) &&
                                            campaign.getTeamId() == newPartner.getTeamId())
                        .collect(Collectors.toList());
        return availableCampaigns;
    }

    private Partner getPartnerBy(String email) {
        for (Partner partner : partners) {
            if (partner.getEmail().equals(email)) return partner;
        }
        return null;
    }

    private Partner getPartnerBy(int id) {
        for (Partner partner : partners) {
            if (partner.getId() == id) return partner;
        }
        return null;
    }

    private PartnerCampaign getPartnerCampaignBy(Partner partner) {
        for (PartnerCampaign partnerCampaign : partnerCampaigns) {
            if (partnerCampaign.getPartner() == partner) return partnerCampaign;
        }
        return null;
    }

    //TODO (tratar ParseException)
    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public Partner update(
            @RequestParam(value = "full_name", required = false) String fullName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "birth_date", required = false) String birthDateStr,
            @PathVariable int id
    ) throws ParseException {
        Partner partnerToUpdate = getPartnerBy(id);
        if (partnerToUpdate == null) throw new PartnerDoesNotExistException();

        if (fullName != null) partnerToUpdate.setFullName(fullName);
        if (email != null) partnerToUpdate.setEmail(email);
        if (birthDateStr != null) partnerToUpdate.setBirthDate(formatter.parse(birthDateStr));

        return partnerToUpdate;
    }

    //TODO (como devolver uma mensagem para o usuário no formato JSON?)
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        Partner partnerToDelete = getPartnerBy(id);
        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partnerToDelete);
        partners.remove(partnerToDelete);
        if (partnerCampaign != null) partnerCampaigns.remove(partnerCampaign);
        return "Campaign " + id + " deleted!!";
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<Partner> all() {
        return partners;
    }


}
