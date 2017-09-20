package true_.oop.api;

/**
 * Base of the entire system.
 */
public interface Base {

    Categories categories();

    Products products();

    CategoryProducts categoryProducts(Category category);
}
