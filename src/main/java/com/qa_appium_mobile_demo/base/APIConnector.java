package com.qa_appium_mobile_demo.base;

import static io.restassured.RestAssured.given;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIConnector {

    private static String BASE_URI = "";
    private static String POST_TEST_RESULT_ENDPOINT = "";
    private static String POST_TEST_SUITE_ENDPOINT = "";
    private static String POST_TEST_REPORT_ENDPOINT = "";
    private static String APPLICATION_JSON_TYPE = "application/json";

    public APIConnector() {
        setBaseUri();
    }

    public void setBaseUri() {
        RestAssured.baseURI = BASE_URI;
    }

    public Response sendTestResult(JsonObject json) {
        Response response = null;
        try {
            return response = given()
                     .header("Content-type", APPLICATION_JSON_TYPE)
                     .and()
                     .body(json)
                     .when()
                     .post(POST_TEST_RESULT_ENDPOINT)
                     .then()
                     .extract()
                     .response();   
        } catch (AssertionError e) {
            return response;
        }
    }

    public Response sendTestReport(JsonObject json) {
        Response response = null;
        try {
            return response = given()
                     .header("Content-type", APPLICATION_JSON_TYPE)
                     .and()
                     .body(json)
                     .when()
                     .post(POST_TEST_REPORT_ENDPOINT)
                     .then()
                     .extract()
                     .response();   
        } catch (AssertionError e) {
            return response;
        }
    }

    public Response sendSuiteStatus(JsonObject json) {
        Response response = null;
        try {
            return response = given()
                     .header("Content-type", APPLICATION_JSON_TYPE)
                     .and()
                     .body(json)
                     .when()
                     .post(POST_TEST_SUITE_ENDPOINT)
                     .then()
                     .extract()
                     .response();   
        } catch (AssertionError e) {
            return response;
        }
    }
}
