package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Builder(builderClassName = "Builder")
@Getter
@Setter
public class DiffItem {
    private final String state;
    private final String fieldName;
    private final JsonNode oldValue;
    private final JsonNode newValue;
    private final List<DiffItem> children;
}
