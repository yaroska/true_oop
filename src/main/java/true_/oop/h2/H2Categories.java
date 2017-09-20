package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import true_.oop.api.Categories;
import true_.oop.api.Category;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

final class H2Categories implements Categories {

    private final H2Source dBase;

    H2Categories(H2Source dBase) {
        this.dBase = dBase;
    }

    @Override
    public JsonStructure toJson() {
        JsonArrayBuilder result = Json.createArrayBuilder();
        iterate().stream()
                .map(Category::toJson)
                .forEach(result::add);
        return result.build();
    }

    @Override
    public List<Category> iterate() {
        try {
            return new JdbcSession(this.dBase.get())
                    .sql("SELECT id FROM category")
                    .select(new ListOutcome<>(rSet ->
                            new H2Category(dBase, rSet.getLong(1))));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public Category add(String name, Optional<Category> parent) {
        try {
            return new H2Category(dBase,
                    new JdbcSession(this.dBase.get())
                            .sql("INSERT INTO category (name, parent_id) VALUES (?, ?)")
                            .set(name)
                            .set(parent.map(Category::id).orElse(null))
                            .insert(new SingleOutcome<>(Long.class)));
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public void delete(Category category) {
        try {
            new JdbcSession(this.dBase.get())
                    .sql("DELETE FROM category WHERE id = ?")
                    .set(category.id())
                    .execute();
        } catch (SQLException e) {
            ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public Category category(long number) {
        try {
            new JdbcSession(this.dBase.get())
                    .sql("SELECT id FROM category WHERE id = ?")
                    .set(number)
                    .select(Outcome.NOT_EMPTY);
            // exist
            return new H2Category(dBase, number);
        } catch (SQLException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        H2Categories that = (H2Categories) o;

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
