package com.ttapractice.test.Integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttapractice.base.BaseAction;
import com.ttapractice.endpoints.APIConstant;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class Integration01 extends BaseAction {

    String TOKEN;
    Integer BOOKING_ID;

    @Test
    public void getToken() throws JsonProcessingException {
        requestSpecification.basePath(APIConstant.BASE_AUTH_URL);
        response = RestAssured.given().spec(requestSpecification).body(authModule.createAuthLoad()).when().post();
        validatableResponse = response.then().log().all();
//        #TC 1
        validatableResponse.statusCode(200);

        validatableResponse.body("token", Matchers.notNullValue());

        validatableResponse.body("token.length()", Matchers.equalTo(15));

        TOKEN = response.then().extract().path("token");

    }


    @Test(priority = 1)
    public void postRequest() throws JsonProcessingException {
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING);
        response = RestAssured.given().spec(requestSpecification).cookies("token", TOKEN).body(payloadModule.createPayload()).when().post();
        validatableResponse = response.then().log().all();

//        Validations
        validatableResponse.statusCode(200);
        validatableResponse.body("booking.firstname", Matchers.equalTo("Nishant"));
        BOOKING_ID = response.then().extract().path("bookingid");

    }


    @Test(priority = 2)
    public void deleteRequest() {
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING + "/" + BOOKING_ID);
        response= RestAssured.given().spec(requestSpecification).cookies("token",TOKEN).when().delete();
        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(201);

    }

    @Test(priority = 3)
    public void getBooking() {
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING + "/" + BOOKING_ID);
        response = RestAssured.given().spec(requestSpecification).cookies("token",TOKEN).when().get();
        validatableResponse = response.then().log().all();


        validatableResponse.statusCode(404);


    }


}
