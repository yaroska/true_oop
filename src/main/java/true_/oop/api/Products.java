package true_.oop.api;

import javax.money.MonetaryAmount;
import java.util.List;

public interface Products extends JsonSource {

    List<Product> iterate();

    Product add(String name, String desc, MonetaryAmount price);

    void delete(Product product);

    Product product(long number);
}
