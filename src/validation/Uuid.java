package validation;

import java.util.regex.Pattern;

public class Uuid {
    /**
     * The regex specification used to parse the UUID from log entries is taken from here:
     * https://tools.ietf.org/html/rfc4122#section-4.1.7
     * <p>
     * Which the regex implemented here: https://stackoverflow.com/questions/7905929/how-to-test-valid-uuid-guid
     */
    public static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$",
                    Pattern.CASE_INSENSITIVE);
}
