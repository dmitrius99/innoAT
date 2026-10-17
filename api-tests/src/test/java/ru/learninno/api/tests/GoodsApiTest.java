package ru.learninno.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.learninno.api.assertions.ApiAssert;
import ru.learninno.api.client.BasicAuthApi;
import ru.learninno.api.client.BasicGoodsApi;
import ru.learninno.api.client.RestApiBuilder;
import ru.learninno.api.model.GoodRequest;
import ru.learninno.common.config.TestConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Epic("SmartShop")
@Feature("Goods API")
@Tag("API")
@Tag("Smoke")
class GoodsApiTest {
    private final TestConfig config = TestConfig.get();
    private final RestApiBuilder rest = new RestApiBuilder(config);
    private final BasicAuthApi authApi = new BasicAuthApi(rest, config);
    private final BasicGoodsApi goodsApi = new BasicGoodsApi(rest, config);
    private final List<Integer> productsToDelete = new ArrayList<>();

    @AfterEach
    void cleanUpProducts() {
        productsToDelete.forEach(goodsApi::delete);
    }

    @Test
    @Story("Авторизация")
    void administratorCanLogin() {
        Response response = authApi.loginAsAdmin();
        ApiAssert.status(response, config.redirectStatus());
    }

    @Test
    @Story("Создание товара")
    void createGoodReturnsCreatedIdentifier() {
        Response response = goodsApi.create(new GoodRequest(uniqueProductName(), config.defaultPrice()));
        ApiAssert.status(response, config.successStatus());
        ApiAssert.createdIdIsPositive(response);
        productsToDelete.add(ApiAssert.createdId(response));
    }

    @Test
    @Story("Создание товара")
    void createGoodWithoutNameReturnsValidationError() {
        Response response = goodsApi.createWithoutName();
        ApiAssert.status(response, config.validationErrorStatus());
        ApiAssert.hasBody(response);
    }

    @Test
    @Story("Получение товара")
    void getExistingGoodReturnsItsData() {
        GoodRequest expected = new GoodRequest(uniqueProductName(), config.defaultPrice());
        int id = createGood(expected);
        Response response = goodsApi.getById(id);
        ApiAssert.status(response, config.successStatus());
        ApiAssert.good(response, id, expected.name(), expected.price());
    }

    @Test
    @Story("Получение товара")
    void getMissingGoodReturnsNotFound() {
        Response response = goodsApi.getById(config.missingId());
        ApiAssert.status(response, config.notFoundStatus());
        ApiAssert.hasBody(response);
    }

    @Test
    @Story("Редактирование товара")
    void updateExistingGoodSavesNewData() {
        int id = createGood(new GoodRequest(uniqueProductName(), config.defaultPrice()));
        GoodRequest expected = new GoodRequest(uniqueProductName(), config.updatedPrice());
        Response response = goodsApi.update(id, expected);
        ApiAssert.status(response, config.successStatus());
        ApiAssert.good(response, id, expected.name(), expected.price());
    }

    @Test
    @Story("Редактирование товара")
    void updateWithoutNameReturnsValidationError() {
        int id = createGood(new GoodRequest(uniqueProductName(), config.defaultPrice()));
        Response response = goodsApi.updateWithoutName(id);
        ApiAssert.status(response, config.validationErrorStatus());
        ApiAssert.hasBody(response);
    }

    @Test
    @Story("Редактирование товара")
    void updateMissingGoodReturnsNotFound() {
        Response response = goodsApi.update(
                config.missingId(), new GoodRequest(uniqueProductName(), config.updatedPrice()));
        ApiAssert.status(response, config.notFoundStatus());
        ApiAssert.hasBody(response);
    }

    @Test
    @Story("Удаление товара")
    void deleteExistingGoodReturnsSuccess() {
        int id = createGood(new GoodRequest(uniqueProductName(), config.defaultPrice()));
        Response response = goodsApi.delete(id);
        ApiAssert.status(response, config.successStatus());
        productsToDelete.remove(Integer.valueOf(id));
    }

    @Test
    @Story("Удаление товара")
    void deleteMissingGoodReturnsNotFound() {
        Response response = goodsApi.delete(config.missingId());
        ApiAssert.status(response, config.notFoundStatus());
        ApiAssert.hasBody(response);
    }

    @Test
    @Story("Получение списка товаров")
    void listGoodsContainsCreatedGood() {
        int id = createGood(new GoodRequest(uniqueProductName(), config.defaultPrice()));
        Response response = goodsApi.list();
        ApiAssert.status(response, config.successStatus());
        ApiAssert.containsGood(response, id);
    }

    @Step("Подготовить товар через API")
    private int createGood(GoodRequest product) {
        Response response = goodsApi.create(product);
        ApiAssert.status(response, config.successStatus());
        ApiAssert.createdIdIsPositive(response);
        int id = ApiAssert.createdId(response);
        productsToDelete.add(id);
        return id;
    }

    @Step("Сгенерировать уникальное название товара")
    private String uniqueProductName() {
        return config.productNamePrefix() + UUID.randomUUID();
    }
}
