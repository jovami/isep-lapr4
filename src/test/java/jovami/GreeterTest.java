package jovami;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

// import static org.hamcrest.CoreMatchers.containsString;
// import static org.junit.juput.Assert.*;

// import org.junit.Test;

/**
  A comment
  */
public class GreeterTest {

    private Greeter greeter = new Greeter();

    @Test
    public void greeterSaysHello() {
        assertThat(greeter.sayHello(), containsString("Hello"));
    }

}
