package smokeTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SmokeTest {


    //todo authEndpointExistTest should be in security test
    @Test
    public void authEndpointExistTest() {
        setBaseURL();
        when().post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(415);
    }

    @Test
    public void bookGetEndPointExistsTest() {
        setBaseURL();
        when().
                get(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void pingGetEndpointExistsTest() {
        setBaseURL();
        when()
                .get(SharedVariables.pingEndpoint)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void usernameAndPasswordAuthTest() {
        setBaseURL();
        given()
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post(SharedVariables.authEndpoint)
                .then()
                .assertThat()
                .statusCode(200)
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
                .post(SharedVariables.bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(200)
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
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        //put the created booking
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(jsonBody)
                .when()
                .put(SharedVariables.bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(200)
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
                .delete(SharedVariables.bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(201);
    }
    @Test
    public void bookingIDEndpointTest() {
        String jsonBody = """
                {
                    "firstname" : "Nyuszi",
                    "lastname" : "Muszi",
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
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(SharedVariables.bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .body();
    }
    private void setBaseURL() {
        RestAssured.baseURI = SharedVariables.basicURL;
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
                .statusCode(200)
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
                .statusCode(200)
                .extract()
                .response();
        return List.of(authResponse, idResponse);
    }
}
