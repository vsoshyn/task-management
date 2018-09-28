import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    Main main = new Main();


    @Test
    void hello() {
        assertEquals("Hello1", main.hello());
    }
}