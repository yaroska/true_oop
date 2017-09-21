package true_.oop.fake;

import true_.oop.api.Category;

import javax.json.Json;
import javax.json.JsonStructure;
import java.util.Optional;

public class FkCategory implements Category {

    @Override
    public long id() {
        return 0;
    }

    @Override
    public Category update(String name, Optional<Category> parent) {
        return new FkCategory();
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder().build();
    }
}
