import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.ListIterator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("ApiTestsTask1")
public class FirstApiTest {

    @Test
        void checkStatusCodeAndBody () {
        given()
                .baseUri("http://localhost:8080/")
                .basePath("goods/list")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .header("Accept", "application/json")
                .log().all()
        .when()
                .get()
        .then()
                .log().all()
                .statusCode(200)
                .body("goods.isEmpty()", is(true));
    }

    private RequestSpecification basicParams = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .addQueryParam("page", "0")
            .addQueryParam("size", "10")
            .addHeader("Accept", "application/json")
            .log(LogDetail.ALL)
            .build();

    @Test
        void checkStatusCodeAndBodyWithSpec () {
        given()
                .spec(basicParams)
        .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.isEmpty()", is(true));
    }

    @Test
        void createAndCheckGood () {
        JsonPath responceGoodAdd = given()
            .baseUri("http://localhost:8080/")
            .basePath("goods/add")
            .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
            .auth().basic("admin", "secret123")
                .body("""
                        {
                          "name": "Gyros",
                          "price": 100
                        }
                        """)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath();

        int goodId = responceGoodAdd.getInt("data.id");
        System.out.println("Id добавленного товара: " + goodId);

        given()
                .baseUri("http://localhost:8080/")
                .basePath("goods/list")
                .header("Accept", "application/json")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .header("Accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("goods.id", hasItem(goodId));
        System.out.println("нашел товар с Id:" + goodId);
    }

    @Test
    void createAndCheckGoodWithAssertJ () {
        JsonPath responceGoodAdd = given()
                .baseUri("http://localhost:8080/")
                .basePath("goods/add")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin", "secret123")
                .body("""
                        {
                          "name": "fsaaros",
                          "price": 150
                        }
                        """)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath();

        int goodId = responceGoodAdd.getInt("data.id");
        System.out.println("Id созданного товара: " + goodId);

        JsonPath responceGoodList = given()
                .baseUri("http://localhost:8080/")
                .basePath("goods/list")
                .accept(ContentType.JSON)
                .queryParam("page", "0")
                .queryParam("size", "100")
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath();

        List<Integer> listId = responceGoodList.getList("goods.id");

        Assertions.assertThat(listId)
                .as("Созданный товар должен присутствовать в списке")
                .contains(goodId);

        System.out.println("Нашел товар с Id:" + goodId);
    }
}
