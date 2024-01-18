package com.MindMySelf;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public final class SharedVariables {
    public static String basicURL = "https://restful-booker.herokuapp.com";
    public static String authEndpoint = "/auth";
    public static String bookingEndpoint = "/booking";
    public static String pingEndpoint = "/ping";
    public static int badRequest = 400;
    public static int UnsupportedMediaType = 415;
    public static int OK = 200;
    public static int created = 201;
    public static int notFound = 404;
    public static void setBaseURL() {
        RestAssured.baseURI = SharedVariables.basicURL;
    }
    public static List<Response> setupAuthAndCreateBooking(String jsonBody) {
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
