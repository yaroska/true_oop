package true_.oop.api;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface Category extends JsonSource {

    long id();

    String name();

    Optional<Category> parent();

    Category update(String name, Optional<Category> parent);

    List<Product> products();
}
