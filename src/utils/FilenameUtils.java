package utils;

import java.util.Optional;

public class FilenameUtils {

    /**
     * Used to extract the extension from a file name.
     *
     * @param filename name of the file.
     * @return an optional which wll contain the file name.
     */
    public static Optional<String> extractFilenameExtension(String filename) {
        int periodIndex = filename.lastIndexOf('.');

        return periodIndex > 0 ? Optional.of(filename.substring(periodIndex)) : Optional.empty();
    }

}
