package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJson;
import true_.oop.api.Base;
import true_.oop.api.Category;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.util.Optional;

/**
 * Create category.
 */
final class TkCategoryAdd implements Take {

    private final Base base;

    TkCategoryAdd(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        try {
            JsonReader reader = Json.createReader(req.body());
            JsonObject json = reader.readObject();
            String name = json.getString("name");
            Optional<Category> parentId = Optional.of(json.getInt("parent_id", 0))
                    .filter(id -> id > 0)
                    .map(id -> base.categories().category(id));
            Category result = base.categories().add(name, parentId);
            return new RsJson(result);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
