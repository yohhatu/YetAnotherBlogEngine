import org.junit.*;
import java.util.*;

import javax.jws.soap.SOAPBinding.Use;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void createAndRetrieveUser(){
    	new User("bob@example.com", "secret", "Bob").save();

    	User bob = User.find("byEmail", "bob@example.com").first();

    	assertNotNull(bob);
    	assertEquals("Bob", bob.fullname);
    }

    @Test
    public void tryConnectAsUser(){
    	new User("bob@example.com", "secret", "Bob").save();

    	assertNotNull(User.connect("bob@example.com", "secret"));
    	assertNull(User.connect("bob@example.com", "badpassword"));
    	assertNull(User.connect("tom@example.com", "secret"));

    }

}
