import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Integer> extensions = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("log.json")))) {

            String entry = "";
            while ((entry = reader.readLine()) != null) {
                LogEntry logEntry = mapper.readValue(entry, LogEntry.class);
                if (logEntry.isValid()) {

                    int count = +extensions.getOrDefault(logEntry.getFilename(), 1);
                    extensions.put(logEntry.getFilename(), count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int mapfiles = 0;

        for (Map.Entry<String, Integer> e : extensions.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
            if (e.getKey().lastIndexOf(".") > 0) {
                String fileExtension = e.getKey().substring(e.getKey().lastIndexOf("."));
                if (fileExtension.equals(".map")) {
                    mapfiles++;
                }
            }
        }

        System.out.println(mapfiles);

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
}
