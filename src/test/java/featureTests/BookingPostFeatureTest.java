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
}
