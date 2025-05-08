package specifications;

import config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification authenticatedRequest() {
        return new RequestSpecBuilder()
                .addHeader("x-api-key", TestConfig.API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public static RequestSpecification unauthenticatedRequest() {
        return new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
}