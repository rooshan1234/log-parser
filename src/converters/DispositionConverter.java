package converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import validation.Disposition;

import java.util.Optional;

public class DispositionConverter extends StdConverter<Integer, Disposition> {

    @Override
    public Disposition convert(Integer code) {
        Optional<Disposition> disposition = Disposition.valueOf(code);
        return disposition.orElse(null);
    }
}
