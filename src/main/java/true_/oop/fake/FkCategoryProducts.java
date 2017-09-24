package true_.oop.fake;

import true_.oop.api.Category;
import true_.oop.api.CategoryProducts;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonStructure;
import java.util.Collections;
import java.util.List;

final class FkCategoryProducts implements CategoryProducts {

    @SuppressWarnings("unused")
    FkCategoryProducts(Category category) {
    }

    @Override
    public List<Product> iterate() {
        return Collections.emptyList();
    }

    @Override
    public void add(Product... products) {
        // noop
    }

    @Override
    public void remove(Product product) {
        // noop
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder().build();
    }
}
