package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import exercise1and2.models.Partner;
import exercise1and2.utils.DateUtils;

import java.io.IOException;

public class JsonPartnerSerializer extends StdSerializer<Partner> {

    public JsonPartnerSerializer() {
        this(null);
    }

    public JsonPartnerSerializer(Class<Partner> vc) {
        super(vc);
    }

    @Override
    public void serialize(
            Partner value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("team_id", value.getTeamId());
        jgen.writeStringField("full_name", value.getFullName());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeStringField("birth_date", DateUtils.formatter.format(value.getBirthDate()));
        jgen.writeEndObject();
    }
}