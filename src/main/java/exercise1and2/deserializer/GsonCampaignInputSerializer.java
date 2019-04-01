package exercise1and2.deserializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.*;
import exercise1and2.models.CampaignInput;
import exercise1and2.utils.DateUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GsonCampaignInputSerializer implements JsonSerializer<CampaignInput> {

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public JsonElement serialize(CampaignInput src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jObject = new JsonObject();
        jObject.addProperty("team_id", src.getTeamId());
        jObject.addProperty("name", src.getName());
        jObject.addProperty("start_date", DateUtils.formatter.format(src.getStartDate()));
        jObject.addProperty("end_date", DateUtils.formatter.format(src.getEndDate()));
        return jObject;
    }
}