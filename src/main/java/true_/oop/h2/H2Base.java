package true_.oop.h2;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import true_.oop.api.Base;
import true_.oop.api.Categories;
import true_.oop.api.Products;

public final class H2Base implements Base {

    private final H2Source src;

    public H2Base() throws Exception {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        Class.forName("org.h2.Driver");
        src = () -> JdbcConnectionPool.create(
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
}
