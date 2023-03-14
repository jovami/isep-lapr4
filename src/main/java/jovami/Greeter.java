package jovami;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Greeter
 */

public class Greeter {
    protected Greeter() {}

    public String sayHello() {
        return "Hello world!";
    }
}
