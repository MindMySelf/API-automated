package featureTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AuthFeatureTest {
    private void setBaseURL() {
        RestAssured.baseURI = SharedVariables.basicURL;
    }

    @Test
    public void emptyRequestToAuthEndpointTest() {
        setBaseURL();
        when().post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.UnsupportedMediaType);
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
                .statusCode(SharedVariables.badRequest)
                .log()
                .body();
    }
    @Test
    public void wrongUsernameAndPasswordRequestBodyToAuthEndpointTest() {
        setBaseURL();
        given()
                .header("Content-Type","application/json")
                .body("{\"username\": \"kacsa\", \"password\": \"kulcs\"}")
                .when()
                .post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.badRequest)
                .log()
                .body();
    }
    @Test
    public void wrongPasswordRequestBodyToAuthEndpointTest() {
        setBaseURL();
        given()
                .header("Content-Type","application/json")
                .body("{\"username\": \"admin\", \"password\": \"kulcs\"}")
                .when()
                .post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.badRequest)
                .log()
                .body();
    }

}
