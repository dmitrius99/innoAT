package support;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** API assertions are separate Allure steps so a failed expectation is readable in the report. */
public final class ApiChecks {
    private ApiChecks() { }

    @Step("Проверить HTTP-статус {expectedStatus}")
    public static void hasStatus(Response response, int expectedStatus) {
        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }

    @Step("Проверить, что id созданного товара положительный")
    public static void hasPositiveId(int id) {
        assertThat(id).isPositive();
    }

    @Step("Проверить, что ответ API не пустой")
    public static void hasNonEmptyBody(Response response) {
        assertThat(response.getBody().asString()).isNotBlank();
    }

    @Step("Проверить данные товара с id {id}")
    public static void hasGoodData(Response response, int id, String name, double price) {
        assertThat(response.jsonPath().getInt("id")).isEqualTo(id);
        assertThat(response.jsonPath().getString("name")).isEqualTo(name);
        assertThat(response.jsonPath().getDouble("price")).isEqualTo(price);
    }

    @Step("Проверить, что список товаров содержит id {id}")
    public static void containsGood(Response response, int id) {
        List<Integer> ids = response.jsonPath().getList("goods.id", Integer.class);
        assertThat(ids).contains(id);
    }
}
