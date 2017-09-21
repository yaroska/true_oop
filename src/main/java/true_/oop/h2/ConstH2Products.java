package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.javamoney.moneta.FastMoney;
import true_.oop.api.Product;
import true_.oop.api.Products;
import true_.oop.misc.JsonArray;

import javax.json.JsonStructure;
import javax.money.MonetaryAmount;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of products which return 'cached' product.
 */
final class ConstH2Products implements Products {

    private final DataSource dBase;
    private final Products origin;

    ConstH2Products(DataSource dBase, Products origin) {
        this.dBase = dBase;
        this.origin = origin;
    }

    @Override
    public List<Product> iterate() {
        try {
            return new JdbcSession(this.dBase)
                    .sql("SELECT * FROM product")
                    .select(new ListOutcome<>(this::create));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    private Product create(ResultSet rSet) throws SQLException {
        return new ConstProduct(
                new H2Product(dBase, rSet.getLong(1)),
                rSet.getString(2),
                rSet.getString(3),
                FastMoney.of(rSet.getLong(4), "CZK"));
    }

    @Override
    public Product add(String name, String desc, MonetaryAmount price) {
        return new ConstProduct(origin.add(name, desc, price),
                name, desc, price);
    }

    @Override
    public void delete(Product product) {
        origin.delete(product);
    }

    @Override
    public Product product(long number) {
        try {
            return new JdbcSession(this.dBase)
                    .sql("SELECT * FROM product WHERE id=?")
                    .set(number)
                    .select((rset, stmt) -> {
                        if (!rset.next()) {
                            throw new IllegalStateException("no product");
                        }
                        return create(rset);
                    });
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public JsonStructure toJson() {
        return new JsonArray(iterate()).toJson();
    }
}
