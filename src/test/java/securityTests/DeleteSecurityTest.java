package securityTests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static com.MindMySelf.SharedVariables.*;
import static com.MindMySelf.SharedVariables.forbidden;
import static io.restassured.RestAssured.given;

public class DeleteSecurityTest {
    private static String dummyToken = "abc123";
    private static String jsonBody = """
            {
                "firstname" : "TestThree",
                "lastname" : "TestFore",
                "totalprice" : 1234,
                "depositpaid" : false,
                "bookingdates" : {
                    "checkin" : "2024-01-18",
                    "checkout" : "2024-01-19"
                },
                "additionalneeds" : "TestTest"
            }""";

    @Test
    public void dummyTokenAuthDeleteRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "")
                .header("Accept", "")
                .header("Cookie", "token=" + dummyToken)
                .when()
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void usernameAndPasswordAuthDeleteRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "application/json")
                .when()
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void emptyTokenAuthDeleteRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "")
                .header("Accept", "")
                .header("Cookie", "token=")
                .when()
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
    @Test
    public void randomTokenAuthDeleteRequestTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "")
                .header("Accept", "")
                .header("Cookie", "token=21341eabveq")
                .when()
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(forbidden)
                .log().body();
    }
}
