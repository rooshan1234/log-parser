package processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entry.LogEntry;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogEntryParser {

    private static final Logger LOGGER = Logger.getLogger(LogEntryParser.class.getName());

    private ObjectMapper mapper;
    private boolean loggingEnabled;

    public LogEntryParser(ObjectMapper mapper) {
        this(mapper, false);
    }

    public LogEntryParser(ObjectMapper mapper, boolean loggingEnabled) {
        this.mapper = mapper;
        this.loggingEnabled = loggingEnabled;
    }

    public LogEntry parse(String line) {
        LogEntry logEntry = new LogEntry();

        try {
            logEntry = mapper.readValue(line, LogEntry.class);
        } catch (JsonProcessingException ex) {
            // raised from readValue
            // if a single line has parsing exception, we will continue to parse the rest of the file
            if (loggingEnabled) {
                LOGGER.log(Level.WARNING, "Was unable to parse line: " + ex.getMessage());
            }
        }

        return logEntry;
    }

}
