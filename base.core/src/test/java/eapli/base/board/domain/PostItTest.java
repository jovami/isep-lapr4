package eapli.base.board.domain;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostItTest {
    private SystemUser owner;
    private SystemUser owner2;
    private PostIt postIt1;
    private PostIt postIt2;
    @Before
    public void setup() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());

        owner = userBuilder.with("tony", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        owner2 = userBuilder.with("tony123", "Password1", "Alexandre", "Moreira", "alexmore132ira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        postIt1 = new PostIt(owner, "Initial data");
        postIt2 = new PostIt(owner);

    }
    @Test
    public void testConstructorAndGetters() {
        assertEquals(owner, postIt1.getOwner());
        assertTrue(postIt1.hasData());
        assertEquals("Initial data", postIt1.getData());
    }

    @Test
    public void testAlterPostItData() {
        postIt1.alterPostItData("Updated data");

        assertEquals("Updated data", postIt1.getData());
    }


    @Test
    public void testConstructorWithOwner() {
        assertEquals(owner, postIt2.getOwner());
        assertFalse(postIt2.hasData());
        assertNull(postIt2.getData());
    }

    @Test
    public void testEqualsAndHashCode() {
        PostIt samePostIt1 = new PostIt(owner, "Initial data");
        PostIt differentPostIt1 = new PostIt(owner2);
        PostIt differentPostIt2 = new PostIt(owner, "Different data");

        assertEquals(postIt1, samePostIt1);
        assertNotEquals(postIt1, differentPostIt1);
        assertNotEquals(postIt1, differentPostIt2);

        assertEquals(postIt1.hashCode(), samePostIt1.hashCode());
        assertNotEquals(postIt1.hashCode(), differentPostIt1.hashCode());
        assertNotEquals(postIt1.hashCode(), differentPostIt2.hashCode());
    }


}