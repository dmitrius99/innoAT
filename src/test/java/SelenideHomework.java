import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selectors.withText;

@Tag("homework")
public class SelenideHomework {

    private static final TestConfiguration CONFIG = TestConfiguration.getInstance();

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = CONFIG.elementTimeoutMs();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    @Step("Проверить отображение добавленного товара на витрине")
    void productAddedInAdminIsShownOnStorefront() {
        String productName = createStarterProductInAdmin();

        open(CONFIG.standUrl());

        $(".product-card[data-name='" + productName + "']").shouldBe(visible);
    }

    @Test
    @Step("Проверить добавление товара в корзину")
    void productAddedToCartIsShownInCart() {
        String productName = createProductInAdmin(100);

        open(CONFIG.standUrl());
        addProductToCart(productName);
        $("#open-cart-btn").click();

        $("#cart-items").$(withText(productName)).shouldBe(visible);
    }

    @Test
    @Step("Проверить отказ при неверных учётных данных")
    void cannotLoginToAdminWithInvalidCredentials() {
        open(CONFIG.standUrl() + "/admin");

        $("#username").setValue("wrong-login");
        $("#password").setValue("wrong-password");
        $("button[type='submit']").click();

        $("#username").shouldBe(visible);
        $("#n-name").shouldBe(hidden);
    }

    @Test
    @Step("Проверить сохранение корзины после обновления страницы")
    void productsRemainInCartAfterPageRefresh() {
        String productName = createProductInAdmin(100);

        open(CONFIG.standUrl());
        addProductToCart(productName);
        refresh();
        $("#open-cart-btn").click();

        $("#cart-items").$(withText(productName)).shouldBe(visible);
    }

    @Test
    @Step("Проверить предупреждение при сумме заказа больше лимита")
    void alertIsShownWhenOrderCostsMoreThan300Rubles() {
        String productName = createProductInAdmin(301);

        open(CONFIG.standUrl());
        addProductToCart(productName);
        $("#open-cart-btn").click();
        $("#makeOrder").click();

        confirm("[SmartShop]: Денег не хватает! Сумма 301 ₽ превышает лимит 300 ₽.");
    }

    @Step("Создать товар в админ-панели по цене {price}")
    private String createProductInAdmin(int price) {
        String productName = "Cup " + UUID.randomUUID();

        open(CONFIG.standUrl() + "/admin");
        loginToAdmin();
        $("#n-name").setValue(productName);
        $("#n-price").setValue(String.valueOf(price));
        $("#add-btn").click();

        $(".toast").shouldBe(visible);
        return productName;
    }

    @Step("Создать стартовый товар в админ-панели")
    private String createStarterProductInAdmin() {
        String productName = CONFIG.starterProductName() + " " + UUID.randomUUID();

        open(CONFIG.standUrl() + "/admin");
        loginToAdmin();
        $("#n-name").setValue(productName);
        $("#n-price").setValue(CONFIG.starterProductPrice().toPlainString());
        $("#add-btn").click();

        $(".toast").shouldBe(visible);
        return productName;
    }

    @Step("Авторизоваться в админ-панели")
    private void loginToAdmin() {
        $("#username").setValue(CONFIG.adminLogin());
        $("#password").setValue(CONFIG.adminPassword());
        $("button[type='submit']").click();

        $("#n-name").shouldBe(visible);
    }

    @Step("Добавить товар «{productName}» в корзину")
    private void addProductToCart(String productName) {
        SelenideElement productCard = $(".product-card[data-name='" + productName + "']")
                .shouldBe(visible);
        productCard.$("button[data-action='add-to-cart']").click();
    }
}
