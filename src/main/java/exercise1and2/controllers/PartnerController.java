package exercise1and2.controllers;


import exercise1and2.exceptions.PartnerAlreadyRegisteredException;
import exercise1and2.exceptions.PartnerNotFoundException;
import exercise1and2.models.*;
import exercise1and2.utils.DateUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/prova-java/partners")
public class PartnerController {

    private static List<Partner> partners = new ArrayList<>();
    private static List<PartnerCampaign> partnerCampaigns = new ArrayList<>();

    @PostMapping("/init")
    @ResponseBody
    public List<Partner> init() throws ParseException {
        Partner newPartner1 = new Partner(1, "Thiago Bardella", "thiago.bardella@gmail.com", DateUtils.formatter.parse("22-05-1990"));
        partners.add(newPartner1);
        Partner newPartner2 = new Partner(2, "Jane Doe", "jane.doe@gmail.com", DateUtils.formatter.parse("01-01-1990"));
        partners.add(newPartner2);
        return partners;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Partner getPartner(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) throw new PartnerNotFoundException();
        return partner;
    }

    @GetMapping("/{id}/campaigns")
    @ResponseBody
    public List<Campaign> getCampaigns(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) throw new PartnerNotFoundException();
        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
        if (partnerCampaign == null) return new ArrayList<>();
        return partnerCampaign.getCampaigns();
    }

    @GetMapping("/{id}/new")
    @ResponseBody
    public List<Campaign> getNewCampaigns(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) throw new PartnerNotFoundException();
        List<Campaign> availableCampaigns = getAvailableActiveCampaigns(partner);

        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
        if (partnerCampaign != null) availableCampaigns.removeAll(partnerCampaign.getCampaigns());
        return availableCampaigns;
    }

    @PostMapping("/{id}/join")
    @ResponseBody
    public List<Campaign> join(@PathVariable int id) {
        Partner partner = getPartnerBy(id);
        if (partner == null) throw new PartnerNotFoundException();
        List<Campaign> availableCampaigns = getAvailableActiveCampaigns(partner);
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

    private List<Campaign> getAvailableActiveCampaigns(Partner partner) {
        return CampaignController.campaigns.stream()
                .filter(campaign -> campaign.isActiveAfter(new Date()) &&
                        campaign.getTeamId() == partner.getTeamId())
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public List<Campaign> add(@RequestBody PartnerInput partnerInput) {
        Partner partner = getPartnerBy(partnerInput.getEmail());
        if (partner != null) {
            PartnerCampaign partnerCampaign = getPartnerCampaignBy(partner);
            List<Campaign> activeCampaigns = getAvailableActiveCampaigns(partner);
            if (partnerCampaign != null) {
                if (partnerCampaign.getCampaigns().isEmpty()) {
                    return activeCampaigns;
                }
                return partnerCampaign.getCampaigns();
            }
            return activeCampaigns;
        }

        Partner newPartner = new Partner(   partnerInput.getTeamId(),
                                            partnerInput.getFullName(),
                                            partnerInput.getEmail(),
                                            partnerInput.getBirthDate());
        partners.add(newPartner);

        return getAvailableActiveCampaigns(newPartner);
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

    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public Partner update(
            @RequestParam(value = "full_name", required = false) String fullName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "birth_date", required = false) String birthDateStr,
            @PathVariable int id
    ) throws ParseException {
        Partner partnerToUpdate = getPartnerBy(id);
        if (partnerToUpdate == null) throw new PartnerNotFoundException();

        if (fullName != null) partnerToUpdate.setFullName(fullName);
        if (email != null) partnerToUpdate.setEmail(email);
        if (birthDateStr != null) partnerToUpdate.setBirthDate(DateUtils.formatter.parse(birthDateStr));

        return partnerToUpdate;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        Partner partnerToDelete = getPartnerBy(id);
        if (partnerToDelete == null) throw new PartnerNotFoundException();
        PartnerCampaign partnerCampaign = getPartnerCampaignBy(partnerToDelete);
        partners.remove(partnerToDelete);
        if (partnerCampaign != null) partnerCampaigns.remove(partnerCampaign);
        return "Cliente " + id + " excluído!!";
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<Partner> all() {
        return partners;
    }


}
