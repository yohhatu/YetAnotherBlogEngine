import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }


    @Test
    public void testAdminSecurity() {
        Response response = GET("/admin/users");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/secure/login", response);

        response = GET("/admin/posts");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/secure/login", response);

        response = GET("/admin/comments");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/secure/login", response);

        response = GET("/admin/tags");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/secure/login", response);

    }

}