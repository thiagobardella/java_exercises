package exercise1and2;

import com.google.gson.Gson;
import exercise1and2.controllers.CampaignController;
import exercise1and2.models.CampaignInput;
import exercise1and2.utils.DateUtils;
import exercise1and2.utils.GsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    @Autowired
    private MockMvc mvc;

    private Gson gson = GsonUtils.getCustomGsonCampaignInput();
    private static boolean setUpIsDone = false;

    @Before
    public void setup() throws Exception {
        if (setUpIsDone) {
            return;
        }
        mvc.perform(post("/prova-java/campaigns/init"));
        setUpIsDone = true;
    }

    @Test
    public void shouldReturnOkStatus() throws Exception {
        mvc.perform(get("/prova-java/campaigns/all"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCampaign() throws Exception {
        mvc.perform(get("/prova-java/campaigns/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("first")));
    }

    @Test
    public void shouldNotFindCampaign() throws Exception {
        mvc.perform(get("/prova-java/campaigns/1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Campanha não encontrada!"));
    }

    @Test
    public void shouldAddCampaign() throws Exception {
        CampaignInput campaignInput = new CampaignInput(1, "mock",
                DateUtils.formatter.parse("01-01-2000"),
                DateUtils.formatter.parse("03-01-2000"));

        mvc.perform(post("/prova-java/campaigns/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(campaignInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("mock")));
    }

    @Test
    public void shouldNotAddDuplicatedCampaign() throws Exception {
        CampaignInput campaignInput = new CampaignInput(1, "mock",
                DateUtils.formatter.parse("01-01-2000"),
                DateUtils.formatter.parse("03-01-2000"));

        Gson gson = GsonUtils.getCustomGsonCampaignInput();

        mvc.perform(post("/prova-java/campaigns/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(campaignInput)));

        mvc.perform(post("/prova-java/campaigns/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(campaignInput)))
                .andExpect(status().isOk())
                .andExpect(content().string("Esta campanha já está cadastrada!"));
    }

    @Test
    public void shouldAddCampaignThatUpdatedOtherPeriods() throws Exception {
        CampaignInput campaignInput = new CampaignInput(3, "third",
                DateUtils.formatter.parse("01-01-2019"),
                DateUtils.formatter.parse("03-05-2019"));

        mvc.perform(post("/prova-java/campaigns/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(campaignInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("third")));

        mvc.perform(get("/prova-java/campaigns/allActive"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].name", equalTo("first")))
                .andExpect(jsonPath("$[0].start_date", equalTo("01-01-2019")))
                .andExpect(jsonPath("$[0].end_date", equalTo("05-05-2019")))
//                .andExpect(jsonPath("$[1].name", equalTo("second")))
                .andExpect(jsonPath("$[1].start_date", equalTo("01-01-2019")))
                .andExpect(jsonPath("$[1].end_date", equalTo("04-05-2019")))
//                .andExpect(jsonPath("$[2].name", equalTo("third")))
                .andExpect(jsonPath("$[2].start_date", equalTo("01-01-2019")))
                .andExpect(jsonPath("$[2].end_date", equalTo("03-05-2019")));

    }

    @Test
    public void shouldUpdateCampaign() throws Exception {
        mvc.perform(put("/prova-java/campaigns/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "updated")
                .param("team_id", "200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("updated")))
                .andExpect(jsonPath("$.team_id", equalTo(200)));

        mvc.perform(get("/prova-java/campaigns/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("updated")))
                .andExpect(jsonPath("$.team_id", equalTo(200)));

    }

}