package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsWithStatus;
import true_.oop.api.Base;
import true_.oop.api.Category;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Add products to category
 */
final class TkCategoryProductsAdd implements Take {

    private final Base base;

    TkCategoryProductsAdd(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        long categoryId = Long.parseLong(((RqRegex) req).matcher().group("number"));
        Category category = base.categories().category(categoryId);
        JsonReader reader = Json.createReader(req.body());
        Product[] products = reader.readArray().stream()
                .map(JsonValue::asJsonObject)
                .map(product -> product.getInt("id"))
                .map(id -> base.products().product(id))
                .toArray(Product[]::new);
        base.categoryProducts(category).add(products);
        return new RsWithStatus(HttpURLConnection.HTTP_NO_CONTENT);
    }
}
