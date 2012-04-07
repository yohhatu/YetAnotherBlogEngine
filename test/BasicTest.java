import org.junit.*;
import java.util.*;

import javax.jws.soap.SOAPBinding.Use;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@SuppressWarnings("deprecation")
	@Before
	public void setup(){
		Fixtures.deleteAll();
	}

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

    @Test
    public void createPost(){
    	User bob = new User("bob@example.com", "secret", "Bob").save();

    	new Post(bob, "My first post", "Hello world").save();

    	assertEquals(1, Post.count());

    	List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

    	assertEquals(1, bobPosts.size());
    	Post firstPost = bobPosts.get(0);
    	assertNotNull(firstPost);
    	assertEquals(bob, firstPost.author);
    	assertEquals("My first post", firstPost.title);
    	assertEquals("Hello world", firstPost.content);
    	assertNotNull(firstPost.postedAt);




    }
}
