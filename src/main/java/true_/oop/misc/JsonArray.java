package true_.oop.misc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JsonArray jsonArray = (JsonArray) o;

        return new EqualsBuilder()
                .append(col, jsonArray.col)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(col)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("col", col)
                .toString();
    }
}
