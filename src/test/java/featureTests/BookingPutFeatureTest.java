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
    public void headerWithAuthNoValidContentTypeNoAcceptNoBodyPutRequestToBookingTest() {
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
    public void headerWithAuthInvalidContentTypeInvalidAcceptNoBodyPutRequestToBookingTest() {
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
    @Test
    public void headerWithAuthValidContentTypeInvalidAcceptNoBodyPutRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","text/html")
                .header("Cookie","token=" + token)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void headerWithAuthInvalidContentTypeValidAcceptNoBodyPutRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","text/html")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void validHeaderEmptyBodyPutRequestToBookingTest() {
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .body("")
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void validHeaderBodyWithFieldsWithNoValuePutRequestToBookingTest() {
        String putJsonBody = """
                {
                    "firstname" : "",
                    "lastname" : "",
                    "totalprice" : ,
                    "depositpaid" : ,
                    "bookingdates" : {
                        "checkin" : "",
                        "checkout" : ""
                    },
                    "additionalneeds" : ""
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .body(putJsonBody)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void validHeaderBodyWithMissingFieldsAndNoValuePutRequestToBookingTest() {
        String putJsonBody = """
                {
                    "firstname" : "",
                    "lastname" : "",
                    "totalprice" : ,
                    "depositpaid" : 
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .body(putJsonBody)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void validHeaderBodyWithValidFieldsInValidTypeValuesPutRequestToBookingTest() {
        String putJsonBody = """
                {
                    "firstname" : 123,
                    "lastname" : "2023-12-30",
                    "totalprice" : "nana",
                    "depositpaid" : 0.232,
                    "bookingdates" : {
                        "checkin" : "Elmo@lemon.com",
                        "checkout" : 342134556
                    },
                    "additionalneeds" : true
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .body(putJsonBody)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void validHeaderBodyWithValidFieldsValidTypeInvalidValuesPutRequestToBookingTest() {
        String putJsonBody = """
                {
                    "firstname" : "/*/165",
                    "lastname" : "@&#>|°-",
                    "totalprice" : -987,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "100-12-01",
                        "checkout" : "2824-01-16"
                    },
                    "additionalneeds" : "°˛`˙˛[]"
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .body(putJsonBody)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
    @Test
    public void validHeaderBodyWithValidFieldsValidValuesAndPlusFieldsNoValuesPutRequestToBookingTest() {
        String putJsonBody = """
                {
                    "firstname" : "Sara",
                    "lastname" : "Doe",
                    "totalprice" : 12,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "1990-12-01",
                        "checkout" : "1992-01-16"
                    },
                    "additionalneeds" : "Lunch",
                    "money": 1234,
                    "email": "test@test.com"
                }""";
        setBaseURL();
        List<Response> tokenAndID = setupAuthAndCreateBooking(jsonBody);
        String token = tokenAndID.get(0).path("token");
        int id = tokenAndID.get(1).path("bookingid");
        given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token=" + token)
                .body(putJsonBody)
                .when()
                .put(bookingEndpoint + "/" + id)
                .then()
                .assertThat()
                .statusCode(badRequest)
                .log().body();
    }
}
