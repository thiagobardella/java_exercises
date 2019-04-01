package exercise1and2.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import exercise1and2.models.PartnerInput;
import exercise1and2.utils.DateUtils;

import java.lang.reflect.Type;
import java.text.ParseException;

public class GsonPartnerInputDeserializer implements JsonDeserializer<PartnerInput> {

    @Override
    public PartnerInput deserialize(
            JsonElement jElement, Type typeOfT, JsonDeserializationContext context) {

        JsonObject jObject = jElement.getAsJsonObject();
        int teamId = jObject.get("team_id").getAsInt();
        String fullName = jObject.get("full_name").getAsString();
        String email = jObject.get("email").getAsString();
        String birthDate = jObject.get("birth_date").getAsString();
        try {
            return new PartnerInput(teamId, fullName, email, DateUtils.formatter.parse(birthDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}