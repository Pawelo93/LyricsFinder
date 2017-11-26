package hexfan.lyrics;

import static org.junit.Assert.*;

/**
 * Created by Pawel on 12.07.2017.
 */

public class AbstractTest {

    /**
     * Wait for the specific time using {@link Thread#sleep(long)}
     *
     * @param milliseconds The time we want to wait for in millis
     */
    protected void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            fail();
        }
    }
}
