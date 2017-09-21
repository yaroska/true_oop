package true_.oop.api;

import java.util.Optional;

/**
 * Available category.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface Category extends JsonSource {

    /**
     * Category number.
     *
     * @return Number of category.
     */
    long id();

    /**
     * Update current category.
     * @param name New name of category.
     * @param parent New parent of category if exist.
     * @return Updated category.
     */
    Category update(String name, Optional<Category> parent);
}
