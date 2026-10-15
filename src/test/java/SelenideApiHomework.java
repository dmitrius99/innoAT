import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.AdminLoginPage;
import pages.AdminProductsPage;
import pages.MainPage;
import support.ApiChecks;
import support.ApiClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.restassured.RestAssured.given;

/**
 * Задания вебинара. В тестах нет прямых обращений к UI-элементам:
 * действия выполняют PageObject, проверки выполняют PageAssert.
 */
@Tag("homework")
@Tag("AllureTests")
public class SelenideApiHomework {
    private static final TestConfiguration CONFIG = TestConfiguration.getInstance();

    private final List<Integer> createdProductIds = new ArrayList<>();
    private Map<String, String> adminCookies;
    private ApiClient api;

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = CONFIG.elementTimeoutMs();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
        RestAssured.baseURI = CONFIG.apiUrl();
        RestAssured.filters(new AllureRestAssured());
        api = new ApiClient(CONFIG.apiUrl(), CONFIG.adminLogin(), CONFIG.adminPassword());
        adminCookies = api.login();
    }

    @AfterEach
    void tearDown() {
        try {
            closeWebDriver();
        } finally {
            deleteCreatedProducts();
            SelenideLogger.removeListener("AllureSelenide");
        }
    }

    @Test
    void canPayForThreeUnitsOfProduct() {
        TestProduct product = createProductViaApi(BigDecimal.valueOf(90));
        MainPage storefront = new MainPage().open(CONFIG.standUrl());

        storefront.check().hasVisibleBaseElements().hasProductNamed(product.name());
        storefront.addProductToCart(product.id(), 3).openCart();
        storefront.check().hasCartOpen()
                .hasCartItemNamed(product.name())
                .hasCartItemsCount(3)
                .hasTotalPrice(BigDecimal.valueOf(270))
                .hasTotalPriceNotGreaterThan(BigDecimal.valueOf(300));

        storefront.checkout();
        storefront.check().hasNotification("Заказ принят в обработку!")
                .hasSuccessfulOrderTitle();
    }

    @Test
    void cartTotalIsCalculatedForDifferentProducts() {
        TestProduct firstProduct = createProductViaApi(BigDecimal.valueOf(70));
        TestProduct secondProduct = createProductViaApi(BigDecimal.valueOf(120));
        MainPage storefront = new MainPage().open(CONFIG.standUrl());

        storefront.addProductToCart(firstProduct.id(), 1)
                .addProductToCart(secondProduct.id(), 1)
                .openCart();

        storefront.check().hasCartOpen()
                .hasCartItemNamed(firstProduct.name())
                .hasCartItemNamed(secondProduct.name())
                .hasCartItemsCount(2)
                .hasTotalPrice(BigDecimal.valueOf(190));
    }

    @Test
    void notificationIsShownAfterProductIsAddedInAdmin() {
        String productName = "Admin cup " + UUID.randomUUID();
        AdminLoginPage loginPage = new AdminLoginPage().open(CONFIG.standUrl());

        loginPage.check().hasVisibleLoginForm();
        loginPage.typeLogin(CONFIG.adminLogin()).check().hasLogin(CONFIG.adminLogin());
        loginPage.typePassword(CONFIG.adminPassword()).check().hasPassword(CONFIG.adminPassword());
        AdminProductsPage adminProducts = loginPage.submit();

        adminProducts.check().hasVisibleAddProductForm();
        adminProducts.typeNewProductName(productName).check().hasNewProductName(productName);
        adminProducts.typeNewProductPrice(BigDecimal.valueOf(100)).check().hasNewProductPrice("100");
        adminProducts.clickAddProduct().check().hasNotification("Товар успешно добавлен!");
        createdProductIds.add(findProductIdByName(productName));
    }

    @Test
    void editedProductIsSavedInProductsList() {
        TestProduct product = createProductViaApi(BigDecimal.valueOf(100));
        String changedName = "Edited cup " + UUID.randomUUID();
        AdminLoginPage loginPage = new AdminLoginPage().open(CONFIG.standUrl());

        AdminProductsPage adminProducts = loginPage.loginAs(CONFIG.adminLogin(), CONFIG.adminPassword());
        adminProducts.check().hasVisibleAddProductForm();
        adminProducts.editProductName(product.id(), changedName)
                .check().hasNotification("обновлен");
        adminProducts.refreshProductsList()
                .check().hasVisibleAddProductForm()
                .hasProductName(product.id(), changedName);
    }

    @Step("Подготовить товар через API")
    private TestProduct createProductViaApi(BigDecimal price) {
        String productName = "API cup " + UUID.randomUUID();
        Response response = api.addGood(productName, price);
        ApiChecks.hasStatus(response, 200);
        int productId = api.createdId(response);
        ApiChecks.hasPositiveId(productId);
        createdProductIds.add(productId);
        return new TestProduct(productId, productName, price);
    }

    @Step("Найти id товара «{productName}» через API")
    private int findProductIdByName(String productName) {
        List<Map<String, Object>> goods = api.listGoods(0, 1000)
                .jsonPath().getList("goods");
        Map<String, Object> product = goods.stream()
                .filter(good -> productName.equals(good.get("name")))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Товар не найден: " + productName));
        return ((Number) product.get("id")).intValue();
    }

    @Step("Удалить подготовленные товары через API")
    private void deleteCreatedProducts() {
        for (Integer productId : createdProductIds) {
            api.deleteGood(productId);
        }
    }

    private record TestProduct(int id, String name, BigDecimal price) { }
}
