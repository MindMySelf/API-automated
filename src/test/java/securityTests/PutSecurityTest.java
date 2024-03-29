package securityTests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.given;

public class PutSecurityTest {
    private static String dummyToken = "abc123";
    private static String jsonBody = """
            {
                "firstname" : "TestOne",
                "lastname" : "TestTwo",
                "totalprice" : 123,
                "depositpaid" : true,
                "bookingdates" : {
                    "checkin" : "2024-01-18",
                    "checkout" : "2024-01-19"
                },
                "additionalneeds" : "Test"
            }""";

    @Test
    public void dummyTokenAuthPutRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "")
                .header("Accept", "")
                .header("Cookie", "token=" + dummyToken)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void usernameAndPasswordAuthPutRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "application/json")
                .when()
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void emptyTokenAuthPutRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "")
                .header("Accept", "")
                .header("Cookie", "token=")
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void randomTokenAuthDPutRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "")
                .header("Accept", "")
                .header("Cookie", "token=21341eabveq")
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }

}
