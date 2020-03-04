package com.epam.esm.controller;

import com.epam.esm.config.entity.AuthenticationResponse;
import com.epam.esm.entity.Purchase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PurchaseControllerTest {

    private String jwt;

    @Before
    public void init(){
        String response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\" : \"adm\", \"password\" : \"111\"}")
                .when()
                .post("/users/authenticate")
                .then().extract().response().as(AuthenticationResponse.class).getJwt();
        String[] split = response.split(":");
        jwt = "Bearer " + split[0];
    }

    @Test
    public void testSaveShouldReturnStatusCode200AndPrintResponseBody() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .body("{\n" +
                        "\"id\": 3\n" +
                        "}")
                .when()
                .post("/purchase")
                .then()
                .statusCode(200)
                .extract().response().getBody().print();
    }

    @Test
    public void testFindUsersPurchasesShouldReturnTrueIfCertificateListIsNotEmpty() {
        List<Purchase> userPurchases = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/purchase/user/3")
                .getBody().as(List.class);
        Assert.assertFalse(userPurchases.isEmpty());
    }

    @Test
    public void testFindCurrentUserPurchaseShouldReturnTrueIfCertificateListIsNotEmpty() {
        List<Purchase> currentUserPurchases = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/purchase")
                .getBody().as(List.class);
        Assert.assertFalse(currentUserPurchases.isEmpty());
    }
}