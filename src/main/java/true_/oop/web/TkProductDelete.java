package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsWithStatus;
import true_.oop.api.Base;
import true_.oop.api.Product;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Endpoint for /iterate
 */
final class TkProductDelete implements Take {

    private final Base base;

    TkProductDelete(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        long number = Long.parseLong(((RqRegex) req).matcher().group("id"));
        Product product = base.products().product(number);

        base.products().delete(product);
        return new RsWithStatus(HttpURLConnection.HTTP_NO_CONTENT);
    }
}
