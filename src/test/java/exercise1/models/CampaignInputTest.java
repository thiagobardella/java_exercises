package exercise1.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exercise1.deserializer.GsonCampaignInputDeserializer;
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

        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CampaignInput.class, new GsonCampaignInputDeserializer());
        Gson gson = gsonBuilder.create();

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