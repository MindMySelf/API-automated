package featureTests;

import com.MindMySelf.SharedVariables;
import org.junit.Test;

import static io.restassured.RestAssured.when;

public class BookingGetFeatureTest {

    @Test
    public void bookingGetByIdWithNegativeIDTest() {
        SharedVariables.setBaseURL();
        when()
                .get(SharedVariables.bookingEndpoint + "/-1")
                .then()
                .assertThat()
                .statusCode(SharedVariables.notFound)
                .log().all();
    }

    @Test
    public void bookingGetByIdWithNullIDTest() {
        SharedVariables.setBaseURL();
        when()
                .get(SharedVariables.bookingEndpoint + "/0")
                .then()
                .assertThat()
                .statusCode(SharedVariables.notFound)
                .log().all();
    }

    @Test
    public void bookingGetByIdWithBigIntIDTest() {
        SharedVariables.setBaseURL();
        when()
                .get(SharedVariables.bookingEndpoint + "/4000000000000000")
                .then()
                .assertThat()
                .statusCode(SharedVariables.notFound)
                .log().all();
    }

    @Test
    public void bookingGetByIdWithStringIDTest() {
        SharedVariables.setBaseURL();
        when()
                .get(SharedVariables.bookingEndpoint + "/abc")
                .then()
                .assertThat()
                .statusCode(SharedVariables.notFound)
                .log().all();
    }
}
