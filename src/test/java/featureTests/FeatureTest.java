package featureTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.when;

public class FeatureTest {
    private void setBaseURL() {
        RestAssured.baseURI = SharedVariables.basicURL;
    }

    @Test
    public void emptyRequestToAuthEndpointTest() {
        setBaseURL();
        when().post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(415);
    }
}
