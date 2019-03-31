package exercise1;


import exercise1.models.Campaign;
import exercise1.models.CampaignInput;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/prova-java")
public class CampaignController {

    private static List<Campaign> campaigns = new ArrayList<>();

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    @RequestMapping(value = "/init", method = POST)
    @ResponseBody
    public String init() throws ParseException {
        campaigns.add(
                new Campaign(1, "first", formatter.parse("01-01-2019"), formatter.parse("02-01-2019"))
        );
        campaigns.add(
                new Campaign(2, "second", formatter.parse("01-01-2019"), formatter.parse("02-01-2019"))
        );
        return "campaigns added!!";
    }

    @RequestMapping(value = "/add", method = POST)
    @ResponseBody
    public Campaign add(@RequestBody CampaignInput campaignInput) {
        List<Campaign> activeCampaigns = campaigns.stream().filter(campaign -> campaign.isActiveOnPeriod(campaignInput.getStartDate(), campaignInput.getEndDate())).collect(Collectors.toList());

        Campaign newCampaign = new Campaign(campaignInput.getTeamId(),
                                            campaignInput.getName(),
                                            campaignInput.getStartDate(),
                                            campaignInput.getEndDate());

        campaigns.add(newCampaign);
        return newCampaign;
    }

    @RequestMapping(value = "/all", method = GET)
    @ResponseBody
    public List<Campaign> all() {
        return campaigns.stream().filter(Campaign::isActive).collect(Collectors.toList());
    }


}
