package pages.assertions;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;
import pages.AdminProductsPage;

/** Проверки админского списка и формы добавления товара. */
public class AdminProductsPageAssert extends AbstractAssert<AdminProductsPageAssert, AdminProductsPage> {
    public AdminProductsPageAssert(AdminProductsPage actual) { super(actual, AdminProductsPageAssert.class); }

    public AdminProductsPageAssert hasVisibleAddProductForm() {
        isNotNull();
        actual.newProductNameInput().shouldBe(Condition.visible);
        actual.newProductPriceInput().shouldBe(Condition.visible);
        actual.addProductButton().shouldBe(Condition.visible);
        actual.productsTable().shouldBe(Condition.visible);
        return this;
    }

    public AdminProductsPageAssert hasNewProductName(String expectedName) {
        isNotNull();
        actual.newProductNameInput().shouldHave(Condition.value(expectedName));
        return this;
    }

    public AdminProductsPageAssert hasNewProductPrice(String expectedPrice) {
        isNotNull();
        actual.newProductPriceInput().shouldHave(Condition.value(expectedPrice));
        return this;
    }

    public AdminProductsPageAssert hasNotification(String expectedText) {
        isNotNull();
        actual.notifications().last().shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
        return this;
    }
}
