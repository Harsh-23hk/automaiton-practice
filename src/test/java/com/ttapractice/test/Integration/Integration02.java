package com.ttapractice.test.Integration;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import com.ttapractice.endpoints.APIConstant;
import com.ttapractice.payload.pojos.Auth;
import com.ttapractice.payload.pojos.Bookingdates;
import com.ttapractice.payload.pojos.Bookings;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Integration02 {
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;

    Auth auth=new Auth();
    Bookingdates bookingdates=new Bookingdates();
    Bookings booking=new Bookings();

    String TOKEN;
    Integer BOOKING_ID;

    Gson gson=new Gson();

    @BeforeTest
    public void getToken(){
        requestSpecification= RestAssured.given();
        requestSpecification.baseUri(APIConstant.BASE_URL);
        requestSpecification.basePath(APIConstant.BASE_AUTH_URL);
        requestSpecification.contentType(ContentType.JSON);

        auth.setUsername("admin");
        auth.setPassword("password123");

        String AUTH = gson.toJson(auth);

        requestSpecification.body(AUTH);
        response=requestSpecification.when().post();
        validatableResponse=response.then().log().all();
//        #TC 1
        validatableResponse.statusCode(200);

        validatableResponse.body("token", Matchers.notNullValue());

        validatableResponse.body("token.length()",Matchers.equalTo(15));

        TOKEN=response.then().extract().path("token");

    }


    @Test(priority = 0)
    public void postRequest(){
        requestSpecification= RestAssured.given();
        requestSpecification.baseUri(APIConstant.BASE_URL);
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING);
        requestSpecification.cookies("token",TOKEN);
        requestSpecification.contentType(ContentType.JSON);

        booking.setFirstname("Nishant");
        booking.setLastname("Khanna");
        booking.setTotalprice(2598);
        booking.setDepositpaid(true);
        bookingdates.setCheckin("2023-05-01");
        bookingdates.setCheckout("2023-05-02");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        String BOOKING = gson.toJson(booking);

        requestSpecification.body(BOOKING);
        response = requestSpecification.when().post();
        validatableResponse = response.then().log().all();

//        Validations
        validatableResponse.statusCode(200);
        validatableResponse.body("booking.firstname",Matchers.equalTo("Nishant"));

        BOOKING_ID=response.then().extract().path("bookingid");

    }


    @Test(priority = 1)
    public void deleteRequest(){
        requestSpecification= RestAssured.given();
        requestSpecification.baseUri(APIConstant.BASE_URL);
        requestSpecification.basePath(APIConstant.CREATE_GET_POST_URL_BOOKING+"/"+BOOKING_ID);
        requestSpecification.cookies("token",TOKEN);
        response=requestSpecification.when().delete();
        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(201);

    }


}
