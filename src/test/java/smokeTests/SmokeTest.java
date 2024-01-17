package smokeTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.when;

public class SmokeTest {
    private final SharedVariables sharedVariables = new SharedVariables();

    private void setBaseURL() {
        RestAssured.baseURI = sharedVariables.basicURL;
    }

    @Test
    public void authEndpointExistTest() {
        setBaseURL();
        when().post(sharedVariables.authEndpoint).
                then().
                assertThat().
                statusCode(415);
    }

    @Test
    public void bookGetEndPointExistsTest() {
        setBaseURL();
        when().
                get(sharedVariables.bookingEndpoint).
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void pingGetEndpointExistsTest() {
        setBaseURL();
        when().
                get(sharedVariables.pingEndpoint).
                then().
                assertThat().
                statusCode(201);
    }

}
