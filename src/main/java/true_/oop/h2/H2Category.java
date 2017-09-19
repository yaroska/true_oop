package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import true_.oop.api.Category;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

final class H2Category implements Category {

    private final H2Source dBase;
    private final long number;

    H2Category(H2Source dBase, long id) {
        this.dBase = dBase;
        this.number = id;
    }

    @Override
    public long id() {
        return number;
    }

    private String name() {
        try {
            return new JdbcSession(dBase.get())
                    .sql("SELECT name FROM category WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(String.class));
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private Optional<Category> parent() {
        try {
            return Optional.ofNullable(new JdbcSession(dBase.get())
                    .sql("SELECT parent_id FROM category WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(Long.class, true)))
                    .filter(id -> id > 0) // null value in db
                    .map(id -> new H2Category(dBase, id));
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public Category update(String name, Optional<Category> parent) {
        try {
            new JdbcSession(dBase.get())
                    .sql("UPDATE category SET name=?, parent_id=? WHERE id=?")
                    .set(name)
                    .set(parent.map(Category::id).orElse(null))
                    .set(this.number)
                    .update(Outcome.VOID);
            return new H2Category(dBase, this.number);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public List<Product> products() {
        try {
            return new JdbcSession(this.dBase.get())
                    .sql("SELECT product_id FROM category_product WHERE category_id=?")
                    .set(this.number)
                    .select(new ListOutcome<>(rSet ->
                            new H2Product(dBase, rSet.getLong(1))));
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void add(Product product) {
        try {
            new JdbcSession(this.dBase.get())
                    .sql("INSERT INTO category_product (category_id, product_id) VALUES (?, ?)")
                    .set(this.number)
                    .set(product.id())
                    .insert(Outcome.VOID);
        } catch (Exception e) {
            rethrow(e);
        }

    }

    @Override
    public void remove(Product product) {
        try {
            new JdbcSession(this.dBase.get())
                    .sql("DELETE FROM category_product WHERE category_id=? AND product_id=?")
                    .set(this.number)
                    .set(product.id())
                    .execute();
        } catch (Exception e) {
            rethrow(e);
        }
    }

    @Override
    public JsonStructure toJson() {
        JsonObjectBuilder json = Json.createObjectBuilder()
                .add("id", number)
                .add("name", name());
        parent().ifPresent(parent -> json.add("parent_id", parent.id()));
        return json.build();
    }
}
