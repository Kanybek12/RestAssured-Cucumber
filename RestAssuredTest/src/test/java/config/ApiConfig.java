package config;
import io.restassured.RestAssured;
public class ApiConfig {
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}

