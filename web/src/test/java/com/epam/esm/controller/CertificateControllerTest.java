//package com.epam.esm.controller;
//
//import com.epam.esm.config.entity.AuthenticationResponse;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import org.junit.Before;
//import org.junit.Test;
//
//public class CertificateControllerTest {
//
//    private String jwt;
//
//    @Before
//    public void init(){
//      String response = RestAssured.given()
//                .contentType("application/json")
//                .body("{\"username\" : \"admin\", \"password\" : \"22222\"}")
//                .when()
//                .post("/users/authenticate")
//                .then().extract().response().as(AuthenticationResponse.class).getJwt();
//        String[] split = response.split(":");
//        jwt = "Bearer " + split[0];
//    }
//
//    @Test
//    public void testFindCertificates() {
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/certificates?name=a")
//                .then()
//                .statusCode(200);
//    }
//
//   @Test
//    public void testSaveCertificates() {
//        RestAssured.given()
//                .contentType("application/json")
//                .header("Authorization", jwt)
//                .body("{}")
//                .when()
//                .post("/certificates/")
//                .then()
//                .statusCode(400);
//    }
//
//    @Test
//    public void testDelete() {
//        RestAssured.given()
//                .contentType("application/json")
//                .header("Authorization", jwt)
//                .when()
//                .delete("/certificates/4")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void testUpdatePrice() {
//        RestAssured.given()
//                .contentType("application/json")
//                .header("Authorization", jwt)
//                .body("{\"price\" : 20}")
//                .when()
//                .put("/certificates/price/5")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void testUpdate() {
//        RestAssured.given()
//                .contentType("application/json")
//                .header("Authorization", jwt)
//                .body("{\"price\" : 20}")
//                .when()
//                .put("/certificates/5")
//                .then()
//                .statusCode(400);
//    }
//}