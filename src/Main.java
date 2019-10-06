import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        JsonFactory factory = new JsonFactory();

        try {
            JsonParser parser = factory.createParser(new File(("log.json")));
            LogEntry entry = read(parser);

            System.out.println(entry.getTs());

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    protected static LogEntry read(JsonParser parser) throws IOException {
        LogEntry entry = new LogEntry();

        if (parser.nextToken() != JsonToken.START_OBJECT) {
            throw new RuntimeException("Invalid JSON object.");
        }

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.getCurrentName();
            parser.nextToken();

            if (fieldName.equals("ts")) {
                entry.setTs(parser.getLongValue());
            }
        }
        parser.close();
        return entry;
    }

    static class LogEntry {
        private Long ts;

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }
    }

}