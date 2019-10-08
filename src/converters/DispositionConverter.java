package converters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import validation.Disposition;

import java.io.IOException;
import java.util.Optional;

public class DispositionConverter extends StdDeserializer<Disposition> {

    public DispositionConverter() {
        super(Number.class);
    }

    @Override
    public Disposition deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int code = p.getValueAsInt();
        Optional<Disposition> disposition = Disposition.valueOf(code);

        return disposition.orElseThrow(() -> new JsonParseException(p, "Was unable to parse Disposition field with value: " + code));
    }
}
