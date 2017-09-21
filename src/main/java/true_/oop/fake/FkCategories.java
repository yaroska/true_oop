package true_.oop.fake;

import true_.oop.api.Categories;
import true_.oop.api.Category;

import javax.json.Json;
import javax.json.JsonStructure;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

final class FkCategories implements Categories {

    @Override
    public List<Category> iterate() {
        return Collections.emptyList();
    }

    @Override
    public Category add(String name, Optional<Category> parent) {
        return new FkCategory();
    }

    @Override
    public void delete(Category category) {
        // noop
    }

    @Override
    public Category category(long number) {
        return new FkCategory();
    }

    @Override
    public JsonStructure toJson() {
        return Json.createObjectBuilder().build();
    }
}
