package com.ttapractice.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttapractice.endpoints.APIConstant;
import com.ttapractice.module.AuthModule;
import com.ttapractice.module.PayloadModule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BaseAction {

    protected RequestSpecification requestSpecification;
    protected ValidatableResponse validatableResponse;

    protected Response response;
    protected PayloadModule payloadModule = new PayloadModule();
    protected AuthModule authModule = new AuthModule();


    @BeforeMethod
    protected void setUpBeforeRequest() {
        requestSpecification = new RequestSpecBuilder().setBaseUri(APIConstant.BASE_URL)
                .addHeader("Content-Type", "application/json").
                build().log().all();

    }

    @Test
    protected void auth(ITestContext iTestContext) throws JsonProcessingException {
        response = RestAssured.given().spec(requestSpecification).basePath(APIConstant.BASE_AUTH_URL).body(authModule.createAuthLoad()).when().post();
        validatableResponse=response.then().log().all();
        JsonPath jsonPath= response.jsonPath();
        iTestContext.setAttribute("token", jsonPath.get("token"));

    }

}
