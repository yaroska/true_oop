package true_.oop.web;

import org.takes.Take;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.TkForward;
import org.takes.tk.TkSlf4j;
import org.takes.tk.TkWrap;
import true_.oop.api.Base;

/**
 * Web App.
 * Endpoints configuration.
 */
public final class TkApp extends TkWrap {

    public TkApp(Base base) {
        super(make(base));
    }

    private static Take make(Base base) {
        return new TkAppFallback(
                new TkSlf4j(
                        new TkForward(
                                new TkFork(
                                        new FkRegex("/", "try /categories"),
                                        new FkRegex("/categories", new TkFork(
                                                new FkMethods("GET", new TkCategories(base)),
                                                new FkMethods("POST,PUT", new TkCategoryAdd(base))
                                        )),
                                        new FkRegex("/categories/(?<number>[^/]+)", new TkFork(
                                                new FkMethods("GET", new TkCategory(base)),
                                                new FkMethods("POST,PUT", new TkCategoryUpdate(base)),
                                                new FkMethods("DELETE", new TkCategoryDelete(base))
                                        )),
                                        new FkRegex("/products", new TkFork(
                                                new FkMethods("GET", new TkProducts(base)),
                                                new FkMethods("POST,PUT", new TkProductAdd(base))
                                        )),
                                        new FkRegex("/products/(?<number>[^/]+)", new TkFork(
                                                new FkMethods("GET", new TkProduct(base)),
                                                new FkMethods("POST,PUT", new TkProductUpdate(base)),
                                                new FkMethods("DELETE", new TkProductDelete(base))
                                        )),
                                        new FkRegex("/categories/(?<number>[^/]+)/products", new TkFork(
                                                new FkMethods("GET", new TkCategoryProducts(base)),
                                                new FkMethods("POST,PUT", new TkCategoryProductsAdd(base))
                                        )),
                                        new FkRegex("/categories/(?<ctgId>[^/]+)/products/(?<prdId>[^/]+)", new TkFork(
                                                new FkMethods("POST,PUT", new TkCategoryProductAdd(base)),
                                                new FkMethods("DELETE", new TkCategoryProductDelete(base))
                                        ))
                                )
                        )
                )
        );
    }
}
