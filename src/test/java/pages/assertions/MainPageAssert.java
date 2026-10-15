package pages.assertions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.CollectionCondition;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import pages.MainPage;

import java.math.BigDecimal;

/** Проверки витрины SmartShop. */
public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {
    public MainPageAssert(MainPage actual) { super(actual, MainPageAssert.class); }

    @Step("Проверить базовые элементы витрины")
    public MainPageAssert hasVisibleBaseElements() {
        isNotNull();
        actual.mainTitle().shouldBe(Condition.visible);
        // Пустая сетка не имеет размеров, поэтому Selenide считает её hidden.
        // Для контейнера достаточно проверить наличие; карточки проверяются отдельно.
        actual.productsList().shouldBe(Condition.exist);
        actual.administrationLink().shouldBe(Condition.visible);
        actual.openCartButton().shouldBe(Condition.visible);
        actual.cartCount().shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что корзина открыта")
    public MainPageAssert hasCartOpen() {
        isNotNull();
        actual.cartModal().shouldBe(Condition.visible);
        actual.closeCartButton().shouldBe(Condition.visible);
        actual.cartItems().shouldBe(Condition.visible);
        actual.totalPrice().shouldBe(Condition.visible);
        actual.makeOrderButton().shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить видимость карточек товаров")
    public MainPageAssert hasVisibleProductCards() {
        isNotNull();
        actual.productCards().first().shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить отображение товара «{productName}»")
    public MainPageAssert hasProductNamed(String productName) {
        isNotNull();
        actual.productCards().findBy(Condition.attribute("data-name", productName)).shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить товар «{productName}» в корзине")
    public MainPageAssert hasCartItemNamed(String productName) {
        isNotNull();
        actual.cartItems().shouldHave(Condition.text(productName));
        return this;
    }

    @Step("Проверить итоговую цену {expectedPrice}")
    public MainPageAssert hasTotalPrice(BigDecimal expectedPrice) {
        isNotNull();
        actual.totalPrice().shouldHave(Condition.exactText(expectedPrice.toPlainString()));
        return this;
    }

    @Step("Проверить, что итоговая цена не больше {maximumPrice}")
    public MainPageAssert hasTotalPriceNotGreaterThan(BigDecimal maximumPrice) {
        isNotNull();
        BigDecimal actualPrice = actual.totalPriceValue();
        if (actualPrice.compareTo(maximumPrice) > 0) {
            failWithMessage("Expected cart total to be at most <%s>, but was <%s>", maximumPrice, actualPrice);
        }
        return this;
    }

    @Step("Проверить количество товаров в корзине: {expectedCount}")
    public MainPageAssert hasCartItemsCount(int expectedCount) {
        isNotNull();
        actual.cartCount().shouldHave(Condition.exactText(String.valueOf(expectedCount)));
        return this;
    }

    @Step("Проверить уведомление «{expectedText}»")
    public MainPageAssert hasNotification(String expectedText) {
        isNotNull();
        actual.notifications().shouldHave(CollectionCondition.sizeGreaterThan(0));
        actual.notifications().last().shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
        return this;
    }

    @Step("Проверить успешное оформление заказа")
    public MainPageAssert hasSuccessfulOrderTitle() {
        isNotNull();
        actual.mainTitle().shouldHave(Condition.exactText("Заказ успешно оформлен!"));
        return this;
    }
}
