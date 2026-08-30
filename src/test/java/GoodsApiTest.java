import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("ApiTests")
public class GoodsApiTest {

    private static final String BASE_URI = "http://localhost:8080";

    @Test
    void addGoodReturns200AndCreatedId() {
        String name = "good-" + UUID.randomUUID();

        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "%s",
                          "price": 100.0
                        }
                        """.formatted(name))
        .when()
                .post("/goods/add");

        Integer createdId = response.jsonPath().getObject("data.id", Integer.class);

        assertThat(response.statusCode())
                .as("Статус ответа при создании товара")
                .isEqualTo(200);
        assertThat(createdId)
                .as("ID созданного товара")
                .isNotNull()
                .isPositive();
    }

    @Test
    void addGoodWithoutRequiredNameReturns400() {
        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "price": 100.0
                        }
                        """)
        .when()
                .post("/goods/add");

        assertThat(response.statusCode())
                .as("Статус ответа при создании товара без обязательного name")
                .isEqualTo(400);
        assertThat(response.getBody().asString())
                .as("Тело ответа с ошибкой")
                .isNotBlank();
    }

    //метод для создания тестового товара для гарантии что
    //тесты получения изменения удаления товара не зависит от прежних запусков и содержимого базы
    private int createGood(String name, double price) {
        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "%s",
                          "price": %s
                        }
                        """.formatted(name, price))
                .when()
                .post("/goods/add");

        Integer createdId = response.jsonPath().getInt("data.id");

        assertThat(response.statusCode())
                .as("Статус ответа при подготовке товара")
                .isEqualTo(200);
        assertThat(createdId)
                .as("ID подготовленного товара")
                .isNotNull()
                .isPositive();

        return createdId;
    }
    
    @Test
    void getGoodByExistingIdReturns200AndGoodData() {
        String name = "good-" + UUID.randomUUID();
        int createdId = createGood(name, 100.0);

        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .pathParam("id", createdId)
        .when()
                .get("/goods/{id}");

        assertThat(response.statusCode())
                .as("Статус ответа при получении существующего товара")
                .isEqualTo(200);
        assertThat(response.jsonPath().getInt("id"))
                .as("ID товара в ответе")
                .isEqualTo(createdId);
        assertThat(response.jsonPath().getString("name"))
                .as("Название товара в ответе")
                .isEqualTo(name);
    }

    @Test
    void getGoodByMissingIdReturns404() {
        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .pathParam("id", -1)
        .when()
                .get("/goods/{id}");

        assertThat(response.statusCode())
                .as("Статус ответа при получении несуществующего товара")
                .isEqualTo(404);
        assertThat(response.getBody().asString())
                .as("Тело ответа об отсутствующем товаре")
                .isNotBlank();
    }

    @Test
    void patchExistingGoodReturns200AndUpdatedData() {
        int createdId = createGood("good-" + UUID.randomUUID(), 100.0);
        String updatedName = "updated-good-" + UUID.randomUUID();

        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", createdId)
                .body("""
                        {
                          "name": "%s",
                          "price": 250.0
                        }
                        """.formatted(updatedName))
        .when()
                .patch("/goods/{id}");

        assertThat(response.statusCode())
                .as("Статус ответа при обновлении существующего товара")
                .isEqualTo(200);
        assertThat(response.jsonPath().getInt("id"))
                .as("ID обновлённого товара")
                .isEqualTo(createdId);
        assertThat(response.jsonPath().getString("name"))
                .as("Новое название товара")
                .isEqualTo(updatedName);
        assertThat(response.jsonPath().getDouble("price"))
                .as("Новая цена товара")
                .isEqualTo(250.0);
    }

    @Test
    void patchGoodWithoutRequiredNameReturns400() {
        int createdId = createGood("good-" + UUID.randomUUID(), 100.0);

        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", createdId)
                .body("""
                        {
                          "price": 250.0
                        }
                        """)
        .when()
                .patch("/goods/{id}");

        assertThat(response.statusCode())
                .as("Статус ответа при обновлении товара без обязательного name")
                .isEqualTo(400);
    }

    @Test
    void patchGoodByMissingIdReturns404() {
        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", -1)
                .body("""
                        {
                          "name": "missing-good",
                          "price": 250.0
                        }
                        """)
        .when()
                .patch("/goods/{id}");

        assertThat(response.statusCode())
                .as("Статус ответа при обновлении несуществующего товара")
                .isEqualTo(404);
    }

    @Test
    void deleteExistingGoodReturns200AndRemovesGood() {
        int createdId = createGood("good-" + UUID.randomUUID(), 100.0);

        Response deleteResponse = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .pathParam("id", createdId)
        .when()
                .delete("/goods/{id}");

        assertThat(deleteResponse.statusCode())
                .as("Статус ответа при удалении существующего товара")
                .isEqualTo(200);

    }

    @Test
    void deleteGoodByMissingIdReturns404() {
        Response response = given()
                .baseUri(BASE_URI)
                .auth().basic("admin", "secret123")
                .accept(ContentType.JSON)
                .pathParam("id", -1)
        .when()
                .delete("/goods/{id}");

        assertThat(response.statusCode())
                .as("Статус ответа при удалении несуществующего товара")
                .isEqualTo(404);
    }

    @Test
    void listGoodsReturns200AndContainsCreatedGood() {
        int createdId = createGood("good-" + UUID.randomUUID(), 100.0);

        Response response = given()
                .baseUri(BASE_URI)
                .accept(ContentType.JSON)
                .queryParam("page", 0)
                .queryParam("size", 100)
                .when()
                .get("/goods/list");

        List<Integer> goodsIds = response.jsonPath().getList("goods.id", Integer.class);

        assertThat(response.statusCode())
                .as("Статус ответа при получении списка товаров")
                .isEqualTo(200);
        assertThat(goodsIds)
                .as("Список товаров должен содержать только что созданный товар")
                .contains(createdId);
    }

}
