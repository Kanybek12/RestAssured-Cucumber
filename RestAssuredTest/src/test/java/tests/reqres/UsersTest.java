package tests.reqres;

import config.ApiConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;
import java.io.File;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class UsersTest {
    // Define User class


    @BeforeAll
    public static void setup() {
        ApiConfig.setup();
    }

    @Test
    public void testGetUser() {
        given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    public void testGetAllUser() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users?page=2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .statusCode(200)
                .extract()
                .response();

        //extract single user data
        //String firstUserEmail
        // .path("data[1].email");
        // System.out.println("First user's email: " + firstUserEmail);

        int pageNumber = response.path("page");
        int totalUsers = response.path("total");
        String secondUserName = response.path("data[1].first_name");

        System.out.println("Page: " + pageNumber); // Page: 2
        System.out.println("Total users: " + totalUsers); // Total users: 12
        System.out.println("Second user: " + secondUserName); // Second user: Lindsay

    }

    @Test
    public void SingleUserNotFound() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users/23")
                .then()
                .spec(ResponseSpecs.singleUserNotFound())
                .statusCode(404)
                .extract().response();

        System.out.println(response.statusCode());
    }

    //Test
    @Test
    public void ListOfResources() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/unknown")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        int pageNumber = response.path("page");
        int totalUsers = response.path("total");
        String secondUserName = response.path("data[0].pantone_value");

        System.out.println("Page: " + pageNumber); // Page: 2
        System.out.println("Total users: " + totalUsers); // Total users: 12
        System.out.println("Pantone value: " + secondUserName); // Second user: Lindsay
    }

    @Test
    public void SingleOfResources() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/unknown/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();
        String name = response.path("data.name");
        System.out.println(name);
    }

    @Test
    public void SingleUserNotFoundResources() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/unknown/23")
                .then()
                .spec(ResponseSpecs.singleUserNotFound())
                .statusCode(404)
                .extract().response();

        System.out.println(response.statusCode());
    }


    //Method POST
    @Test
    public void CreateUser() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/users.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/users")
                .then()
                .spec(ResponseSpecs.createdResponse())
                .extract().response();

        System.out.println("Resource exists: " +
                (getClass().getResource("/testdata/users.json") != null));

        // Additional assertions or logging
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());

        // Example: Extract and use values from response
/*        int userId = response.jsonPath().getInt("id");
        assertTrue(userId > 0, "User ID should be positive");*/
    }

    @Test
    public void UpdateUser() {
        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/updUser.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .put("/users/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @Test
    public void DeleteUser() {

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .delete("/users/2")
                .then()
                .spec(ResponseSpecs.deleteUser())
                .extract().response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @Test
    public void RegisterUser() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/auth.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/register")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        System.out.println("Resource exists: " +
                (getClass().getResource("/testdata/auth.json") != null));

        // Additional assertions or logging
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }
    @Test
    public void RegisterUserUnsuccessful () {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/auth.json").getFile());

        // Extract specific fields using JsonPath
        String email = JsonPath.from(jsonFile).getString("email");
//        String password = JsonPath.from(jsonFile).getString("password");

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(email)
                .post("/register")
                .then()
                .spec(ResponseSpecs.MissingAndBadRequest())
                .extract().response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());

    }
}