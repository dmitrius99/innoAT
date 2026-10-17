package ru.learninno.ui.assertions;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import ru.learninno.ui.pages.AdminProductsPage;

/** Assertions for the administrator product page, rendered as Allure steps. */
public final class AdminProductsAssert extends AbstractAssert<AdminProductsAssert, AdminProductsPage> {
    public AdminProductsAssert(AdminProductsPage actual) {
        super(actual, AdminProductsAssert.class);
    }

    @Step("Проверить видимость формы добавления товара")
    public AdminProductsAssert addFormIsVisible() {
        actual.newProductName().shouldBe(Condition.visible);
        actual.newProductPrice().shouldBe(Condition.visible);
        actual.addButton().shouldBe(Condition.visible);
        actual.table().shouldBe(Condition.exist);
        return this;
    }

    @Step("Проверить уведомление {message}")
    public AdminProductsAssert hasNotification(String message) {
        actual.notifications().shouldHave(CollectionCondition.sizeGreaterThan(0));
        actual.notifications().last().shouldBe(Condition.visible).shouldHave(Condition.text(message));
        return this;
    }

    @Step("Проверить сохранённое название товара {productId}")
    public AdminProductsAssert productHasName(int productId, String expectedName) {
        actual.productName(productId).shouldBe(Condition.visible).shouldHave(Condition.value(expectedName));
        return this;
    }
}
