package stepDefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;
import starter.BaseParams;
import starter.GenericActionFunctions;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.*;


public class GenericAction {
    @Steps
    GenericActionFunctions genFUN;

    @Given("^customer retrieves a list with below inputs$")
    public void customerRetrievesAListWithBelowInputs(DataTable dt) {
        List<List<String>> list = dt.raw();
        String apiName = "";
        HashMap<String, String> params = new HashMap<>();
        for (int i = 0; i < list.get(0).size(); i++) {
            if (list.get(0).get(i).equalsIgnoreCase("apiName")) {
                apiName = list.get(1).get(i);
            } else {
                params.put(list.get(0).get(i).toString(), list.get(1).get(i).toString());
            }
        }
        //All the base params except wa_key are defined through the feature file
        BaseParams baseParam = new BaseParams();
        params.put("wa_key", baseParam.getBaseParams());
        genFUN.serviceOperation(apiName, params);
    }

    @Given("^verifies whether the response code is '(\\d+)'$")
    public void verifies_whether_the_response_code_is(int responseCode) {
        restAssuredThat(lastResponse -> lastResponse.statusCode(equalTo(responseCode)));
    }


    @And("^verifies whether the following are available in the list$")
    public void verifiesWhetherTheFollowingAreAvailableInTheList(DataTable dt) {
        List<String> list = dt.asList(String.class);
        String apiName = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            genFUN.responseBodyValidation(list.get(i), apiName);
        }
    }


    @And("^validate whether the error message contains the following text$")
    public void validateWhetherTheErrorMessageContainsTheFollowingText(DataTable dt) {
        List<String> errormsg = dt.asList(String.class);
        String actualError = lastResponse().jsonPath().get("message");
        assertThat(errormsg.get(1), is(actualError));
    }
}
