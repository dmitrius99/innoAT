package ru.learninno.api.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.learninno.api.model.GoodRequest;
import ru.learninno.common.config.TestConfig;

/** Basic API class responsible only for the goods endpoints. */
public final class BasicGoodsApi {
    private final RestApiBuilder rest;
    private final TestConfig config;

    public BasicGoodsApi(RestApiBuilder rest, TestConfig config) {
        this.rest = rest;
        this.config = config;
    }

    @Step("API: создать товар {request.name}")
    public Response create(GoodRequest request) {
        return json().body(request).post(config.addGoodPath());
    }

    @Step("API: создать товар без обязательного названия")
    public Response createWithoutName() {
        return json().body(new GoodRequest(null, config.defaultPrice())).post(config.addGoodPath());
    }

    @Step("API: получить товар с id {id}")
    public Response getById(int id) {
        return rest.authenticated().get(config.goodPath(), id);
    }

    @Step("API: обновить товар с id {id}")
    public Response update(int id, GoodRequest request) {
        return json().body(request).patch(config.goodPath(), id);
    }

    @Step("API: обновить товар с id {id} без обязательного названия")
    public Response updateWithoutName(int id) {
        return json().body(new GoodRequest(null, config.updatedPrice())).patch(config.goodPath(), id);
    }

    @Step("API: удалить товар с id {id}")
    public Response delete(int id) {
        return rest.authenticated().delete(config.goodPath(), id);
    }

    @Step("API: получить список товаров")
    public Response list() {
        return rest.anonymous()
                .queryParam("page", config.listPage())
                .queryParam("size", config.listSize())
                .get(config.goodsListPath());
    }

    private io.restassured.specification.RequestSpecification json() {
        return rest.authenticated().contentType(ContentType.JSON).accept(ContentType.JSON);
    }
}
