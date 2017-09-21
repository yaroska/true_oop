package true_.oop.fake;

import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.money.MonetaryAmount;

final class FkProduct implements Product {

    @Override
    public long number() {
        return 0;
    }

    @Override
    public Product update(String name, String desc, MonetaryAmount price) {
        return new FkProduct();
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder().build();
    }
}
