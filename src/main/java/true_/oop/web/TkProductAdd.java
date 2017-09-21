package true_.oop.web;

import org.javamoney.moneta.FastMoney;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsJson;
import true_.oop.api.Base;
import true_.oop.api.Product;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Create product.
 */
final class TkProductAdd implements Take {

    private final Base base;

    TkProductAdd(Base base) {
        this.base = base;
    }

    @Override
    public Response act(Request req) throws IOException {
        try {
            JsonReader reader = Json.createReader(req.body());
            JsonObject json = reader.readObject();
            String name = json.getString("name");
            String desc = json.getString("desc");
            MonetaryAmount price = FastMoney.of(new BigDecimal(json.getString("price")), "CZK");
            Product result = base.products().add(name, desc, price);
            return new RsJson(result);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
