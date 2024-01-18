package featureTests;

import org.junit.Test;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AuthFeatureTest {

    @Test
    public void emptyRequestToAuthEndpointTest() {
        setBaseURL();
        when().post(authEndpoint)
                .then()
                .assertThat()
                .statusCode(UnsupportedMediaType);
    }

    @Test
    public void emptyRequestBodyToAuthEndpointTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .when()
                .post(authEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log()
                .body();
    }

    @Test
    public void wrongUsernameAndPasswordRequestBodyToAuthEndpointTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"kacsa\", \"password\": \"kulcs\"}")
                .when()
                .post(authEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log()
                .body();
    }

    @Test
    public void wrongPasswordRequestBodyToAuthEndpointTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"kulcs\"}")
                .when()
                .post(authEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log()
                .body();
    }

}
