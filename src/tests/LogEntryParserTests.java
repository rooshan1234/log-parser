package tests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import factories.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import processor.LogEntryParser;

public class LogEntryParserTests {

    private LogEntryParser parser;

    @Before
    public void setUp() throws Exception {
        ObjectMapper mapper = ObjectMapperFactory.create();
        parser = new LogEntryParser(mapper, true);
    }

    @Test
    public void nullLine() throws Exception {
        String line = null;
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void invalidLine() throws Exception {
        String line = "\n";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void invalidJsonStart() throws Exception {
        String line =
                "\"ts\":1551140352," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void invalidJsonEnd() throws Exception {
        String line =
                "{\"ts\":1551140352," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void invalidUuidTest() throws Exception {
        String line =
                "{\"ts\":1551140352," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f10\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void invalidDisposition() {
        String line =
                "{\"ts\":1551140352," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":10}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void invalidFilenameExtension() {
        String line =
                "{\"ts\":1551140352," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void fieldNotProvided() {
        // test: timestamp is not provided
        String line =
                "{\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.txt\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void fieldIsBlank() {
        // test: timestamp is blank
        String line =
                "{\"ts\":," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void fieldIsNull() {
        // test: timestamp is blank
        String line =
                "{\"ts\":\"null\":," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

    @Test
    public void fieldLongHasInvalidType() {
        // test: timestamp is blank
        String line =
                "{\"ts\": \"test\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        Assert.assertTrue(parser.parse(line).isPresent());
    }

}
