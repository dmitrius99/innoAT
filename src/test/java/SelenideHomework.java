import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
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

    private static final String BASE_URL = "http://localhost:8080";
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "secret123";

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10_000;
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void productAddedInAdminIsShownOnStorefront() {
        String productName = createProductInAdmin(100);

        open(BASE_URL);

        $(".product-card[data-name='" + productName + "']").shouldBe(visible);
    }

    @Test
    void productAddedToCartIsShownInCart() {
        String productName = createProductInAdmin(100);

        open(BASE_URL);
        addProductToCart(productName);
        $("#open-cart-btn").click();

        $("#cart-items").$(withText(productName)).shouldBe(visible);
    }

    @Test
    void cannotLoginToAdminWithInvalidCredentials() {
        open(BASE_URL + "/admin");

        $("#username").setValue("wrong-login");
        $("#password").setValue("wrong-password");
        $("button[type='submit']").click();

        $("#username").shouldBe(visible);
        $("#n-name").shouldBe(hidden);
    }

    @Test
    void productsRemainInCartAfterPageRefresh() {
        String productName = createProductInAdmin(100);

        open(BASE_URL);
        addProductToCart(productName);
        refresh();
        $("#open-cart-btn").click();

        $("#cart-items").$(withText(productName)).shouldBe(visible);
    }

    @Test
    void alertIsShownWhenOrderCostsMoreThan300Rubles() {
        String productName = createProductInAdmin(301);

        open(BASE_URL);
        addProductToCart(productName);
        $("#open-cart-btn").click();
        $("#makeOrder").click();

        confirm("[SmartShop]: Денег не хватает! Сумма 301 ₽ превышает лимит 300 ₽.");
    }

    private String createProductInAdmin(int price) {
        String productName = "Cup " + UUID.randomUUID();

        open(BASE_URL + "/admin");
        loginToAdmin();
        $("#n-name").setValue(productName);
        $("#n-price").setValue(String.valueOf(price));
        $("#add-btn").click();

        $(".toast").shouldBe(visible);
        return productName;
    }

    private void loginToAdmin() {
        $("#username").setValue(ADMIN_LOGIN);
        $("#password").setValue(ADMIN_PASSWORD);
        $("button[type='submit']").click();

        $("#n-name").shouldBe(visible);
    }

    private void addProductToCart(String productName) {
        SelenideElement productCard = $(".product-card[data-name='" + productName + "']")
                .shouldBe(visible);
        productCard.$("button[data-action='add-to-cart']").click();
    }
}
