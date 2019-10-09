package processor;

import entry.LogEntry;
import utils.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogEntryReader {

    private static final Logger LOGGER = Logger.getLogger(LogEntryReader.class.getName());

    private BufferedReader reader;
    private LogEntryParser parser;
    private HashMap<String, HashSet<LogEntry>> data = new HashMap<>();

    public LogEntryReader(BufferedReader reader, LogEntryParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public HashMap<String, HashSet<LogEntry>> read() {
        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                // deserialize input line into LogEntry from file
                LogEntry logEntry = parser.parse(line);

                // extract the filename extension
                Optional<String> fileNameExtension = FilenameUtils.extractFilenameExtension(logEntry.getFilename());

                // check if the filename is present
                if (fileNameExtension.isPresent()) {
                    // we build a HashMap of the extension (e.g. '.pdf') to the unique list of files for that extension
                    HashSet<LogEntry> files = data.getOrDefault(fileNameExtension.get(), new HashSet<>());

                    // files is a HashSet, which means it will use the equals and hashcode methods to create a unique
                    // list of files
                    files.add(logEntry);

                    // finally update the HashMap with the new list of unique files for the current processing LogEntry
                    data.put(fileNameExtension.get(), files);
                }
            }
        } catch (IOException ex) {
            // raised from readLine
            // if a single line cannot be read from the buffer, we will do our best to continue
            LOGGER.log(Level.WARNING, "Was unable to read line from buffer: " + ex.getMessage());
        }
        return data;
    }
}
