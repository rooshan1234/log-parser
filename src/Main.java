import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.FilenameUtils;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, HashSet<LogEntry>> extensions = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("log.json")))) {

            String entry = "";
            while ((entry = reader.readLine()) != null) {
                // deserialize
                try {
                    LogEntry logEntry = mapper.readValue(entry, LogEntry.class);

                    if (logEntry.isValid()) {
                        // extract the filename extension
                        Optional<String> fileNameExtension = FilenameUtils.extractFilenameExtension(logEntry.getFilename());

                        // check if the filename is present
                        if (fileNameExtension.isPresent()) {
                            HashSet<LogEntry> files = extensions.getOrDefault(fileNameExtension.get(), new HashSet<>());

                            files.add(logEntry);
                            extensions.put(fileNameExtension.get(), files);
                        }
                    }

                } catch (JsonParseException px) {
                    // if a single line has parsing exception, we will continue to parse the rest of the file
                    LOGGER.log(Level.WARNING, "Was unable to parse line: " + px.getMessage());
                }

            }
        } catch (IOException io) {
            io.printStackTrace();
        }

        for (Map.Entry<String, HashSet<LogEntry>> entry : extensions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
        }
    }
}
