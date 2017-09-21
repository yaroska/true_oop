package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsJson;
import true_.oop.api.Base;
import true_.oop.api.Product;

import java.io.IOException;

/**
 * Get product.
 */
final class TkProduct implements Take {

    private final Base base;

    TkProduct(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        long number = Long.parseLong(((RqRegex) req).matcher().group("number"));
        Product product = base.products().product(number);

        try {
            return new RsJson(product);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
