package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.DiffItem;

public interface BaseFormatter {
    String format(DiffItem item) throws JsonProcessingException;
}
