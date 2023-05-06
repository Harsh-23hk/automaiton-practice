package com.ttapractice.test.Integration;


import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import com.ttapractice.payload.pojos.Auth;
import com.ttapractice.payload.pojos.Bookingdates;
import com.ttapractice.payload.pojos.Bookings;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;


public class Integration03 {


    //baseurl =https://restful-booker.herokuapp.com
    //path =  /auth /booking


    /*String payloadAuth = "{\n" + "    \"username\" : \"admin\",\n" + "    \"password\" : \"password123\"\n" + "}";

    String payloadBody = "{\n" + "    \"firstname\" : \"Ajay\",\n" + "    \"lastname\" : \"Brown\",\n" + "    \"totalprice\" : 111,\n" + "    \"depositpaid\" : true,\n" + "    \"bookingdates\" : {\n" + "        \"checkin\" : \"2018-01-01\",\n" + "        \"checkout\" : \"2019-01-01\"\n" + "    },\n" + "    \"additionalneeds\" : \"Breakfast\"\n" + "}";

    String putload = "{\n" + "    \"firstname\" : \"James\",\n" + "    \"lastname\" : \"Brown\",\n" + "    \"totalprice\" : 111,\n" + "    \"depositpaid\" : true,\n" + "    \"bookingdates\" : {\n" + "        \"checkin\" : \"2018-01-01\",\n" + "        \"checkout\" : \"2019-01-01\"\n" + "    },\n" + "    \"additionalneeds\" : \"Breakfast\"\n" + "}";
*/
    @Test
    public void addUpdateBooking() {

       /* Map<String,String> payloadAuth=new HashMap<>();
        payloadAuth.put("username","admin");
        payloadAuth.put("password","password123");

        Map<String, Object> payloadBody=new HashMap<>();
        payloadBody.put("firstname","Ajay");
        payloadBody.put("lastname","brown");
        payloadBody.put("totalprice","111");
        payloadBody.put("depositpaid","true");
        Map<String,String>Bookingdates=new HashMap<>();
        Bookingdates.put("checkin","2018-01-01");
        Bookingdates.put("checkout","2019-01-01");
        payloadBody.put("bookingdates",Bookingdates);
        payloadBody.put("additionalneeds","Breakfast");*/

        /* Map<String, Object> autoload=new HashMap<>();
        autoload.put("firstname","James");
        autoload.put("lastname","brown");
        autoload.put("totalprice","111");
        autoload.put("depositpaid","true");
        Map<String,String>bookingdates=new HashMap<>();
        bookingdates.put("checkin","2018-01-01");
        bookingdates.put("checkout","2019-01-01");
        autoload.put("bookingdates",bookingdates);
        autoload.put("additionalneeds","Breakfast");*/

        Auth auth1 = new Auth("admin", "password123");
        Gson gson = new Gson();
        String auth = gson.toJson(auth1);

        Bookings booking = new Bookings();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(1111);
        booking.setDepositpaid(true);
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2023-05-01");
        bookingdates.setCheckout("2023-05-02");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        RequestSpecification requestSpecification;
        ValidatableResponse validatableResponse;
        Response response;
        String token;
        Integer bookingid;


        String createBookings = gson.toJson(booking);


         requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");



            requestSpecification.basePath("/auth");
            requestSpecification.contentType(ContentType.JSON);
            requestSpecification.body(auth);


            response = requestSpecification.when().post();
            validatableResponse = response.then().log().all();
            validatableResponse.statusCode(200);

             token = response.then().extract().path("token");


        //post
        requestSpecification.basePath("/booking");
        requestSpecification.cookies("token", token);
        requestSpecification.body(createBookings);

        response = requestSpecification.post();
        bookingid = response.then().extract().path("bookingid");
        validatableResponse = response.then().log().all();

        //#TC 1
        validatableResponse.statusCode(200);

        //#TC 2
        validatableResponse.body("bookingid", Matchers.notNullValue());


        //put


        booking.setFirstname("Ajay");
        String autoload = gson.toJson(booking);

        requestSpecification.basePath("/booking/" + bookingid);
        requestSpecification.cookies("token", token);
        requestSpecification.body(autoload);

        response = requestSpecification.patch();
        validatableResponse = response.then().log().all();

        //#TC 1
        validatableResponse.statusCode(200);

        //#TC 2
        validatableResponse.body("firstname", Matchers.equalTo("Ajay"));


        //delete

        requestSpecification.basePath("/booking/" + bookingid);
        requestSpecification.cookies("token", token);

        response = requestSpecification.delete();
        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(201);
        String status = response.statusLine();
        System.out.println(status);
    }


}
