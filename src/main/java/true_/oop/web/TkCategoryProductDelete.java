package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsWithStatus;
import true_.oop.api.Base;
import true_.oop.api.Category;
import true_.oop.api.Product;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Remove product from category.
 */
final class TkCategoryProductDelete implements Take {

    private final Base base;

    TkCategoryProductDelete(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        long categoryId = Long.parseLong(((RqRegex) req).matcher().group("ctgId"));
        long productId = Long.parseLong(((RqRegex) req).matcher().group("prdId"));
        Category category = base.categories().category(categoryId);
        Product product = base.products().product(productId);

        base.categoryProducts(category).remove(product);

        return new RsWithStatus(HttpURLConnection.HTTP_NO_CONTENT);
    }
}
