package true_.oop.h2;

import com.jcabi.jdbc.JdbcSession;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.sql.DataSource;
import java.util.function.Function;

/**
 * DB transaction
 */
final class Txn {

    private final DataSource dbase;

    Txn(DataSource dbase) {
        this.dbase = dbase;
    }

    /**
     * Wrap the body with transaction.
     *
     * @param body The body to be executed with transaction.
     * @return Result of execution.
     */
    <T> T call(Function<JdbcSession, T> body) {
        JdbcSession session = new JdbcSession(this.dbase);
        try {
            session.autocommit(false);
            T result = body.apply(session);
            session.sql("COMMIT").execute();
            return result;
        } catch (Exception ex) {
            try {
                session.sql("ROLLBACK").execute();
            } catch (Exception e) {
                // noop
            }
            return ExceptionUtils.rethrow(ex);
        }
    }
}