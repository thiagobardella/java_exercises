package exercise1and2.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import exercise1and2.models.Campaign;
import exercise1and2.models.Partner;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonPartnerSerializer extends StdSerializer<Partner> {

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

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
        jgen.writeStringField("birth_date", formatter.format(value.getBirthDate()));
        jgen.writeEndObject();
    }
}