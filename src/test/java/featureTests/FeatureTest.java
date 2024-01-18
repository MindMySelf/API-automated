package featureTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;
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
    @Test
    public void emptyRequestBodyToAuthEndpointTest() {
        setBaseURL();
        given()
                .header("Content-Type","application/json")
                .when()
                .post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(400);
    }

}
