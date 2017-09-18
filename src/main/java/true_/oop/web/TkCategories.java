package true_.oop.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJson;
import true_.oop.api.Base;

import java.io.IOException;

final class TkCategories implements Take {

    private final Base base;

    TkCategories(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        try {
            return new RsJson(base.categories());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
