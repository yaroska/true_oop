package true_.oop.api;

/**
 * Base of the entire system.
 */
public interface Base {

    /**
     * All categories available now.
     *
     * @return All categories.
     */
    Categories categories();

    /**
     * All products available now.
     * @return All products.
     */
    Products products();

    /**
     * All products in category.
     * @param category The category.
     * @return Products in category.
     */
    CategoryProducts categoryProducts(Category category);
}
