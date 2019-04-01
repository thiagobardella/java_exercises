package exercise1and2.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exercise1and2.deserializer.GsonCampaignInputDeserializer;
import exercise1and2.utils.GsonUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CampaignInputTest {

    @Test
    public void whenDeserializingUsingGsonDeserialize_thenCorrect() {

        SimpleDateFormat formatter
                = new SimpleDateFormat("dd-MM-yyyy");

        String json = "{" +
                "\"team_id\":1," +
                "\"name\":\"third\"," +
                "\"start_date\":\"01-02-2018\"," +
                "\"end_date\":\"05-02-2018\"" +
                "}";

        Gson gson = GsonUtils.getCustomGsonCampaignInput();

        CampaignInput campaignInput = gson.fromJson(json, CampaignInput.class);

        assertEquals("01-02-2018", formatter.format(campaignInput.getStartDate()));
        assertEquals("05-02-2018", formatter.format(campaignInput.getEndDate()));
    }

    @Test
    public void whenDeserializingUsingJsonDeserialize_thenCorrect() throws IOException {

        SimpleDateFormat formatter
                = new SimpleDateFormat("dd-MM-yyyy");

        String json = "{" +
                "\"team_id\":1," +
                "\"name\":\"third\"," +
                "\"start_date\":\"01-02-2018\"," +
                "\"end_date\":\"05-02-2018\"" +
                "}";

        CampaignInput campaignInput = new ObjectMapper().readValue(json, CampaignInput.class);

        assertEquals("01-02-2018", formatter.format(campaignInput.getStartDate()));
        assertEquals("05-02-2018", formatter.format(campaignInput.getEndDate()));
    }

}