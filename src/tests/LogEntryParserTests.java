package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import factories.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import processor.LogEntryParser;

/**
 * This class will test the parser with various JSON line inputs that the file can contain. This class also has
 * logging enabled, which means each test will throw a warning telling you why it failed (it is not an exception).
 */
public class LogEntryParserTests {

    private LogEntryParser parser;

    @Before
    public void setUp() throws Exception {
        ObjectMapper mapper = ObjectMapperFactory.create();
        // we enable logging here, this tells us what the parser thinks should be valid
        parser = new LogEntryParser(mapper, true);
    }

    @Test(expected = Exception.class)
    public void nullLine() throws Exception {
        String line = null;
        // we assert false because we don't want to parse if a null line is provided
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void invalidLine() throws Exception {
        String line = "\n";
        // ignore line if invalid line is provided
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void invalidJsonStart() throws Exception {
        String line =
                "\"ts\": \"3214213\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        // ignore line if JSON line has incorrect begin character
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void invalidJsonEnd() throws Exception {
        String line =
                "{\"ts\": \"3214213\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2";
        // ignore line if JSON line has incorrect end character
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void invalidUuidTest() throws Exception {
        String line =
                "{\"ts\": \"3214213\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f10\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        // ignore line if JSON line has incorrect UUID
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void invalidDisposition() {
        String line =
                "{\"ts\": \"3214213\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":10}";
        // ignore line if JSON line has incorrect disposition
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void invalidFilenameExtension() {
        String line =
                "{\"ts\": \"3214213\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        // ignore line if JSON line has no filename extension
        Assert.assertFalse(parser.parse(line).isPresent());
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
        // ignore line if JSON line has no 'ts' field
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void fieldIsBlank() {
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
        // ignore line if JSON line has no 'ts' value provided
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void fieldIsNull() {
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
        // ignore line if JSON line has 'ts' null value
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void fieldLongHasInvalidType() {
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
        // ignore line if JSON line has 'ts' string instead of long (primitive)
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void lineMissingMultipleRequiredFields() {
        String line =
                "{\"ts\": \"3214213\"," +
                        "\"pt\":55," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.txt\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        // ignore line if JSON line doesn't have uu, bg, sha
        Assert.assertFalse(parser.parse(line).isPresent());
    }

    @Test
    public void validJsonProvided() {
        String line =
                "{\"ts\": \"3214213\"," +
                        "\"pt\":\"55\"," +
                        "\"si\":" + "\"3380fb19-0bdb-46ab-8781-e4c5cd448074\"," +
                        "\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\"," +
                        "\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\"," +
                        "\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\"," +
                        "\"nm\":\"phkkrw.ext\"," +
                        "\"ph\":\"/efvrfutgp/expgh/phkkrw\"," +
                        "\"dp\":2}";
        // valid JSON provided, the line will be parsed successfully
        Assert.assertTrue(parser.parse(line).isPresent());
    }

}
