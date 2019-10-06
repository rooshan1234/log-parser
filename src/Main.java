import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("log.json")))) {
            String entry = "";
            while ((entry = reader.readLine()) != null) {
                LogEntry logEntry = mapper.readValue(entry, LogEntry.class);
                System.out.println(logEntry.getTimestamp());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        try {
//            LogEntry[] entry = mapper.readValue(new File("log.json"), LogEntry[].class);
//            System.out.println(entry[0].getTs());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        JsonFactory factory = new JsonFactory();
//
//        try {
//            JsonParser parser = factory.createParser(new File(("log.json")));
//            LogEntry entry = read(parser);
//
//            System.out.println(entry.getTs());
//
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }


    protected LogEntry read(JsonParser parser) throws IOException {
        LogEntry entry = new LogEntry();

        if (parser.nextToken() != JsonToken.START_OBJECT) {
            throw new RuntimeException("Invalid JSON object.");
        }

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.getCurrentName();
            parser.nextToken();

            if (fieldName.equals("ts")) {
                entry.setTimestamp(parser.getLongValue());
            }
        }
        parser.close();
        return entry;
    }
}