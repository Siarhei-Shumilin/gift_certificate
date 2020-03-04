package com.epam.esm.controller;

import com.epam.esm.config.entity.AuthenticationResponse;
import com.epam.esm.entity.GiftCertificate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

//@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class CertificateControllerTest {

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
    public void testFindCertificatesShouldReturnListCertificates() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/certificates");
        List<GiftCertificate> certificateList = response.getBody().as(List.class);
        Assert.assertFalse(certificateList.isEmpty());
    }

    @Test
    public void testFindCertificatesByIdShouldReturnStatusCode200AndNameCertificate() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/certificates/8")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", CoreMatchers.equalTo("nisi"));
    }

    @Test
    public void testSaveCertificatesShouldReturnBadRequestWhenBodyIsEmpty() {
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .body("{}")
                .when()
                .post("/certificates/")
                .then()
                .statusCode(400);
    }

    @Test
    public void testDeleteShouldReturnStatusCode200() {
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .when()
                .delete("/certificates/4")
                .then()
                .statusCode(200);


    }

    @Test
    public void testUpdatePriceShouldReturnStatusCode200() {
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .body("{\"price\" : 20}")
                .when()
                .put("/certificates/price/5")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateShouldReturnBadRequestWhenCertificateFieldsIsIncorrect() {
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .body("{\"price\" : 20}")
                .when()
                .put("/certificates/5")
                .then()
                .statusCode(400);
    }
}