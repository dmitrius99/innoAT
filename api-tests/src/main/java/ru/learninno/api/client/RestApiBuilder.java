package ru.learninno.api.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import ru.learninno.common.config.TestConfig;

import static io.restassured.RestAssured.given;

/** Creates uniform REST Assured specifications for all basic API classes. */
public final class RestApiBuilder {
    private final TestConfig config;

    public RestApiBuilder(TestConfig config) {
        this.config = config;
    }

    public RequestSpecification anonymous() {
        return given().spec(base().build());
    }

    public RequestSpecification authenticated() {
        return given().spec(base().build())
                .auth().basic(config.adminLogin(), config.adminPassword());
    }

    private RequestSpecBuilder base() {
        return new RequestSpecBuilder()
                .setBaseUri(config.apiUrl())
                .addFilter(new AllureRestAssured());
    }
}
