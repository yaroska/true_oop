package true_.oop.api;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface Category extends JsonSource {

    long id();

    Category update(String name, Optional<Category> parent);
}
