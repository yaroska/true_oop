package true_.oop.h2;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import true_.oop.api.*;

import javax.sql.DataSource;

public final class H2Base implements Base {

    private final DataSource src;

    public H2Base() throws Exception {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        Class.forName("org.h2.Driver");
        src = JdbcConnectionPool.create(
                "jdbc:h2:file:/tmp/warehouse;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false", "sa", "");
    }

    @Override
    public Categories categories() {
        return new H2Categories(src);
    }

    @Override
    public Products products() {
        return new H2Products(src);
    }

    @Override
    public CategoryProducts categoryProducts(Category category) {
        return new H2CategoryProducts(src, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        H2Base h2Base = (H2Base) o;

        return new EqualsBuilder()
                .append(src, h2Base.src)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(src)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("src", src)
                .toString();
    }
}
