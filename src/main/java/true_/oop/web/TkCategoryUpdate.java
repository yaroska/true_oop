package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsJson;
import true_.oop.api.Base;
import true_.oop.api.Category;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.util.Optional;

final class TkCategoryUpdate implements Take {

    private final Base base;

    TkCategoryUpdate(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        try {
            long number = Long.parseLong(((RqRegex) req).matcher().group("id"));
            Category category = base.categories().category(number);

            JsonReader reader = Json.createReader(req.body());
            JsonObject json = reader.readObject();
            String name = json.getString("name");
            Optional<Category> parent = Optional.of(json.getInt("parent_id", 0))
                    .filter(id -> id > 0)
                    .map(id -> base.categories().category(id));

            Category result = category.update(name, parent);
            return new RsJson(result);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
