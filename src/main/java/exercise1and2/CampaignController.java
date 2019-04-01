package exercise1and2;


import exercise1and2.exceptions.CampaignAlreadyRegisteredException;
import exercise1and2.exceptions.CampaignDoesNotExistException;
import exercise1and2.models.Campaign;
import exercise1and2.models.CampaignInput;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/prova-java/campaigns")
public class CampaignController {

    public static List<Campaign> campaigns = new ArrayList<>();

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    @RequestMapping(value = "/init", method = POST)
    @ResponseBody
    public String init() throws ParseException {
        campaigns.add(
                new Campaign(1, "first", formatter.parse("01-01-2019"), formatter.parse("03-05-2019"))
        );
        campaigns.add(
                new Campaign(2, "second", formatter.parse("01-01-2019"), formatter.parse("02-05-2019"))
        );
        return "campaigns added!!";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public Campaign add(@RequestBody CampaignInput campaignInput) {
        List<Campaign> activeCampaigns = campaigns.stream().filter(campaign -> campaign.isActiveAfter(campaignInput.getStartDate())).collect(Collectors.toList());
        // TODO (create exception handler)
        if (campaignAlreadyExists(activeCampaigns, campaignInput.getName(), campaignInput.getTeamId())) {
            throw new CampaignAlreadyRegisteredException();
        }
        validateActiveCampaigns(activeCampaigns);
        Campaign newCampaign = new Campaign(campaignInput.getTeamId(),
                                            campaignInput.getName(),
                                            campaignInput.getStartDate(),
                                            campaignInput.getEndDate());

        campaigns.add(newCampaign);
        return newCampaign;
    }

    private Boolean campaignAlreadyExists(List<Campaign> campaigns, String name, int teamId) {
        for (Campaign campaign : campaigns) {
            if (campaign.getName().equals(name) && campaign.getTeamId() == teamId) return true;
        }
        return false;
    }

    //TODO (tratar ParseException)
    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public Campaign update(
            @RequestParam(value = "team_id", required = false) String teamId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "start_date", required = false) String startDateStr,
            @RequestParam(value = "end_date", required = false) String endDate,
            @PathVariable int id
    ) throws ParseException {
        Campaign campaignToUpdate = getCampaignById(id);
        if (campaignToUpdate == null) throw new CampaignDoesNotExistException();

        //TODO (verificar se tem como passar os campos dentro do payload para PUT request)
//        String teamId = request.getParameter("team_id");
//        String name = request.getParameter("name");
//        String startDateStr = request.getParameter("start_date");
//        String endDate = request.getParameter("end_date");

        if (teamId != null) campaignToUpdate.setTeamId(Integer.valueOf(teamId));
        if (name != null) campaignToUpdate.setName(name);
        //TODO (testar update de datas)
        //TODO (não tenho ctz se todas as campanhas devem ser atualizadas caso uma data de validade mude)
        if (startDateStr != null) {
            Date startDate = formatter.parse(startDateStr);
            campaignToUpdate.setStartDate(startDate);
            List<Campaign> activeCampaigns = campaigns.stream().filter(campaign -> campaign.isActiveAfter(startDate)).collect(Collectors.toList());
            validateActiveCampaigns(activeCampaigns);
        }
        //TODO (qual o impacto de mudarmos a data de fim?)
        if (endDate != null) campaignToUpdate.setStartDate(formatter.parse(endDate));

        return campaignToUpdate;
    }

    private Campaign getCampaignById(int id) {
        for (Campaign campaign : campaigns) {
            if (campaign.getId() == id) return campaign;
        }
        return null;
    }

    //TODO (como devolver uma mensagem para o usuário no formato JSON?)
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        Campaign campaignToDelete = getCampaignById(id);
        campaigns.remove(campaignToDelete);
        return "Campaign " + id + " deleted!!";
    }

    @GetMapping(value = "/allActive")
    @ResponseBody
    public List<Campaign> allActive() {
        return campaigns.stream().filter(Campaign::isActive).collect(Collectors.toList());
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<Campaign> all() {
        return campaigns;
    }

    public void validateActiveCampaigns(List<Campaign> activeCampaigns)
    {
        activeCampaigns.sort(Comparator.comparing(Campaign::getId).reversed());

        for (Campaign activeCampaign : activeCampaigns) {
            activeCampaign.postponeCampaign();
            while (getCountOnPeriod(activeCampaign.getStartDate(), activeCampaign.getEndDate(), activeCampaigns) != 1) {
                activeCampaign.postponeCampaign();
            }
        }
    }

    // TODO (verificar se posso transformar em isUniqueWithPeriod)
    public long getCountOnPeriod(Date fromDate, Date toDate, List<Campaign> campaigns) {
        return campaigns.stream().filter(campaign -> campaign.getStartDate().equals(fromDate) && campaign.getEndDate().equals(toDate)).count();
    }

    //TODO (Verificar necessidade desse método)
    //TODO Transformar fromDate, toDate em uma class Period
    public Campaign getFirstOnPeriod(String fromDate, String toDate, List<Campaign> campaigns) {
        for (Campaign campaign : campaigns) {
            if (fromDate.equals(campaign.getStartDate()) &&
                toDate.equals(campaign.getEndDate())) {
                return campaign;
            }
        }
        return null;
    }

}
