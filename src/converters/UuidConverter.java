package converters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import utils.FilenameUtils;
import validation.Uuid;

import java.io.IOException;
import java.util.regex.Matcher;

public class UuidConverter extends StdDeserializer<String> {

    public UuidConverter() {
        super(String.class);
    }

    /**
     * Used to convert the incoming UUID back into a string name if it is valid, typically we could convert this
     * into UUID class with special properties.
     *
     * @param p    the parser object.
     * @param ctxt the parser context.
     * @return the input UUID, as a string if it valid, an exception is raised otherwise.
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String uuid = p.getValueAsString();
        Matcher matcher = Uuid.UUID_PATTERN.matcher(uuid);

        if (matcher.matches()) {
            return uuid;
        } else {
            throw new JsonParseException(p, "Was unable to parse UUID because it is invalid for " + uuid);
        }
    }
}
