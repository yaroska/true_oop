package true_.oop.web;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.RqFallback;
import org.takes.facets.fallback.TkFallback;
import org.takes.misc.Opt;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;
import org.takes.tk.TkWrap;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * App with fallback.
 */
final class TkAppFallback extends TkWrap {

    TkAppFallback(Take take) {
        super(make(take));
    }

    private static Take make(final Take take) {
        return new TkFallback(
                take,
                new FbChain(
                        new FbStatus(
                                HttpURLConnection.HTTP_NOT_FOUND,
                                new RsWithStatus(
                                        new RsText("endpoint with this path not found"),
                                        HttpURLConnection.HTTP_NOT_FOUND
                                )
                        ),
                        new FbStatus(
                                HttpURLConnection.HTTP_BAD_REQUEST,
                                new RsWithStatus(
                                        new RsText("bad request"),
                                        HttpURLConnection.HTTP_BAD_REQUEST
                                )
                        ),
                        req -> new Opt.Empty<>(),
                        req -> new Opt.Single<>(fatal(req))
                )
        );
    }

    private static Response fatal(final RqFallback req) throws IOException {
        return new RsWithStatus(
                new RsText(ExceptionUtils.getStackTrace(req.throwable())),
                HttpURLConnection.HTTP_INTERNAL_ERROR
        );
    }
}
