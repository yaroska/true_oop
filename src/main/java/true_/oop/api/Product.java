package true_.oop.api;

import javax.money.MonetaryAmount;

public interface Product extends JsonSource {

    long id();

    String name();

    String desc();

    MonetaryAmount price();

    Product update(String name, String desc, MonetaryAmount price);
}
