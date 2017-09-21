package true_.oop.h2;

import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.money.MonetaryAmount;

/**
 * Cached product decorator.
 */
final class ConstProduct implements Product {

    private final Product origin;
    private final String name;
    private final String desc;
    private final MonetaryAmount price;

    ConstProduct(Product product, String name, String desc, MonetaryAmount price) {
        this.origin = product;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    @Override
    public long number() {
        return origin.number();
    }

    @Override
    public Product update(String name, String desc, MonetaryAmount price) {
        return new ConstProduct(origin.update(name, desc, price),
                name, desc, price);
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder()
                .add("number", number())
                .add("name", name)
                .add("description", desc)
                .add("price", price.toString())
                .build();
    }
}
