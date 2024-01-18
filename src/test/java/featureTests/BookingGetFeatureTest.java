package featureTests;

import org.junit.Test;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.when;

public class BookingGetFeatureTest {

    @Test
    public void bookingGetByIdWithNegativeIDTest() {
        setBaseURL();
        when()
                .get(bookingEndpoint + "/-1")
                .then()
                .assertThat()
                .statusCode(notFound)
                .log().all();
    }

    @Test
    public void bookingGetByIdWithNullIDTest() {
        setBaseURL();
        when()
                .get(bookingEndpoint + "/0")
                .then()
                .assertThat()
                .statusCode(notFound)
                .log().all();
    }

    @Test
    public void bookingGetByIdWithBigIntIDTest() {
        setBaseURL();
        when()
                .get(bookingEndpoint + "/4000000000000000")
                .then()
                .assertThat()
                .statusCode(notFound)
                .log().all();
    }

    @Test
    public void bookingGetByIdWithStringIDTest() {
        setBaseURL();
        when()
                .get(bookingEndpoint + "/abc")
                .then()
                .assertThat()
                .statusCode(notFound)
                .log().all();
    }
}
