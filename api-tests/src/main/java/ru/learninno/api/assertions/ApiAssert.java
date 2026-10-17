package ru.learninno.api.assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.util.List;

/** Reusable API checks, kept outside tests and client code. */
public final class ApiAssert {
    private ApiAssert() { }

    @Step("Проверить HTTP-статус {expected}")
    public static void status(Response response, int expected) {
        Assertions.assertThat(response.statusCode()).isEqualTo(expected);
    }

    @Step("Проверить, что ответ API содержит тело")
    public static void hasBody(Response response) {
        Assertions.assertThat(response.asString()).isNotBlank();
    }

    @Step("Проверить, что идентификатор созданного товара положительный")
    public static void createdIdIsPositive(Response response) {
        Assertions.assertThat(createdId(response)).isPositive();
    }

    @Step("Проверить данные товара с id {id}")
    public static void good(Response response, int id, String name, BigDecimal price) {
        Assertions.assertThat(response.jsonPath().getInt("id")).isEqualTo(id);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(name);
        Assertions.assertThat(new BigDecimal(response.jsonPath().getString("price"))).isEqualByComparingTo(price);
    }

    @Step("Проверить, что список товаров содержит товар с id {id}")
    public static void containsGood(Response response, int id) {
        List<Integer> ids = response.jsonPath().getList("goods.id", Integer.class);
        Assertions.assertThat(ids).contains(id);
    }

    public static int createdId(Response response) {
        return response.jsonPath().getInt("data.id");
    }
}
