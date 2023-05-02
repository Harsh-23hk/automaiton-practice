package com.ttapractice.test.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttapractice.base.BaseAction;
import com.ttapractice.endpoints.APIConstant;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
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

        String TOKEN = response.then().extract().path("token");
        iTestContext.setAttribute("token", TOKEN);


    }

    @Test(priority = 1)
    public void postRequest(ITestContext iTestContext) throws JsonProcessingException {
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING);
        String TOKEN = (String) iTestContext.getAttribute("token");
        response = RestAssured.given().spec(requestSpecification).cookies("token", TOKEN).body(payloadModule.createPayload()).when().post();

        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(200);

        validatableResponse.body("booking.firstname", Matchers.equalTo("Nishant"));

        Integer bookingid = response.then().extract().path("bookingid");
        iTestContext.setAttribute("bookingid", bookingid);


    }
}
