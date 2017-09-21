package true_.oop.api;

import java.util.List;
import java.util.Optional;

/**
 * Available categories.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface Categories extends JsonSource {

    /**
     * Iterate them.
     *
     * @return All categories.
     */
    List<Category> iterate();

    /**
     * Add new category.
     * @param name Name of category.
     * @param parent Parent category if exist.
     * @return Created category.
     */
    Category add(String name, Optional<Category> parent);

    /**
     * Delete caterogy.
     * @param category Category to be deleted.
     */
    void delete(Category category);

    /**
     * Find category by number.
     * @param number Category number.
     * @return Found category if exist and Exception otherwise.
     */
    Category category(long number);
}
