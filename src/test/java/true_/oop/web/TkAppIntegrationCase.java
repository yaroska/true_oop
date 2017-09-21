package true_.oop.web;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.RestResponse;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.http.FtRemote;
import true_.oop.fake.FkBase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Integration test.
 */
public final class TkAppIntegrationCase {

    @Test
    public void returnsTextPageOnHttpRequest() throws Exception {
        new FtRemote(new TkApp(new FkBase())).exec(
                new FtRemote.Script() {
                    @Override
                    public void exec(final URI home) throws IOException {
                        new JdkRequest(home)
                                .fetch()
                                .as(RestResponse.class)
                                .assertStatus(HttpURLConnection.HTTP_OK)
                                .assertBody(Matchers.equalTo("try /categories"));
                    }
                }
        );
    }
}