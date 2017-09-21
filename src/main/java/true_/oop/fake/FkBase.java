package true_.oop.fake;

import true_.oop.api.*;

public final class FkBase implements Base {

    @Override
    public Categories categories() {
        return new FkCategories();
    }

    @Override
    public Products products() {
        return new FkProducts();
    }

    @Override
    public CategoryProducts categoryProducts(Category category) {
        return new FkCategoryProducts(category);
    }
}
