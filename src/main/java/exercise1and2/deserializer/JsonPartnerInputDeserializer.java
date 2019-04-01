package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import exercise1and2.models.PartnerInput;
import exercise1and2.utils.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JsonPartnerInputDeserializer extends StdDeserializer<PartnerInput> {

    public JsonPartnerInputDeserializer() {
        this(null);
    }

    public JsonPartnerInputDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PartnerInput deserialize(
            JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);
        int teamId = node.get("team_id").asInt();
        String email = node.get("email").asText();
        String fullName = node.get("full_name").asText();
        String birthDate = node.get("birth_date").asText();
        try {
            return new PartnerInput(teamId, fullName, email, DateUtils.formatter.parse(birthDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}