package true_.oop;

import org.takes.http.Exit;
import org.takes.http.FtCli;
import true_.oop.api.Base;
import true_.oop.h2.H2Base;
import true_.oop.web.TkApp;

/**
 * Main class
 */
public final class Warehouse {

    public static void main(String[] args) throws Exception {

        Base base = new H2Base();
        new FtCli(new TkApp(base), args).start(Exit.NEVER);
    }

    /**
     * Utility class.
     */
    private Warehouse() {
        throw new IllegalStateException("Utility class");
    }
}
