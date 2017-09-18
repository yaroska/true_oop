package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import org.javamoney.moneta.FastMoney;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.sql.SQLException;

final class H2Product implements Product {

    private final H2Source dBase;
    private final long number;

    H2Product(H2Source dBase, long number) {
        this.dBase = dBase;
        this.number = number;
    }

    @Override
    public long id() {
        return number;
    }

    @Override
    public String name() {
        try {
            return new JdbcSession(dBase.get())
                    .sql("SELECT name FROM product WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(String.class));
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public String desc() {
        try {
            return new JdbcSession(dBase.get())
                    .sql("SELECT desc FROM product WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(String.class));
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public MonetaryAmount price() {
        try {
            return FastMoney.of(
                    new BigDecimal(
                            new JdbcSession(dBase.get())
                                    .sql("SELECT price FROM product WHERE id=?")
                                    .set(this.number)
                                    .select(new SingleOutcome<>(String.class))),
                    "CZK");
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public Product update(String name, String desc, MonetaryAmount price) {
        try {
            new JdbcSession(dBase.get())
                    .sql("UPDATE product SET name=?, desc=?, price=? WHERE id=?")
                    .set(name)
                    .set(desc)
                    .set(price.getNumber())
                    .set(this.number)
                    .update(Outcome.VOID);
            return new H2Product(dBase, this.number);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder()
                .add("id", number)
                .add("name", name())
                .add("description", desc())
                .add("price", price().toString())
                .build();
    }
}
