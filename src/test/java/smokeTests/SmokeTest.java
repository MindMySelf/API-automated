package smokeTests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static com.MindMySelf.SharedVariables.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SmokeTest {
    @Test
    public void bookGetEndPointExistsTest() {
        setBaseURL();
        when().
                get(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(OK)
                .log().body();
    }

    @Test
    public void pingGetEndpointExistsTest() {
        setBaseURL();
        when()
                .get(pingEndpoint)
                .then()
                .assertThat()
                .statusCode(created);
    }

    @Test
    public void usernameAndPasswordAuthTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post(authEndpoint)
                .then()
                .assertThat()
                .statusCode(OK)
                .log().body();
    }

    @Test
    public void bookingPostEndpointTest() {
        String jsonBody = "{\"firstname\": \"Willy\", \"lastname\": \"Wonka\"," +
                " \"totalprice\": \"666\"," +
                "\"depositpaid\": \"true\"," +
                "\"bookingdates\": {" +
                "\"checkin\": \"2020-01-01\"," +
                "\"checkout\": \"2020-01-11\"" +
                "}," +
                "\"additionalneeds\": \"Lunch\"}";
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonBody)
                .when()
                .post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(OK)
                .log().body();
    }

    @Test
    public void bookingPutEndpointTest() {
        String jsonBody = """
                {
                    "firstname" : "Maci",
                    "lastname" : "Laci",
                    "totalprice" : 123,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2023-01-01",
                        "checkout" : "2023-01-21"
                    },
                    "additionalneeds" : "Lunch"
                }""";
        String putJsonBody = """
                {
                    "firstname" : "Naci",
                    "lastname" : "Laci",
                    "totalprice" : 1234,
                    "depositpaid" : false,
                    "bookingdates" : {
                        "checkin" : "2023-01-01",
                        "checkout" : "2023-01-21"
                    },
                    "additionalneeds" : "Dinner"
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        //put the created booking
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(putJsonBody)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(OK)
                .log().all();
    }

    @Test
    public void bookingDeleteEndpointTest() {
        String jsonBody = """
                {
                    "firstname" : "Kicsi",
                    "lastname" : "Csacsi",
                    "totalprice" : 425,
                    "depositpaid" : false,
                    "bookingdates" : {
                        "checkin" : "2023-01-01",
                        "checkout" : "2023-01-21"
                    },
                    "additionalneeds" : "Lunch"
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when()
                .delete(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(created);
    }
}
