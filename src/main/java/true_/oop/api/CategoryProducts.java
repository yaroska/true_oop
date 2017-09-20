package true_.oop.api;

import java.util.List;

public interface CategoryProducts extends JsonSource {

    List<Product> iterate();

    void add(Product product);

    void remove(Product product);
}
