package com.epam.esm;

import com.epam.esm.config.entity.AuthenticationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import java.util.Arrays;
import static org.hamcrest.Matchers.equalTo;

public class CertificateControllerTest {

    @Test
    public void testFindCertificates() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/certificates?name=jjjkkj")
                .then()
                .statusCode(200)
                .body("name", equalTo(Arrays.asList("jjjkkj")))
                .extract().response().prettyPrint();
    }

    @Test
    public void testSaveCertificates() {
        String response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\" : \"username\", \"password\" : \"22222\"}")
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
                .statusCode(400)
                .extract().response().prettyPrint();
    }
}