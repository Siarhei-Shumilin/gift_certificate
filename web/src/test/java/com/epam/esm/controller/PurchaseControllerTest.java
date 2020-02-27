package com.epam.esm.controller;

import com.epam.esm.config.entity.AuthenticationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

public class PurchaseControllerTest {

    private String jwt;

    @Before
    public void init(){
        String response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\" : \"admin\", \"password\" : \"22222\"}")
                .when()
                .post("/users/authenticate")
                .then().extract().response().as(AuthenticationResponse.class).getJwt();
        String[] split = response.split(":");
        jwt = "Bearer " + split[0];
    }

    @Test
    public void save() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .body("{\"id\" : 5 , \"price\" : 20}")
                .when()
                .post("/purchase")
                .then()
                .statusCode(200);
    }

    @Test
    public void findUsersPurchases() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/purchase/3")
                .then()
                .statusCode(200);
    }

    @Test
    public void findCurrentUserPurchase() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/purchase")
                .then()
                .statusCode(200);
    }
}