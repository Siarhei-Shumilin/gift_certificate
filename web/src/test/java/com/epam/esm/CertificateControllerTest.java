package com.epam.esm;

import com.epam.esm.config.entity.AuthenticationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

public class CertificateControllerTest {

    @Test
    public void testFindCertificates() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/certificates?name=a")
                .then()
                .statusCode(200);
    }

   @Test
    public void testSaveCertificates() {
        String response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\" : \"admin\", \"password\" : \"22222\"}")
                .when()
                .post("/users/authenticate")
                .then().extract().response().as(AuthenticationResponse.class).getJwt();
        String[] split = response.split(":");
        String jwt = "Bearer " + split[0];
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", jwt)
                .body("{}")
                .when()
                .post("/certificates/")
                .then()
                .statusCode(400);
    }
}