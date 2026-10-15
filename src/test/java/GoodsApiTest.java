import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import support.ApiChecks;
import support.ApiClient;

import java.math.BigDecimal;
import java.util.UUID;

@Tag("ApiTests")
@Tag("AllureTests")
public class GoodsApiTest {
    private static final TestConfiguration CONFIG = TestConfiguration.getInstance();
    private final ApiClient api = new ApiClient(CONFIG.apiUrl(), CONFIG.adminLogin(), CONFIG.adminPassword());

    @Test
    void addGoodReturns200AndCreatedId() {
        Response response = api.addGood(uniqueName(), CONFIG.starterProductPrice());
        ApiChecks.hasStatus(response, 200);
        ApiChecks.hasPositiveId(api.createdId(response));
    }

    @Test
    void addGoodWithoutRequiredNameReturns400() {
        Response response = api.addGoodWithoutName(BigDecimal.valueOf(100));
        ApiChecks.hasStatus(response, 400);
        ApiChecks.hasNonEmptyBody(response);
    }

    @Test
    void getGoodByExistingIdReturns200AndGoodData() {
        String name = uniqueName();
        int id = createGood(name, BigDecimal.valueOf(100));
        Response response = api.getGood(id);
        ApiChecks.hasStatus(response, 200);
        ApiChecks.hasGoodData(response, id, name, 100);
    }

    @Test
    void getGoodByMissingIdReturns404() {
        Response response = api.getGood(-1);
        ApiChecks.hasStatus(response, 404);
        ApiChecks.hasNonEmptyBody(response);
    }

    @Test
    void patchExistingGoodReturns200AndUpdatedData() {
        int id = createGood(uniqueName(), BigDecimal.valueOf(100));
        String updatedName = "updated-" + UUID.randomUUID();
        Response response = api.updateGood(id, updatedName, BigDecimal.valueOf(250));
        ApiChecks.hasStatus(response, 200);
        ApiChecks.hasGoodData(response, id, updatedName, 250);
    }

    @Test
    void patchGoodWithoutRequiredNameReturns400() {
        int id = createGood(uniqueName(), BigDecimal.valueOf(100));
        ApiChecks.hasStatus(api.updateGoodWithoutName(id, BigDecimal.valueOf(250)), 400);
    }

    @Test
    void patchGoodByMissingIdReturns404() {
        ApiChecks.hasStatus(api.updateGood(-1, "missing-good", BigDecimal.valueOf(250)), 404);
    }

    @Test
    void deleteExistingGoodReturns200AndRemovesGood() {
        int id = createGood(uniqueName(), BigDecimal.valueOf(100));
        ApiChecks.hasStatus(api.deleteGood(id), 200);
    }

    @Test
    void deleteGoodByMissingIdReturns404() {
        ApiChecks.hasStatus(api.deleteGood(-1), 404);
    }

    @Test
    void listGoodsReturns200AndContainsCreatedGood() {
        int id = createGood(uniqueName(), BigDecimal.valueOf(100));
        Response response = api.listGoods(0, 100);
        ApiChecks.hasStatus(response, 200);
        ApiChecks.containsGood(response, id);
    }

    @Step("Подготовить тестовый товар через API")
    private int createGood(String name, BigDecimal price) {
        Response response = api.addGood(name, price);
        ApiChecks.hasStatus(response, 200);
        int id = api.createdId(response);
        ApiChecks.hasPositiveId(id);
        return id;
    }

    @Step("Сгенерировать уникальное название тестового товара")
    private String uniqueName() {
        return "good-" + UUID.randomUUID();
    }
}
