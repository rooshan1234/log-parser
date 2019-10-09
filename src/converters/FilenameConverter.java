package converters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import utils.FilenameUtils;

import java.io.IOException;

public class FilenameConverter extends StdDeserializer<String> {

    public FilenameConverter() {
        super(String.class);
    }

    /**
     * Used to convert the incoming filename back into the file name if it as an extension.
     *
     * @param p    the parser object.
     * @param ctxt the parser context.
     * @return the input filename, if it has extension, null otherwise.
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String filename = p.getValueAsString();
        return FilenameUtils.extractFilenameExtension(filename)
                .orElseThrow(() ->
                        new JsonParseException(p, "Was unable to parse Filename field with value: " + filename));
    }
}
