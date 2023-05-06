package com.ttapractice.test.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttapractice.base.BaseAction;
import com.ttapractice.endpoints.APIConstant;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class PUTRequest extends BaseAction {



    @Test(priority = 1)
    public void postRequest(ITestContext iTestContext) throws JsonProcessingException {
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING);
        String TOKEN = (String) iTestContext.getAttribute("token");
        response = RestAssured.given().spec(requestSpecification).cookies("token", TOKEN).body(payloadModule.createPayload()).when().post();

        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(200);

        validatableResponse.body("booking.firstname", Matchers.is("Nishant"));

        Integer BOOKINGID = response.then().extract().path("bookingid");
        iTestContext.setAttribute("bookingid", BOOKINGID);


    }

    @Test(priority = 2)
    public void putRequest(ITestContext iTestContext) throws JsonProcessingException {
        String TOKEN = (String) iTestContext.getAttribute("token");
        Integer BOOKINGID = (Integer) iTestContext.getAttribute("bookingid");

        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING + "/" + BOOKINGID);

        response = RestAssured.given().spec(requestSpecification).cookies("token", TOKEN).body(payloadModule.updatePayload()).when().put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        validatableResponse.body("lastname", Matchers.equalTo("Khanna"));

    }
}
