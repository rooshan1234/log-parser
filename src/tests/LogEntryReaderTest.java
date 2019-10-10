package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import factories.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import processor.LogEntryParser;
import processor.LogEntryReader;

import java.io.BufferedReader;
import java.io.StringReader;

public class LogEntryReaderTest {

    private ObjectMapper mapper = ObjectMapperFactory.create();
    private LogEntryParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new LogEntryParser(mapper);
    }

    @Test
    public void invalidLineFromBuffer() throws Exception {
        String entry = "\n";
        StringReader line = new StringReader(entry);
        LogEntryReader reader = new LogEntryReader(new BufferedReader(line), parser, true);

        // ignore line if invalid line is provided
        Assert.assertTrue(reader.read().isEmpty());
    }

}
