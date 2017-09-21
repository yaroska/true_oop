package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsWithStatus;
import true_.oop.api.Base;
import true_.oop.api.Category;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Delete category.
 */
final class TkCategoryDelete implements Take {

    private final Base base;

    TkCategoryDelete(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        long number = Long.parseLong(((RqRegex) req).matcher().group("number"));
        Category category = base.categories().category(number);

        base.categories().delete(category);
        return new RsWithStatus(HttpURLConnection.HTTP_NO_CONTENT);
    }
}
