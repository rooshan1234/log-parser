package validation;

import java.util.Arrays;
import java.util.Optional;

public enum Disposition {
    MALICIOUS(1), CLEAN(2), UNKNOWN(3);

    private int value;

    private Disposition(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static Optional<Disposition> valueOf(Integer value) {
        return Arrays.stream(Disposition.values())
                .filter(disposition -> disposition.getValue().equals(value))
                .findFirst();
    }
}
