package restassured.crud;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class GetAuth {


    //baseurl =https://restful-booker.herokuapp.com
    //path =  /auth


    static String payload = """
            {
            "username": "admin" "password" :"user123"
            }""";

    public static void getAuth(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/auth");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payload);
       Response response = requestSpecification.when().post();
        ValidatableResponse validatableResponse =response.then();
        




    }







}
