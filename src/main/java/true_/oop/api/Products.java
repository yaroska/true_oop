package true_.oop.api;

import javax.money.MonetaryAmount;
import java.util.List;

/**
 * Available products.
 */
public interface Products extends JsonSource {

    /**
     * Get all available products.
     *
     * @return All products.
     */
    List<Product> iterate();

    /**
     * Add new product.
     * @param name Product name.
     * @param desc Product description.
     * @param price Product price.
     * @return Created product.
     */
    Product add(String name, String desc, MonetaryAmount price);

    /**
     * Delete product.
     * @param product Product to be deleted.
     */
    void delete(Product product);

    /**
     * Find product by number.
     * @param number The number.
     * @return Found product or exception thrown otherwise.
     */
    Product product(long number);
}
