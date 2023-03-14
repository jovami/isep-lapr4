package jovami;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    private static EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
            createEntityManagerFactory("jovami");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

	public static void main(String[] args) {
        var em = getEntityManager();
        var t = em.getTransaction();


		Greeter greeter = new Greeter();
		System.out.println(greeter.sayHello());

        t.begin();
        em.persist(greeter);
        t.commit();
        em.close();
	}
}
