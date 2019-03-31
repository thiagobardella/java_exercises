package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import exercise1and2.models.Campaign;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonCampaignSerializer extends StdSerializer<Campaign> {

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    public JsonCampaignSerializer() {
        this(null);
    }

    public JsonCampaignSerializer(Class<Campaign> vc) {
        super(vc);
    }

    @Override
    public void serialize(
            Campaign value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("team_id", value.getTeamId());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("start_date", formatter.format(value.getStartDate()));
        jgen.writeStringField("end_date", formatter.format(value.getEndDate()));
        jgen.writeEndObject();
    }
}