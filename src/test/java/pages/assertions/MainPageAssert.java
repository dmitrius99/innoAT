package pages.assertions;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;
import pages.MainPage;

import java.math.BigDecimal;

/** Проверки витрины SmartShop. */
public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {
    public MainPageAssert(MainPage actual) { super(actual, MainPageAssert.class); }

    public MainPageAssert hasVisibleBaseElements() {
        isNotNull();
        actual.mainTitle().shouldBe(Condition.visible);
        actual.productsList().shouldBe(Condition.visible);
        actual.administrationLink().shouldBe(Condition.visible);
        actual.openCartButton().shouldBe(Condition.visible);
        actual.cartCount().shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert hasCartOpen() {
        isNotNull();
        actual.cartModal().shouldBe(Condition.visible);
        actual.closeCartButton().shouldBe(Condition.visible);
        actual.cartItems().shouldBe(Condition.visible);
        actual.totalPrice().shouldBe(Condition.visible);
        actual.makeOrderButton().shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert hasProductNamed(String productName) {
        isNotNull();
        actual.productCards().findBy(Condition.attribute("data-name", productName)).shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert hasCartItemNamed(String productName) {
        isNotNull();
        actual.cartItems().shouldHave(Condition.text(productName));
        return this;
    }

    public MainPageAssert hasTotalPrice(BigDecimal expectedPrice) {
        isNotNull();
        actual.totalPrice().shouldHave(Condition.exactText(expectedPrice.toPlainString()));
        return this;
    }

    public MainPageAssert hasCartItemsCount(int expectedCount) {
        isNotNull();
        actual.cartCount().shouldHave(Condition.exactText(String.valueOf(expectedCount)));
        return this;
    }

    public MainPageAssert hasNotification(String expectedText) {
        isNotNull();
        actual.notifications().last().shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
        return this;
    }

    public MainPageAssert hasSuccessfulOrderTitle() {
        isNotNull();
        actual.mainTitle().shouldHave(Condition.exactText("Заказ успешно оформлен!"));
        return this;
    }
}
