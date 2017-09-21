package true_.oop.web;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;
import true_.oop.fake.FkBase;

/**
 * Unit test.
 */
public class TkAppTest {

    @Test
    public void returnsHttpResponse() throws Exception {
        MatcherAssert.assertThat(
                new RsPrint(
                        new TkApp(new FkBase()).act(new RqFake("GET", "/"))
                ).printBody(),
                Matchers.equalTo("try /categories")
        );
    }
}