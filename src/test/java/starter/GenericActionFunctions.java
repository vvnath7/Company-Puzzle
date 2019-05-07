package starter;

import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static starter.serviceEndpoints.*;


public class GenericActionFunctions {
    @Step("API Invocation")
    public void serviceOperation(String apiName, HashMap<String, String> paramsmap) {
        String endpoint = "";
        String manufacturerCode = "";
        switch (apiName) {
            case "MANUFACTURER":
                endpoint = MANUFACTURER.getUrl().toString();
                break;
            case "CARTYPE":
                endpoint = CARTYPE.getUrl().toString();
                break;
            case "BUILTYEAR":
                endpoint = BUILTYEAR.getUrl().toString();
                break;
            default:
                System.out.println("No such api exists");
        }
        String apiUrl = BASEURL.getUrl().toString() + endpoint;

        SerenityRest.given().params(paramsmap).when().get(apiUrl);
    }

    public void responsebodyvalidation(String expectedmanufacturer, String apiName) {
        JsonPath jsonPathEvaluator = lastResponse().jsonPath();
        if (apiName.equalsIgnoreCase("MANUFACTURER")) {
            EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
            String manufacturercode = variables.getProperty(expectedmanufacturer);
            String actualmanufacturer = jsonPathEvaluator.get("wkda." + manufacturercode);
            assertThat(expectedmanufacturer, is(actualmanufacturer));
        } else {
            String path = "wkda." + expectedmanufacturer;
            if (expectedmanufacturer.contains("'")) {
                expectedmanufacturer = expectedmanufacturer.replaceAll("^\'|\'$", "");
            }
            String actualmanufacturer = jsonPathEvaluator.get(path);
            assertThat(expectedmanufacturer, is(actualmanufacturer));
        }
    }
}