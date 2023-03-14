package jovami;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Greeter
 */
@Entity
public class Greeter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected Greeter() {}

    public String sayHello() {
        return "Hello world!";
    }
}
