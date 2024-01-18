package featureTests;

import com.MindMySelf.SharedVariables;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class BookingPostFeatureTest {
    @Test
    public void emptyHeaderAndEmptyBodyPostRequestToBookingTest() {
        SharedVariables.setBaseURL();
        when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.UnsupportedMediaType);
    }
    @Test
    public void wrongHeaderAndEmptyBodyPostRequestToBookingTest() {
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type","text/plain")
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.UnsupportedMediaType);
    }
    @Test
    public void correctHeaderAndEmptyBodyPostRequestToBookingTest() {
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type","application/json")
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.badRequest);
    }
    @Test
    public void correctHeaderAndMissingFieldsBodyPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"Willy\", \"lastname\": \"Wonka\"," +
                " \"totalprice\": \"666\"," +
                "\"depositpaid\": \"true\"}";
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type","application/json")
                .body(jsonBody)
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.badRequest);
    }
    @Test
    public void correctHeaderAndAllFieldsMissingValuesBodyPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"\", \"lastname\": \"\"," +
                " \"totalprice\": \"\"," +
                "\"depositpaid\": \"\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"\"," +
                "\"checkout\": \"\"" +
                "}," +
                "\"additionalneeds\": \"\"}";;
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type","application/json")
                .body(jsonBody)
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.badRequest);
    }
    @Test
    public void correctHeaderAndAllFieldsWrongTypeValuesBodyPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"123\", \"lastname\": \"abc@gmail.com\"," +
                " \"totalprice\": \"Ferrari\"," +
                "\"depositpaid\": \"...///*\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"tomorrow\"," +
                "\"checkout\": \"yesterday\"" +
                "}," +
                "\"additionalneeds\": \"2020-12-12\"}";;
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type","application/json")
                .body(jsonBody)
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.badRequest);
    }
}
