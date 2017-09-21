package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import true_.oop.api.Product;
import true_.oop.api.Products;
import true_.oop.misc.JsonArray;

import javax.json.JsonStructure;
import javax.money.MonetaryAmount;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

final class H2Products implements Products {

    private final DataSource dBase;

    H2Products(DataSource dBase) {
        this.dBase = dBase;
    }

    @Override
    public List<Product> iterate() {
        try {
            return new JdbcSession(this.dBase)
                    .sql("SELECT id FROM product")
                    .select(new ListOutcome<>(rSet ->
                            new H2Product(dBase, rSet.getLong(1))));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public Product add(String name, String desc, MonetaryAmount price) {
        try {
            return new H2Product(dBase,
                    new JdbcSession(this.dBase)
                            .sql("INSERT INTO product (name, desc, price) VALUES (?, ?, ?)")
                            .set(name)
                            .set(desc)
                            .set(price.getNumber())
                            .insert(new SingleOutcome<>(Long.class)));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public void delete(Product product) {
        try {
            new JdbcSession(this.dBase)
                    .sql("DELETE FROM product WHERE id=?")
                    .set(product.number())
                    .execute();
        } catch (SQLException e) {
            ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public Product product(long number) {
        try {
            new JdbcSession(this.dBase)
                    .sql("SELECT id FROM product WHERE id=?")
                    .set(number)
                    .select(Outcome.NOT_EMPTY);
            // exist
            return new H2Product(dBase, number);
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public JsonStructure toJson() {
        return new JsonArray(iterate()).toJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        H2Products that = (H2Products) o;

        return new EqualsBuilder()
                .append(dBase, that.dBase)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(dBase)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dBase", dBase)
                .toString();
    }
}
