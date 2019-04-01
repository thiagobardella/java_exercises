package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import exercise1and2.models.Campaign;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JsonPartnerDeserializer extends StdDeserializer<Campaign> {

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    public JsonPartnerDeserializer() {
        this(null);
    }

    public JsonPartnerDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Campaign deserialize(
            JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);

        int id = node.get("id").asInt();
        int teamId = node.get("team_id").asInt();
        String name = node.get("name").asText();
        String startDate = node.get("start_date").asText();
        String endDate = node.get("end_date").asText();
        try {
            return new Campaign(teamId, name, formatter.parse(startDate), formatter.parse(endDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}