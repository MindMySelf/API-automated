package smokeTests;

import com.MindMySelf.SharedVariables;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SmokeTest {
    @Test
    public void bookGetEndPointExistsTest() {
        SharedVariables.setBaseURL();
        when().
                get(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.OK)
                .log().body();
    }

    @Test
    public void pingGetEndpointExistsTest() {
        SharedVariables.setBaseURL();
        when()
                .get(SharedVariables.pingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.created);
    }

    @Test
    public void usernameAndPasswordAuthTest() {
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.OK)
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
        SharedVariables.setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonBody)
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.OK)
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
        SharedVariables.setBaseURL();
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
                .put(SharedVariables.bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(SharedVariables.OK)
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
        SharedVariables.setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when()
                .delete(SharedVariables.bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(SharedVariables.created);
    }

    private List<Response> setupAuthAndCreateBooking(String jsonBody) {
        //auth
        Response authResponse = given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.OK)
                .extract()
                .response();
        //create booking
        Response idResponse = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonBody)
                .when()
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(SharedVariables.OK)
                .extract()
                .response();
        return List.of(authResponse, idResponse);
    }
}
