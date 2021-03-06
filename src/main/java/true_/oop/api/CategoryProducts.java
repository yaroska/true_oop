package true_.oop.api;

import java.util.List;

/**
 * All products in category.
 */
public interface CategoryProducts extends JsonSource {

    /**
     * List all products in category.
     *
     * @return All products in category.
     */
    List<Product> iterate();

    /**
     * Add product to category.
     * @param products Products to add.
     */
    void add(Product... products);

    /**
     * Remove product from category.
     * @param product Product to remove.
     */
    void remove(Product product);
}
