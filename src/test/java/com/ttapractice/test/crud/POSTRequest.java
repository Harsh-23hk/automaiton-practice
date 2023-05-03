package com.ttapractice.test.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttapractice.base.BaseAction;
import com.ttapractice.endpoints.APIConstant;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class POSTRequest extends BaseAction {


    @Test
    public void getToken(ITestContext iTestContext) throws JsonProcessingException {
        requestSpecification.basePath(APIConstant.BASE_AUTH_URL);
        response = RestAssured.given().spec(requestSpecification).body(authModule.createAuthLoad()).when().post();
        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(200);

        validatableResponse.body("token.length()", Matchers.is(15));

        validatableResponse.body("token", Matchers.notNullValue());

        JsonPath jsonPath = response.jsonPath();
        iTestContext.setAttribute("token", jsonPath.get("token"));


    }

    @Test(dependsOnMethods= {"getToken"})
    public void postRequest(ITestContext iTestContext) throws JsonProcessingException {
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING);
        String TOKEN = (String) iTestContext.getAttribute("token");
        response = RestAssured.given().spec(requestSpecification).cookies("token", TOKEN).body(payloadModule.createPayload()).when().post();

        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(200);

        validatableResponse.body("booking.firstname", Matchers.equalTo("Nishant"));

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(jsonPath.getString("booking.lastname"),"Khanna","checking lastname of the booking");

    }
}
