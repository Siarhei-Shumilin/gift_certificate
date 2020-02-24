package com.epam.esm.controller;

import com.epam.esm.config.entity.AuthenticationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagControllerTest {

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
    public void testFindByParameters() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/tags?name=eye")
                .then()
                .statusCode(200);
    }

    @Test
    public void testSave() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .body("{\"name\" : \"a\"}")
                .when()
                .post("/tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .delete("/tags/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFindMostPopularTag() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .when()
                .get("/tags/popular")
                .then()
                .statusCode(200);
    }
}