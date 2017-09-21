package true_.oop.api;

import javax.money.MonetaryAmount;

/**
 * Available product.
 */
public interface Product extends JsonSource {

    /**
     * Product number.
     *
     * @return The number.
     */
    long number();

    /**
     * Update product with new values.
     *
     * @param name  New name.
     * @param desc  New description.
     * @param price New price.
     * @return Updated product.
     */
    Product update(String name, String desc, MonetaryAmount price);
}
