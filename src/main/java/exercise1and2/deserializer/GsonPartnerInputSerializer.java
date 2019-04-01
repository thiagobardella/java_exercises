package exercise1and2.deserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import exercise1and2.models.CampaignInput;
import exercise1and2.models.PartnerInput;
import exercise1and2.utils.DateUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class GsonPartnerInputSerializer implements JsonSerializer<PartnerInput> {

    @Override
    public JsonElement serialize(PartnerInput src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jObject = new JsonObject();
        jObject.addProperty("team_id", src.getTeamId());
        jObject.addProperty("email", src.getEmail());
        jObject.addProperty("full_name", src.getFullName());
        jObject.addProperty("birth_date", DateUtils.formatter.format(src.getBirthDate()));
        return jObject;
    }
}