package featureTests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class BookingPutFeatureTest {
    private static String jsonBody = """
                {
                    "firstname" : "Ödön",
                    "lastname" : "Szerencsi",
                    "totalprice" : 987,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2024-01-01",
                        "checkout" : "2024-01-16"
                    },
                    "additionalneeds" : "Lunch"
                }""";
    private static String putJsonBody = """
                {
                    "firstname" : "Lilla",
                    "lastname" : "Lali",
                    "totalprice" : 9999,
                    "depositpaid" : false,
                    "bookingdates" : {
                        "checkin" : "2010-11-01",
                        "checkout" : "2011-11-21"
                    },
                    "additionalneeds" : "Dinner"
                }""";
    @Test
    public void noHeaderNoBodyPutRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void headerWithAuthNoValueContentTypeNoAcceptNoBodyPutRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","")
                .header("Accept","")
                .header("Cookie","token=" + token)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void headerWithAuthInvalueContentTypeInvalidAcceptNoBodyPutRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","text/html")
                .header("Accept","text/html")
                .header("Cookie","token=" + token)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
}
