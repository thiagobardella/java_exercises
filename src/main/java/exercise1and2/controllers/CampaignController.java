package exercise1and2.controllers;


import exercise1and2.exceptions.CampaignAlreadyRegisteredException;
import exercise1and2.exceptions.CampaignNotFoundException;
import exercise1and2.models.Campaign;
import exercise1and2.models.CampaignInput;
import exercise1and2.utils.DateUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/prova-java/campaigns")
public class CampaignController {

    static List<Campaign> campaigns = new ArrayList<>();

    @PostMapping("/init")
    @ResponseBody
    public List<Campaign> init() throws ParseException {
        Campaign newCampaign1 = new Campaign(1,1, "first", DateUtils.formatter.parse("01-01-2019"), DateUtils.formatter.parse("03-05-2019"));
        if (!campaigns.contains(newCampaign1)) campaigns.add(newCampaign1);
        Campaign newCampaign2 = new Campaign(2,2, "second", DateUtils.formatter.parse("01-01-2019"), DateUtils.formatter.parse("02-05-2019"));
        if (!campaigns.contains(newCampaign2)) campaigns.add(newCampaign2);
        return campaigns;
    }

    @PostMapping("/add")
    @ResponseBody
    public Campaign add(@RequestBody CampaignInput campaignInput) {
        List<Campaign> activeCampaigns = campaigns.stream().filter(campaign -> campaign.isActiveAfter(campaignInput.getStartDate())).collect(Collectors.toList());
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

    @GetMapping("/allActive")
    @ResponseBody
    public List<Campaign> allActive() {
        return campaigns.stream().filter(Campaign::isActive).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Campaign getCampaign(@PathVariable int id) {
        Campaign campaign = getCampaignById(id);
        if (campaign == null) throw new CampaignNotFoundException();
        return campaign;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        Campaign campaignToDelete = getCampaignById(id);
        if (campaignToDelete == null) throw new CampaignNotFoundException();
        campaigns.remove(campaignToDelete);
        return "Campanha " + id + " excluída!!";
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<Campaign> all() {
        return campaigns;
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Campaign update(
            @RequestParam(value = "team_id", required = false) String teamId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "start_date", required = false) String startDateStr,
            @RequestParam(value = "end_date", required = false) String endDateStr,
            @PathVariable int id
    ) throws ParseException {
        Campaign campaignToUpdate = getCampaignById(id);
        if (campaignToUpdate == null) throw new CampaignNotFoundException();

        if (teamId != null) campaignToUpdate.setTeamId(Integer.valueOf(teamId));
        if (name != null) campaignToUpdate.setName(name);
        if (startDateStr != null) {
            Date startDate = DateUtils.formatter.parse(startDateStr);
            campaignToUpdate.setStartDate(startDate);
            updateActiveCampaigns(startDate);
        }
        if (endDateStr != null) {
            Date endDate = DateUtils.formatter.parse(endDateStr);
            campaignToUpdate.setEndDate(endDate);
            updateActiveCampaigns(campaignToUpdate.getStartDate());
        }

        return campaignToUpdate;
    }

    private void updateActiveCampaigns(Date startDate) {
        List<Campaign> activeCampaigns = campaigns.stream().filter(campaign -> campaign.isActiveAfter(startDate)).collect(Collectors.toList());
        validateActiveCampaigns(activeCampaigns);
    }

    private void validateActiveCampaigns(List<Campaign> activeCampaigns) {
        activeCampaigns.sort(Comparator.comparing(Campaign::getId).reversed());

        for (Campaign activeCampaign : activeCampaigns) {
            activeCampaign.postponeCampaign();
            while (isUniqueWithPeriod(activeCampaign.getStartDate(), activeCampaign.getEndDate(), activeCampaigns)) {
                activeCampaign.postponeCampaign();
            }
        }
    }

    private Campaign getCampaignById(int id) {
        for (Campaign campaign : campaigns) {
            if (campaign.getId() == id) return campaign;
        }
        return null;
    }


    private boolean isUniqueWithPeriod(Date fromDate, Date toDate, List<Campaign> campaigns) {
        return campaigns.stream().filter(campaign -> campaign.getStartDate().equals(fromDate) && campaign.getEndDate().equals(toDate)).count() != 1;
    }
}
