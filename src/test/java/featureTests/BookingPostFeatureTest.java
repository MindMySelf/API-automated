package featureTests;

import org.junit.Test;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class BookingPostFeatureTest {
    @Test
    public void emptyHeaderAndEmptyBodyPostRequestToBookingTest() {
        setBaseURL();
        when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(UnsupportedMediaType);
    }

    @Test
    public void wrongHeaderAndEmptyBodyPostRequestToBookingTest() {
        setBaseURL();
        given()
                .header("Content-Type", "text/plain")
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(UnsupportedMediaType);
    }

    @Test
    public void correctHeaderAndEmptyBodyPostRequestToBookingTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest);
    }

    @Test
    public void correctHeaderAndBodyWithMissingFieldsPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"Willy\", \"lastname\": \"Wonka\"," +
                " \"totalprice\": \"666\"," +
                "\"depositpaid\": \"true\"}";
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest);
    }

    @Test
    public void correctHeaderAndBodyWithAllFieldsMissingValuesPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"\", \"lastname\": \"\"," +
                " \"totalprice\": \"\"," +
                "\"depositpaid\": \"\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"\"," +
                "\"checkout\": \"\"" +
                "}," +
                "\"additionalneeds\": \"\"}";
        ;
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest);
    }

    @Test
    public void correctHeaderAndBodyWithAllFieldsWrongTypeValuesPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"123\", \"lastname\": \"abc@gmail.com\"," +
                " \"totalprice\": \"Ferrari\"," +
                "\"depositpaid\": \"...///*\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"tomorrow\"," +
                "\"checkout\": \"yesterday\"" +
                "}," +
                "\"additionalneeds\": \"2020-12-12\"}";
        ;
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest);
    }

    @Test
    public void correctHeaderAndBodyWithAdditionalFieldsPostRequestToBookingTest() {
        String jsonBody = "{\"firstname\": \"123\", \"lastname\": \"abc@gmail.com\"," +
                " \"totalprice\": \"Ferrari\"," +
                "\"depositpaid\": \"...///*\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"tomorrow\"," +
                "\"checkout\": \"yesterday\"" +
                "}," +
                "\"additionalneeds\": \"2020-12-12\"," +
                "\"outdated\": \"true\",}";
        ;
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(badRequest);
    }
}
