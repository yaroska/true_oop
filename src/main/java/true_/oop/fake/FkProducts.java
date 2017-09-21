package true_.oop.fake;

import true_.oop.api.Product;
import true_.oop.api.Products;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

public class FkProducts implements Products {

    @Override
    public List<Product> iterate() {
        return Collections.emptyList();
    }

    @Override
    public Product add(String name, String desc, MonetaryAmount price) {
        return new FkProduct();
    }

    @Override
    public void delete(Product product) {
        // noop
    }

    @Override
    public Product product(long number) {
        return new FkProduct();
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder().build();
    }
}
