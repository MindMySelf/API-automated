package smokeTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SmokeTest {
    private final SharedVariables sharedVariables = new SharedVariables();

    private void setBaseURL() {
        RestAssured.baseURI = sharedVariables.basicURL;
    }

    @Test
    public void authEndpointExistTest() {
        setBaseURL();
        when().post(sharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(415);
    }

    @Test
    public void bookGetEndPointExistsTest() {
        setBaseURL();
        when().
                get(sharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void pingGetEndpointExistsTest() {
        setBaseURL();
        when()
                .get(sharedVariables.pingEndpoint)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void usernameAndPasswordAuthTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post(sharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }
    @Test
    public void bookingPostEndpointTest() {
        String jsonBody = "{\"firstname\": \"Willy\", \"lastname\": \"Wonka\"," +
                " \"totalprice\": \"666\"," +
                "\"depositpaid\": \"true\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"2020-01-01\"," +
                "\"checkout\": \"2020-01-11\"" +
                "}," +
                "\"additionalneeds\": \"Lunch\"}";
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .body(jsonBody)
                .when()
                .post(sharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }

}
