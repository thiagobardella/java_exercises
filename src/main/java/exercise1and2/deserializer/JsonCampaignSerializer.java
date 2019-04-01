package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import exercise1and2.models.Campaign;
import exercise1and2.utils.DateUtils;

import java.io.IOException;

public class JsonCampaignSerializer extends StdSerializer<Campaign> {

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
        jgen.writeStringField("start_date", DateUtils.formatter.format(value.getStartDate()));
        jgen.writeStringField("end_date", DateUtils.formatter.format(value.getEndDate()));
        jgen.writeEndObject();
    }
}