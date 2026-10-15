package support;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;

/** API gateway: every endpoint invocation is an independently visible Allure step. */
public final class ApiClient {
    private final String baseUrl;
    private final String login;
    private final String password;
    private Map<String, String> cookies;

    public ApiClient(String baseUrl, String login, String password) {
        this.baseUrl = baseUrl;
        this.login = login;
        this.password = password;
        RestAssured.filters(new AllureRestAssured());
    }

    @Step("Авторизоваться через API как администратор")
    public Map<String, String> login() {
        cookies = given().baseUri(baseUrl)
                .redirects().follow(false)
                .contentType(ContentType.URLENC)
                .formParam("username", login)
                .formParam("password", password)
                .post("/login")
                .then().statusCode(302)
                .extract().cookies();
        return cookies;
    }

    @Step("API: добавить товар «{name}» по цене {price}")
    public Response addGood(String name, BigDecimal price) {
        return authenticatedRequest()
                .contentType(ContentType.JSON)
                .body(goodPayload(name, price))
                .post("/goods/add");
    }

    @Step("API: отправить товар без обязательного названия")
    public Response addGoodWithoutName(BigDecimal price) {
        return authenticatedRequest().contentType(ContentType.JSON)
                .body("{\"price\":" + price + "}").post("/goods/add");
    }

    @Step("API: получить товар с id {id}")
    public Response getGood(int id) {
        return authenticatedRequest().get("/goods/{id}", id);
    }

    @Step("API: обновить товар с id {id}")
    public Response updateGood(int id, String name, BigDecimal price) {
        return authenticatedRequest().contentType(ContentType.JSON)
                .body(goodPayload(name, price)).patch("/goods/{id}", id);
    }

    @Step("API: обновить товар с id {id} без обязательного названия")
    public Response updateGoodWithoutName(int id, BigDecimal price) {
        return authenticatedRequest().contentType(ContentType.JSON)
                .body("{\"price\":" + price + "}").patch("/goods/{id}", id);
    }

    @Step("API: удалить товар с id {id}")
    public Response deleteGood(int id) {
        return authenticatedRequest().delete("/goods/{id}", id);
    }

    @Step("API: получить список товаров")
    public Response listGoods(int page, int size) {
        return given().baseUri(baseUrl).filter(new AllureRestAssured())
                .accept(ContentType.JSON).queryParam("page", page).queryParam("size", size)
                .get("/goods/list");
    }

    @Step("API: извлечь id созданного товара")
    public int createdId(Response response) {
        return response.jsonPath().getInt("data.id");
    }

    private io.restassured.specification.RequestSpecification authenticatedRequest() {
        return given().baseUri(baseUrl).filter(new AllureRestAssured())
                .auth().basic(login, password).accept(ContentType.JSON);
    }

    private String goodPayload(String name, BigDecimal price) {
        return "{\"name\":\"" + name.replace("\"", "\\\"")
                + "\",\"price\":" + price.toPlainString() + "}";
    }
}
