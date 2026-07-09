package com.qa_appium_mobile_demo.base;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.qa_appium_mobile_demo.utils.TestUtilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PropertyConfigurator {

    private static String BASE_URI = "[https://your-server-url.com]"; // Replace with your actual server URL
    private static String BASE_JSON = "./src/main/resources/config/properties.json";
    private static PropertyConfigurator instance;

    private PropertyConfigurator() {}

    public static synchronized PropertyConfigurator getInstance() {
        if (instance == null) {
            instance = new PropertyConfigurator();
            setBaseUri();
        }
        return instance;
    }

    private static void setBaseUri() {
        RestAssured.baseURI = BASE_URI;
    }

    public static Response getPropertyConfigurator(String configurator) {
        //TestUtilities.log("Getting configurator: " + configurator);
        Response response = null;
        try {
            return response = given()
                    .contentType(ContentType.JSON) 
                    .when()
                    .get(configurator)
                    .then()
                    .extract()
                    .response();
        } catch (AssertionError e) {
            TestUtilities.log("Error fetching configurator: " + e.getMessage());
            return response;
        }
    }

    public static JSONObject getDriverConfigurator() {
        JSONObject response = null;
        try {
            JSONParser jparser = new JSONParser();
            FileReader reader = new FileReader(BASE_JSON);
            Object objJson = jparser.parse(reader);
            JSONObject jsonData = (JSONObject) objJson;
            return response = ((JSONObject) jsonData.get("driverSetup"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        } catch (ParseException e) {
            e.printStackTrace();
            return response;
        }
    }

    public static JSONObject getTestConfigurator(String environment) {
        JSONObject response = null;
        try {
            JSONParser jparser = new JSONParser();
            FileReader reader = new FileReader(BASE_JSON);
            Object objJson = jparser.parse(reader);
            JSONObject jsonData = (JSONObject) objJson;
            return response = ((JSONObject) jsonData.get(environment));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        } catch (ParseException e) {
            e.printStackTrace();
            return response;
        }
    }
}