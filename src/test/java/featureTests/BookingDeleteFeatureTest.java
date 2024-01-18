package featureTests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class BookingDeleteFeatureTest {
    private static String jsonBody = """
                {
                    "firstname" : "Test",
                    "lastname" : "Testtest",
                    "totalprice" : 123,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2024-01-01",
                        "checkout" : "2024-01-11"
                    },
                    "additionalneeds" : "Lunch"
                }""";
    @Test
    public void noHeaderDeleteRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        when()
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().all();
    }
    @Test
    public void headerWithOnlyValidAuthDeleteRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Cookie","token=" + token)
                .when()
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(created)
                .log().all();
    }
}
