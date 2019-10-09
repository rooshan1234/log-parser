import com.fasterxml.jackson.databind.ObjectMapper;
import entry.LogEntry;
import processor.LogEntryParser;
import processor.LogEntryReader;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        LogEntryParser parser = new LogEntryParser(mapper);

        HashMap<String, HashSet<LogEntry>> data;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("log.json")))) {
            LogEntryReader logEntryReader = new LogEntryReader(reader, parser);
            data = logEntryReader.read();
        } catch (IOException io) {
            // raised from BufferedReader, FileReader or new File()
            // we cannot continue if we cannot initialize the buffer or the file is not found
            throw new RuntimeException(io);
        }

        for (Map.Entry<String, HashSet<LogEntry>> entry : data.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
        }
    }
}
