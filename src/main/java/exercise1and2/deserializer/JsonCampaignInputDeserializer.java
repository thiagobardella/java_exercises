package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import exercise1and2.models.CampaignInput;
import exercise1and2.utils.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JsonCampaignInputDeserializer extends StdDeserializer<CampaignInput> {

    public JsonCampaignInputDeserializer() {
        this(null);
    }

    public JsonCampaignInputDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CampaignInput deserialize(
            JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);
        int teamId = node.get("team_id").asInt();
        String name = node.get("name").asText();
        String startDate = node.get("start_date").asText();
        String endDate = node.get("end_date").asText();
        try {
            return new CampaignInput(teamId, name, DateUtils.formatter.parse(startDate), DateUtils.formatter.parse(endDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}