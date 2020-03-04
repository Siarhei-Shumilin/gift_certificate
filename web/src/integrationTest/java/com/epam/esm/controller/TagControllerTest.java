package com.epam.esm.controller;

import com.epam.esm.config.entity.AuthenticationResponse;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class TagControllerTest {

    private String jwt;

    @Before
    public void init() {
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
    public void testFindByParametersShouldReturnStatusCode200AndTrueIfTagIdEquals() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/tags?tagName=odio")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(Arrays.asList(2)))
                .extract().response().getBody().print();
    }

    @Test
    public void testSaveShouldReturnIdEqualsZeroIfTagExists() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .body("{\"name\" : \"abc\"}")
                .when()
                .post("/tags")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(0))
                .extract().response().getBody().print();
    }

    @Test
    public void testDeleteShouldReturnStatusCode200AndNumberDeletedCertificates() {
        Gson gson = new Gson();
        String numberOfDeletedCertificates = gson.toJson(Map.of("Number of deleted tags", 1));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .delete("/tags/173")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo(numberOfDeletedCertificates));
    }

    @Test
    public void testFindMostPopularTagShouldReturnTrueIfIdPopularTagEquals3() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/tags/popular")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(3))
        .extract().response().getBody().print();
    }
}