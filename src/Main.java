import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entry.LogEntry;
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
            try {
                while ((entry = reader.readLine()) != null) {
                    // deserialize input line into LogEntry from file
                    LogEntry logEntry = mapper.readValue(entry, LogEntry.class);

                    // extract the filename extension
                    Optional<String> fileNameExtension = FilenameUtils.extractFilenameExtension(logEntry.getFilename());

                    // check if the filename is present
                    if (fileNameExtension.isPresent()) {
                        HashSet<LogEntry> files = extensions.getOrDefault(fileNameExtension.get(), new HashSet<>());

                        files.add(logEntry);
                        extensions.put(fileNameExtension.get(), files);
                    }
                }
            } catch (IOException ex) {
                // raised from readLine
                // if a single line cannot be read from the buffer, we will do our best to continue
                LOGGER.log(Level.WARNING, "Was unable to read line from buffer: " + ex.getMessage());
            }
        } catch (IOException io) {
            // raised from BufferedReader, FileReader or new File()
            // we cannot continue if we cannot initialize the buffer or the file is not found
            throw new RuntimeException(io);
        }

        for (Map.Entry<String, HashSet<LogEntry>> entry : extensions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
        }
    }
}
