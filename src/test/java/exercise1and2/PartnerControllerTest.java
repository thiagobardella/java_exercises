package exercise1and2;

import com.google.gson.Gson;
import exercise1and2.models.PartnerInput;
import exercise1and2.utils.DateUtils;
import exercise1and2.utils.GsonUtils;
import org.junit.After;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PartnerControllerTest {

    @Autowired
    private MockMvc mvc;

    private Gson gson = GsonUtils.getCustomGsonPartnerInput();
    private static boolean setUpIsDone = false;

    @Before
    public void setup() throws Exception {
        if (setUpIsDone) {
            return;
        }
        mvc.perform(post("/prova-java/partners/init"));
        mvc.perform(post("/prova-java/campaigns/init"));
        setUpIsDone = true;
    }

    @Test
    public void shouldReturnOkStatus() throws Exception {
        mvc.perform(get("/prova-java/partners/all"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPartner() throws Exception {
        mvc.perform(get("/prova-java/partners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.full_name", equalTo("Thiago Bardella")));
    }

    @Test
    public void shouldNotFindPartner() throws Exception {
        mvc.perform(get("/prova-java/partners/1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado!"));

        mvc.perform(put("/prova-java/partners/update/1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado!"));

        mvc.perform(delete("/prova-java/partners/delete/1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado!"));
    }

    @Test
    public void shouldAssociateCampaign() throws Exception {
        mvc.perform(post("/prova-java/partners/1/join"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("first")));

        mvc.perform(get("/prova-java/partners/1/campaigns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("first")));
    }

    @Test
    public void shouldAddPartner() throws Exception {
        PartnerInput partnerInput = new PartnerInput(1, "New Client",
                "newClient@gmail.com",
                DateUtils.formatter.parse("01-01-2000"));

        mvc.perform(post("/prova-java/partners/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(partnerInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("first")));
    }

    @Test
    public void shouldUpdatePartner() throws Exception {
        mvc.perform(put("/prova-java/partners/update/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("team_id", "200")
                .param("full_name", "New")
                .param("email", "new@gmail.com")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_id", equalTo(200)))
                .andExpect(jsonPath("$.full_name", equalTo("New")))
                .andExpect(jsonPath("$.email", equalTo("new@gmail.com")));

        mvc.perform(get("/prova-java/partners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_id", equalTo(200)))
                .andExpect(jsonPath("$.full_name", equalTo("New")))
                .andExpect(jsonPath("$.email", equalTo("new@gmail.com")));

    }

    @Test
    public void shouldDeletePartner() throws Exception {
        mvc.perform(delete("/prova-java/partners/delete/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente 2 excluído!"));

        mvc.perform(get("/prova-java/partners/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado!"));

    }
}