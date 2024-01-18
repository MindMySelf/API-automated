package featureTests;

import com.MindMySelf.SharedVariables;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AuthFeatureTest {

    @Test
    public void emptyRequestToAuthEndpointTest() {
        SharedVariables.setBaseURL();
        when().post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.UnsupportedMediaType);
    }

    @Test
    public void emptyRequestBodyToAuthEndpointTest() {
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type", "application/json")
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
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type", "application/json")
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
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type", "application/json")
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
