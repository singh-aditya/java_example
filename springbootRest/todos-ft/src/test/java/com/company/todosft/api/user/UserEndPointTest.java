package com.company.todosft.api.user;

import com.company.todosft.TestConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Or;

import static com.company.todosft.TestConstants.JSON;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserEndPointTest {

    private static final String EMAIL = "abc@company.com";
    private static final String PASS = "user123";

    private static String userId;
    private static String authorization;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI= TestConstants.TEST_URI;
        RestAssured.port=TestConstants.PORT;
    }

    @Test
    @Order(1)
    final void testCreateUser() {
        final String reqBody = "{\n" +
                "    \"firstName\": \"abc\",\n" +
                "    \"lastName\": \"abc\",\n" +
                "    \"email\": \"" + EMAIL + "\",\n" +
                "    \"password\": \"" + PASS + "\" \n" +
                "}";
        Response response = given()
                .contentType(JSON)
                .accept(JSON)
                .body(reqBody)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(JSON)
                .extract().response();

        String userId = response.jsonPath().getString("userId");
        assertNotNull(userId);
        assertTrue(userId.length() == 30);
        assertEquals(EMAIL, response.jsonPath().getString("email"));
    }

    @Test
    @Order(2)
    void testLoginUser() {
        final String loginDetails = "{\n" +
                "    \"email\": \"" + EMAIL + "\",\n" +
                "    \"password\": \"" + PASS + "\" \n" +
                "}";

        Response resposne = given().
                contentType(JSON).
                accept(JSON).
                body(loginDetails).
                when().
                post("/users/login").
                then().
                statusCode(HttpStatus.SC_OK).extract().response();

        authorization = resposne.header(TestConstants.AUTH_HEADER_PREFIX);
        assertNotNull(authorization);
        userId = resposne.header(TestConstants.USERID_HEADER_PREFIX);
        assertNotNull(userId);
    }

    @Test
    @Order(3)
    void testGetUser() {

        Response response = given()
                .pathParam("id", userId)
                .header("Authorization", authorization)
                .accept(JSON)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(JSON)
                .extract().response();

        assertEquals(userId, response.jsonPath().getString("userId"));
        assertEquals(EMAIL, response.jsonPath().getString("email"));
    }

    @Test
    @Order(4)
    void testUpdateUser() {
        final String reqBody = "{\n" +
                "    \"firstName\": \"def\",\n" +
                "    \"lastName\": \"def\"\n" +
                "}";

        Response response = given()
                .pathParam("id", userId)
                .header("Authorization", authorization)
                .contentType(JSON)
                .accept(JSON)
                .body(reqBody)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(JSON)
                .extract().response();

        assertEquals("def", response.jsonPath().getString("firstName"));
        assertEquals("def", response.jsonPath().getString("lastName"));
    }

    @Test
    @Order(5)
    void testDeleteUser() {
        // user can not delete itself
        given()
                .header("Authorization",authorization)
                .accept(JSON)
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
