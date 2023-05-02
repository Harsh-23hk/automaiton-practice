package com.ttapractice.base;

import com.ttapractice.endpoints.APIConstant;
import com.ttapractice.module.AuthModule;
import com.ttapractice.module.PayloadModule;
import com.ttapractice.payload.pojos.Bookingdates;
import com.ttapractice.payload.pojos.Bookings;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

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


}
