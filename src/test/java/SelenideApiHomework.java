import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

@Tag("homework")
public class SelenideApiHomework {

    private static final TestConfiguration CONFIG = TestConfiguration.getInstance();

    private final List<Integer> createdProductIds = new ArrayList<>();
    private Map<String, String> adminCookies;

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = CONFIG.elementTimeoutMs();
        RestAssured.baseURI = CONFIG.apiUrl();
        adminCookies = given()
                .redirects().follow(false)
                .contentType(ContentType.URLENC)
                .formParam("username", CONFIG.adminLogin())
                .formParam("password", CONFIG.adminPassword())
                .post("/login")
                .then().statusCode(302)
                .extract().cookies();
    }

    @AfterEach
    void tearDown() {
        try {
            closeWebDriver();
        } finally {
            deleteCreatedProducts();
        }
    }

    @Test
    void canPayForThreeUnitsOfProduct() {
        TestProduct product = createProductViaApi(90);

        open(CONFIG.standUrl());
        addProductToCart(product, 3);
        $("#open-cart-btn").click();
        $("#makeOrder").click();

        $$(".toast").last().shouldBe(visible)
                .shouldHave(text("Заказ принят в обработку!"));
    }

    @Test
    void cartTotalIsCalculatedForDifferentProducts() {
        TestProduct firstProduct = createProductViaApi(70);
        TestProduct secondProduct = createProductViaApi(120);

        open(CONFIG.standUrl());
        addProductToCart(firstProduct, 1);
        addProductToCart(secondProduct, 1);
        $("#open-cart-btn").click();

        $("#total-price").shouldBe(visible).shouldHave(exactText("190"));
    }

    @Test
    void notificationIsShownAfterProductIsAddedInAdmin() {
        String productName = "Admin cup " + UUID.randomUUID();

        open(CONFIG.standUrl() + "/admin");
        loginToAdmin();
        $("#n-name").setValue(productName);
        $("#n-price").setValue("100");
        $("#add-btn").click();

        $(".toast").shouldBe(visible)
                .shouldHave(text("Товар успешно добавлен!"));
        createdProductIds.add(findProductIdByName(productName));
    }

    @Test
    void editedProductIsShownWithNewNameOnStorefront() {
        TestProduct product = createProductViaApi(100);
        String changedName = "Edited cup " + UUID.randomUUID();

        open(CONFIG.standUrl() + "/admin");
        loginToAdmin();
        $("#nm-" + product.id()).setValue(changedName);
        $("button[data-action='update'][data-id='" + product.id() + "']").click();
        $(".toast").shouldBe(visible).shouldHave(text("обновлен"));

        open(CONFIG.standUrl());
        $(".product-card[data-name='" + changedName + "']").shouldBe(visible);
    }

    private TestProduct createProductViaApi(int price) {
        String productName = "API cup " + UUID.randomUUID();

        Response response = given()
                .redirects().follow(false)
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + productName + "\",\"price\":" + price + "}")
                .post("/goods/add");

        response.then().statusCode(200);

        int productId = response.jsonPath().getInt("data.id");
        createdProductIds.add(productId);
        return new TestProduct(productId, productName, price);
    }

    private int findProductIdByName(String productName) {
        List<Map<String, Object>> goods = given()
                .get("/goods/list?page=0&size=1000")
                .jsonPath()
                .getList("goods");

        Map<String, Object> product = goods.stream()
                .filter(good -> productName.equals(good.get("name")))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Товар не найден: " + productName));

        return ((Number) product.get("id")).intValue();
    }

    private void deleteCreatedProducts() {
        for (Integer productId : createdProductIds) {
            given()
                    .cookies(adminCookies)
                    .delete("/goods/{id}", productId);
        }
    }

    private void loginToAdmin() {
        $("#username").setValue(CONFIG.adminLogin());
        $("#password").setValue(CONFIG.adminPassword());
        $("button[type='submit']").click();
        $("#n-name").shouldBe(visible);
    }

    private void addProductToCart(TestProduct product, int quantity) {
        SelenideElement productCard = $(".product-card[data-id='" + product.id() + "']")
                .shouldBe(visible);
        productCard.$(".qty-input").setValue(String.valueOf(quantity));
        productCard.$("button[data-action='add-to-cart']").click();
    }

    private record TestProduct(int id, String name, int price) {
    }
}
