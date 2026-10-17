package ru.learninno.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.learninno.api.assertions.ApiAssert;
import ru.learninno.api.client.BasicGoodsApi;
import ru.learninno.api.client.RestApiBuilder;
import ru.learninno.api.model.GoodRequest;
import ru.learninno.common.config.TestConfig;
import ru.learninno.ui.pages.AdminLoginPage;
import ru.learninno.ui.pages.AdminProductsPage;
import ru.learninno.ui.pages.StorefrontPage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/** UI cases from webinar VI. UI setup is prepared through API to keep tests fast and deterministic. */
@Epic("SmartShop")
@Feature("Вебинар VI: пользовательские сценарии")
@Tag("UI")
@Tag("Smoke")
class WebinarSixUiTest {
    private final TestConfig config = TestConfig.get();
    private final BasicGoodsApi goodsApi = new BasicGoodsApi(new RestApiBuilder(config), config);
    private final List<Integer> productsToDelete = new ArrayList<>();

    @BeforeEach
    void configureBrowser() {
        Configuration.browser = config.browserName();
        Configuration.headless = config.browserHeadless();
        Configuration.browserSize = config.browserSize();
        Configuration.timeout = config.elementTimeoutMs();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @AfterEach
    void cleanUp() {
        try {
            closeWebDriver();
        } finally {
            productsToDelete.forEach(goodsApi::delete);
            SelenideLogger.removeListener("AllureSelenide");
        }
    }

    @Test
    @Story("Оформление заказа")
    void customerCanPayForThreeUnitsOfProduct() {
        TestProduct product = createProduct(config.orderProductPrice());
        StorefrontPage storefront = new StorefrontPage().open();

        storefront.check().hasBaseElements().hasProduct(product.name());
        storefront.addToCart(product.id(), config.orderQuantity()).openCart();
        storefront.check()
                .cartIsOpen()
                .cartContains(product.name())
                .cartQuantityIs(config.orderQuantity())
                .totalIs(config.expectedOrderTotal())
                .totalDoesNotExceedLimit();

        storefront.checkout();
        storefront.check()
                .hasNotification(config.message("order.accepted"))
                .orderIsSuccessful();
    }

    @Test
    @Story("Корзина")
    void cartTotalIsCalculatedForDifferentProducts() {
        TestProduct first = createProduct(config.firstProductPrice());
        TestProduct second = createProduct(config.secondProductPrice());
        BigDecimal expectedTotal = first.price().add(second.price());

        StorefrontPage storefront = new StorefrontPage().open();
        storefront.addToCart(first.id()).addToCart(second.id()).openCart();
        storefront.check()
                .cartIsOpen()
                .cartContains(first.name())
                .cartContains(second.name())
                .totalIs(expectedTotal);
    }

    @Test
    @Story("Администрирование")
    void productCreationInAdminShowsNotification() {
        String name = uniqueProductName();
        AdminLoginPage loginPage = new AdminLoginPage().open();
        loginPage.check().formIsVisible();

        AdminProductsPage productsPage = loginPage.loginAsAdmin();
        productsPage.check().addFormIsVisible();
        productsPage.addProduct(name, config.defaultPrice())
                .check().hasNotification(config.message("product.added"));
        productsToDelete.add(findProductId(name));
    }

    @Test
    @Story("Администрирование")
    void editedProductIsSavedInProductList() {
        TestProduct product = createProduct(config.defaultPrice());
        String renamedProduct = uniqueProductName();
        AdminProductsPage productsPage = new AdminLoginPage().open().loginAsAdmin();

        productsPage.renameProduct(product.id(), renamedProduct)
                .check().hasNotification(config.message("product.updated"));
        productsPage.refreshList().check().productHasName(product.id(), renamedProduct);
    }

    @Step("Подготовить товар через API")
    private TestProduct createProduct(BigDecimal price) {
        String name = uniqueProductName();
        Response response = goodsApi.create(new GoodRequest(name, price));
        ApiAssert.status(response, config.successStatus());
        ApiAssert.createdIdIsPositive(response);
        int id = ApiAssert.createdId(response);
        productsToDelete.add(id);
        return new TestProduct(id, name, price);
    }

    @Step("Найти идентификатор добавленного товара через API")
    private int findProductId(String name) {
        Response response = goodsApi.list();
        ApiAssert.status(response, config.successStatus());
        return response.jsonPath().getList("goods").stream()
                .filter(java.util.Map.class::isInstance)
                .map(java.util.Map.class::cast)
                .filter(product -> name.equals(product.get("name")))
                .map(product -> ((Number) product.get("id")).intValue())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Created product was not found"));
    }

    @Step("Сгенерировать уникальное название товара")
    private String uniqueProductName() {
        return config.productNamePrefix() + UUID.randomUUID();
    }

    private record TestProduct(int id, String name, BigDecimal price) { }
}
