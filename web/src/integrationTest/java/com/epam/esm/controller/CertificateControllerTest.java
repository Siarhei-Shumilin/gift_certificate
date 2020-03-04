package com.epam.esm.controller;

import com.epam.esm.config.entity.AuthenticationResponse;
import com.epam.esm.entity.GiftCertificate;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

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
        List<GiftCertificate> certificateList = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/certificates").getBody().as(List.class);
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
    public void testSaveCertificatesShouldReturnStatusCode200AndPrintResponseWithIdNewObject() {
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .body("{\n" +
                        "        \"name\": \"ali\",\n" +
                        "        \"description\": \"Ut quod ipsa vitae amet expedita voluptatem. Eligendi temporibus fuga mollitia et.\",\n" +
                        "        \"price\": 96.25,\n" +
                        "        \"createDate\": \"1984-06-05T13:06:55\",\n" +
                        "        \"lastUpdateDate\": \"2020-02-26T10:21:39\",\n" +
                        "        \"duration\": 5,\n" +
                        "        \"tagList\": [\n" +
                        "            {\n" +
                        "                \"id\": 2,\n" +
                        "                \"name\": \"odio\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }")
                .when()
                .post("/certificates/")
                .then()
                .statusCode(200)
                .extract().response().getBody().print();
    }

    @Test
    public void testDeleteShouldReturnStatusCode200AndNumberDeletedCertificates() {
        Gson gson = new Gson();
        String numberOfDeletedCertificates = gson.toJson(Map.of("Number of deleted certificates", 1));
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .when()
                .delete("/certificates/118")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo(numberOfDeletedCertificates));
    }

    @Test
    public void testUpdatePriceShouldReturnStatusCode200AndNumberUpdatedCertificates() {
        Gson gson = new Gson();
        String numberOfUpdatedCertificates = gson.toJson(Map.of("Number of updated certificates", 0));
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .body("{\"price\" : 20}")
                .when()
                .put("/certificates/price/5")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo(numberOfUpdatedCertificates));
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