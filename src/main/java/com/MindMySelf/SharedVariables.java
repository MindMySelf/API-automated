package com.MindMySelf;

import io.restassured.RestAssured;

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
}
