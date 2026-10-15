import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import support.ApiChecks;
import support.ApiClient;

import java.math.BigDecimal;

@Tag("ApiTestsTask1")
public class FirstApiTest {
    private static final TestConfiguration CONFIG = TestConfiguration.getInstance();
    private final ApiClient api = new ApiClient(CONFIG.apiUrl(), CONFIG.adminLogin(), CONFIG.adminPassword());

    @Test
    void checkStatusCodeAndBody() {
        Response response = api.listGoods(0, 10);
        ApiChecks.hasStatus(response, 200);
        checkGoodsListIsEmpty(response);
    }

    @Test
    void checkStatusCodeAndBodyWithSpec() {
        Response response = api.listGoods(0, 10);
        ApiChecks.hasStatus(response, 200);
        checkGoodsListIsEmpty(response);
    }

    @Test
    void createAndCheckGood() {
        String name = CONFIG.starterProductName() + " via API";
        int id = createGood(name, CONFIG.starterProductPrice());
        Response response = api.listGoods(0, 100);
        ApiChecks.hasStatus(response, 200);
        ApiChecks.containsGood(response, id);
    }

    @Test
    void createAndCheckGoodWithAssertJ() {
        int id = createGood("fsaaros", BigDecimal.valueOf(150));
        Response response = api.listGoods(0, 100);
        ApiChecks.hasStatus(response, 200);
        ApiChecks.containsGood(response, id);
    }

    @Step("Создать товар через API для проверки списка")
    private int createGood(String name, BigDecimal price) {
        Response response = api.addGood(name, price);
        ApiChecks.hasStatus(response, 200);
        int id = api.createdId(response);
        ApiChecks.hasPositiveId(id);
        return id;
    }

    @Step("Проверить, что список товаров пуст")
    private void checkGoodsListIsEmpty(Response response) {
        org.assertj.core.api.Assertions.assertThat(response.jsonPath().getList("goods")).isEmpty();
    }
}
