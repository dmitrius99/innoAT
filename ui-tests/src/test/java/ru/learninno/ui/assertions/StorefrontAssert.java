package ru.learninno.ui.assertions;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import ru.learninno.common.config.TestConfig;
import ru.learninno.ui.pages.StorefrontPage;

import java.math.BigDecimal;

/** All storefront checks are visible as separate steps in Allure. */
public final class StorefrontAssert extends AbstractAssert<StorefrontAssert, StorefrontPage> {
    private final TestConfig config = TestConfig.get();

    public StorefrontAssert(StorefrontPage actual) {
        super(actual, StorefrontAssert.class);
    }

    @Step("Проверить отображение базовых элементов витрины")
    public StorefrontAssert hasBaseElements() {
        isNotNull();
        actual.title().shouldBe(Condition.visible);
        actual.productsList().shouldBe(Condition.exist);
        return this;
    }

    @Step("Проверить наличие товара {name} на витрине")
    public StorefrontAssert hasProduct(String name) {
        actual.productByName(name).shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что корзина открыта")
    public StorefrontAssert cartIsOpen() {
        actual.cartModal().shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить товар {name} в корзине")
    public StorefrontAssert cartContains(String name) {
        actual.cartItems().shouldHave(Condition.text(name));
        return this;
    }

    @Step("Проверить количество товаров в корзине: {quantity}")
    public StorefrontAssert cartQuantityIs(int quantity) {
        actual.cartCount().shouldHave(Condition.exactText(String.valueOf(quantity)));
        return this;
    }

    @Step("Проверить итоговую стоимость {expected}")
    public StorefrontAssert totalIs(BigDecimal expected) {
        actual.totalPrice().shouldHave(Condition.exactText(expected.toPlainString()));
        return this;
    }

    @Step("Проверить, что итоговая стоимость не превышает лимит")
    public StorefrontAssert totalDoesNotExceedLimit() {
        BigDecimal actualTotal = actual.totalPriceValue();
        if (actualTotal.compareTo(config.orderLimit()) > 0) {
            failWithMessage("Expected total to be at most <%s>, but was <%s>", config.orderLimit(), actualTotal);
        }
        return this;
    }

    @Step("Проверить уведомление {message}")
    public StorefrontAssert hasNotification(String message) {
        actual.notifications().shouldHave(CollectionCondition.sizeGreaterThan(0));
        actual.notifications().last().shouldBe(Condition.visible).shouldHave(Condition.text(message));
        return this;
    }

    @Step("Проверить успешное оформление заказа")
    public StorefrontAssert orderIsSuccessful() {
        actual.title().shouldHave(Condition.exactText(config.message("order.success")));
        return this;
    }
}
