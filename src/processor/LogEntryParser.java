package processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entry.LogEntry;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogEntryParser {

    private static final Logger LOGGER = Logger.getLogger(LogEntryParser.class.getName());

    private ObjectMapper mapper;
    private boolean loggingEnabled;

    /**
     * Constructor used to build the parser with the ObjectMapper. Logging is disabled by default.
     *
     * @param mapper used to bind a single line from the file to a LogEntry.
     */
    public LogEntryParser(ObjectMapper mapper) {
        this(mapper, false);
    }

    /**
     * Constructor used for testing purposes, where logging can be enabled.
     *
     * @param mapper used to bind a single line from the file to a LogEntry.
     */
    public LogEntryParser(ObjectMapper mapper, boolean loggingEnabled) {
        this.mapper = mapper;
        this.loggingEnabled = loggingEnabled;
    }

    public Optional<LogEntry> parse(String line) {
        Optional<LogEntry> logEntry = Optional.empty();

        try {
            logEntry = Optional.of(mapper.readValue(line, LogEntry.class));
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
