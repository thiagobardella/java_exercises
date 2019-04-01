package exercise1and2.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import exercise1and2.models.CampaignInput;
import exercise1and2.utils.DateUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GsonCampaignInputDeserializer implements JsonDeserializer<CampaignInput> {

    @Override
    public CampaignInput deserialize(
            JsonElement jElement, Type typeOfT, JsonDeserializationContext context) {

        JsonObject jObject = jElement.getAsJsonObject();
        int teamId = jObject.get("team_id").getAsInt();
        String name = jObject.get("name").getAsString();
        String startDate = jObject.get("start_date").getAsString();
        String endDate = jObject.get("end_date").getAsString();
        try {
            return new CampaignInput(teamId, name, DateUtils.formatter.parse(startDate), DateUtils.formatter.parse(endDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}