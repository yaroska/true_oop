package true_.oop.h2;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ConstProduct that = (ConstProduct) o;

        return new EqualsBuilder()
                .append(origin.number(), that.origin.number())
                .append(name, that.name)
                .append(desc, that.desc)
                .append(price, that.price)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(origin.number())
                .append(name)
                .append(desc)
                .append(price)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("number", origin.number())
                .append("name", name)
                .append("desc", desc)
                .append("price", price)
                .toString();
    }
}
