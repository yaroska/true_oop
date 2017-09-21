package true_.oop.misc;

import true_.oop.api.JsonSource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.util.Collection;

/**
 * Work with JSON array.
 */
public final class JsonArray implements JsonSource {

    private final Collection<? extends JsonSource> col;

    public JsonArray(Collection<? extends JsonSource> col) {
        this.col = col;
    }

    /**
     * Creates JSON representation of given collection.
     *
     * @return JSON structure.
     */
    @Override
    public JsonStructure toJson() {
        JsonArrayBuilder result = Json.createArrayBuilder();
        col.stream()
                .map(JsonSource::toJson)
                .forEach(result::add);
        return result.build();
    }
}
