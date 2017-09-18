package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsJson;
import true_.oop.api.Base;
import true_.oop.api.Category;
import true_.oop.misc.JsonArray;

import java.io.IOException;

/**
 * Endpoint for /iterate
 */
final class TkCategoryProducts implements Take {

    private final Base base;

    TkCategoryProducts(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        long number = Long.parseLong(((RqRegex) req).matcher().group("id"));
        Category category = base.categories().category(number);

        try {
            return new RsJson(new JsonArray(category.products()));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
