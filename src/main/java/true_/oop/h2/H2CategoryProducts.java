package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import true_.oop.api.Category;
import true_.oop.api.CategoryProducts;
import true_.oop.api.Product;

import javax.json.JsonStructure;
import java.util.List;

public class H2CategoryProducts implements CategoryProducts {

    private final H2Source dBase;
    private final Category category;

    public H2CategoryProducts(H2Source dBase, Category category) {
        this.dBase = dBase;
        this.category = category;
    }

    @Override
    public List<Product> iterate() {
        try {
            return new JdbcSession(this.dBase.get())
                    .sql("SELECT product_id FROM category_product WHERE category_id = ?")
                    .set(category.id())
                    .select(new ListOutcome<>(rSet ->
                            new H2Product(dBase, rSet.getLong(1))));
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public void add(Product product) {
        try {
            new JdbcSession(this.dBase.get())
                    .sql("INSERT INTO category_product (category_id, product_id) VALUES (?, ?)")
                    .set(category.id())
                    .set(product.id())
                    .insert(Outcome.VOID);
        } catch (Exception e) {
            ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public void remove(Product product) {
        try {
            new JdbcSession(this.dBase.get())
                    .sql("DELETE FROM category_product WHERE category_id=? AND product_id=?")
                    .set(category.id())
                    .set(product.id())
                    .execute();
        } catch (Exception e) {
            ExceptionUtils.rethrow(e);
        }
    }

    @Override
    public JsonStructure toJson() {
        return new true_.oop.misc.JsonArray(iterate()).toJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        H2CategoryProducts that = (H2CategoryProducts) o;

        return new EqualsBuilder()
                .append(dBase, that.dBase)
                .append(category, that.category)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(dBase)
                .append(category)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dBase", dBase)
                .append("category", category)
                .toString();
    }
}
