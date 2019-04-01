package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import exercise1and2.models.CampaignInput;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonCampaignInputSerializer extends StdSerializer<CampaignInput> {

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    public JsonCampaignInputSerializer() {
        this(null);
    }

    public JsonCampaignInputSerializer(Class<CampaignInput> vc) {
        super(vc);
    }

    @Override
    public void serialize(
            CampaignInput value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("team_id", value.getTeamId());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("start_date", formatter.format(value.getStartDate()));
        jgen.writeStringField("end_date", formatter.format(value.getEndDate()));
        jgen.writeEndObject();
    }
}