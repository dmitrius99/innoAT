package ru.learninno.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.learninno.common.config.TestConfig;
import ru.learninno.ui.assertions.StorefrontAssert;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/** PageObject of the customer storefront. */
public final class StorefrontPage {
    private final TestConfig config = TestConfig.get();

    @Step("Открыть витрину")
    public StorefrontPage open() {
        open(config.standUrl());
        return this;
    }

    @Step("Указать количество товара {productId}: {quantity}")
    public StorefrontPage setQuantity(int productId, int quantity) {
        productCard(productId).$(config.selector("product.quantity.input")).setValue(String.valueOf(quantity));
        return this;
    }

    @Step("Добавить {quantity} единиц товара {productId} в корзину")
    public StorefrontPage addToCart(int productId, int quantity) {
        return setQuantity(productId, quantity).addToCart(productId);
    }

    @Step("Добавить товар {productId} в корзину")
    public StorefrontPage addToCart(int productId) {
        productCard(productId).$(config.selector("product.add.button")).click();
        return this;
    }

    @Step("Открыть корзину")
    public StorefrontPage openCart() {
        $(config.selector("cart.open")).click();
        return this;
    }

    @Step("Оформить заказ")
    public StorefrontPage checkout() {
        $(config.selector("cart.checkout")).click();
        return this;
    }

    @Step("Перейти к проверкам витрины")
    public StorefrontAssert check() { return new StorefrontAssert(this); }

    public SelenideElement title() { return $(config.selector("main.title")); }
    public SelenideElement productsList() { return $(config.selector("products.list")); }
    public ElementsCollection productCards() { return $$(config.selector("product.cards")); }
    public SelenideElement productByName(String name) { return $(config.selector("product.card.by.name", name)); }
    public SelenideElement cartModal() { return $(config.selector("cart.modal")); }
    public SelenideElement cartItems() { return $(config.selector("cart.items")); }
    public SelenideElement cartCount() { return $(config.selector("cart.count")); }
    public SelenideElement totalPrice() { return $(config.selector("cart.total")); }
    public ElementsCollection notifications() { return $$(config.selector("notification")); }
    public BigDecimal totalPriceValue() { return new BigDecimal(totalPrice().text()); }

    private SelenideElement productCard(int productId) {
        return $(config.selector("product.card.by.id", productId));
    }
}
