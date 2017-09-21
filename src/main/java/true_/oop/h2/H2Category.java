package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import true_.oop.api.Category;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

final class H2Category implements Category {

    private final DataSource dBase;
    private final long number;

    H2Category(DataSource dBase, long id) {
        this.dBase = dBase;
        this.number = id;
    }

    @Override
    public long id() {
        return number;
    }

    private String name() {
        try {
            return new JdbcSession(dBase)
                    .sql("SELECT name FROM category WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(String.class));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    private Optional<Category> parent() {
        try {
            return Optional.ofNullable(new JdbcSession(dBase)
                    .sql("SELECT parent_id FROM category WHERE id=?")
                    .set(this.number)
                    .select(new SingleOutcome<>(Long.class, true)))
                    .filter(id -> id > 0) // null value in db
                    .map(id -> new H2Category(dBase, id));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public Category update(String name, Optional<Category> parent) {
        try {
            new JdbcSession(dBase)
                    .sql("UPDATE category SET name=?, parent_id=? WHERE id=?")
                    .set(name)
                    .set(parent.map(Category::id).orElse(null))
                    .set(this.number)
                    .update(Outcome.VOID);
            return new H2Category(dBase, this.number);
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public JsonStructure toJson() {
        JsonObjectBuilder json = Json.createObjectBuilder()
                .add("number", number)
                .add("name", name());
        parent().ifPresent(parent -> json.add("parent_id", parent.id()));
        return json.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        H2Category that = (H2Category) o;

        return new EqualsBuilder()
                .append(number, that.number)
                .append(dBase, that.dBase)
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
        ToStringBuilder builder = new ToStringBuilder(this)
                .append("dBase", dBase)
                .append("number", number)
                .append("name", name());
        parent().ifPresent(p ->
                builder.append("parent.number", p.id()));
        return builder.toString();
    }
}
