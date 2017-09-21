package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.javamoney.moneta.FastMoney;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.money.MonetaryAmount;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;

final class H2Product implements Product {

    private final DataSource dBase;
    private final long number;

    H2Product(DataSource dBase, long number) {
        this.dBase = dBase;
        this.number = number;
    }

    @Override
    public long number() {
        return number;
    }

    private String name() {
        try {
            return new JdbcSession(dBase)
                    .sql("SELECT name FROM product WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(String.class));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    private String desc() {
        try {
            return new JdbcSession(dBase)
                    .sql("SELECT desc FROM product WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(String.class));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    private MonetaryAmount price() {
        try {
            return FastMoney.of(
                    new BigDecimal(
                            new JdbcSession(dBase)
                                    .sql("SELECT price FROM product WHERE id=?")
                                    .set(this.number)
                                    .select(new SingleOutcome<>(String.class))),
                    "CZK");
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public Product update(String name, String desc, MonetaryAmount price) {
        try {
            new JdbcSession(dBase)
                    .sql("UPDATE product SET name=?, desc=?, price=? WHERE id=?")
                    .set(name)
                    .set(desc)
                    .set(price.getNumber())
                    .set(this.number)
                    .update(Outcome.VOID);
            return new H2Product(dBase, this.number);
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder()
                .add("number", number)
                .add("name", name())
                .add("description", desc())
                .add("price", price().toString())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        H2Product h2Product = (H2Product) o;

        return new EqualsBuilder()
                .append(number, h2Product.number)
                .append(dBase, h2Product.dBase)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(dBase)
                .append(number)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dBase", dBase)
                .append("number", number)
                .append("name", name())
                .append("desc", desc())
                .append("price", price())
                .toString();
    }
}
