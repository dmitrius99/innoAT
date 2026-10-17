package ru.learninno.api.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.learninno.common.config.TestConfig;

/** Basic API class responsible only for authentication endpoints. */
public final class BasicAuthApi {
    private final RestApiBuilder rest;
    private final TestConfig config;

    public BasicAuthApi(RestApiBuilder rest, TestConfig config) {
        this.rest = rest;
        this.config = config;
    }

    @Step("API: авторизоваться как пользователь {login}")
    public Response login(String login, String password) {
        return rest.anonymous()
                .redirects().follow(false)
                .contentType(ContentType.URLENC)
                .formParam("username", login)
                .formParam("password", password)
                .post(config.loginPath());
    }

    @Step("API: авторизоваться как администратор")
    public Response loginAsAdmin() {
        return login(config.adminLogin(), config.adminPassword());
    }
}
