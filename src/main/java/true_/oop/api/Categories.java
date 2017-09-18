package true_.oop.api;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface Categories extends JsonSource {

    List<Category> iterate();

    Category add(String name, Optional<Category> parent);

    void delete(Category category);

    Category category(long number);
}
