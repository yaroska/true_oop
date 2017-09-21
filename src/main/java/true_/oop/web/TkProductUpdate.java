package true_.oop.web;

import org.javamoney.moneta.FastMoney;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsJson;
import true_.oop.api.Base;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Update product.
 */
final class TkProductUpdate implements Take {

    private final Base base;

    TkProductUpdate(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        try {
            long number = Long.parseLong(((RqRegex) req).matcher().group("number"));
            Product product = base.products().product(number);

            JsonReader reader = Json.createReader(req.body());
            JsonObject json = reader.readObject();
            String name = json.getString("name");
            String desc = json.getString("desc");
            FastMoney price = FastMoney.of(new BigDecimal(json.getString("price")), "CZK");

            Product result = product.update(name, desc, price);
            return new RsJson(result);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
