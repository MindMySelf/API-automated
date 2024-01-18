package featureTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.when;

public class BookingGetFeatureTest {
    private void setBaseURL() {
        RestAssured.baseURI = SharedVariables.basicURL;
    }

    @Test
    public void bookingGetByIdWithNegativeIDTest() {
        setBaseURL();
        when()
                .get(SharedVariables.bookingEndpoint + "/-1")
                .then()
                .assertThat()
                .statusCode(404)
                .log().all();
    }
    @Test
    public void bookingGetByIdWithNullIDTest() {
        setBaseURL();
        when()
                .get(SharedVariables.bookingEndpoint + "/0")
                .then()
                .assertThat()
                .statusCode(404)
                .log().all();
    }
}
