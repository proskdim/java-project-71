package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffItem;

public class Json implements BaseFormatter {

    @Override
    public String format(DiffItem item) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(item);
    }
}
