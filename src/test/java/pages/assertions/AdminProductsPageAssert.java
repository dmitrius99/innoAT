package pages.assertions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.CollectionCondition;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import pages.AdminProductsPage;

/** Проверки админского списка и формы добавления товара. */
public class AdminProductsPageAssert extends AbstractAssert<AdminProductsPageAssert, AdminProductsPage> {
    public AdminProductsPageAssert(AdminProductsPage actual) { super(actual, AdminProductsPageAssert.class); }

    @Step("Проверить видимость формы добавления товара")
    public AdminProductsPageAssert hasVisibleAddProductForm() {
        isNotNull();
        actual.newProductNameInput().shouldBe(Condition.visible);
        actual.newProductPriceInput().shouldBe(Condition.visible);
        actual.addProductButton().shouldBe(Condition.visible);
        // Пустой tbody не имеет размеров, но должен присутствовать в разметке.
        actual.productsTable().shouldBe(Condition.exist);
        return this;
    }

    @Step("Проверить значение названия нового товара «{expectedName}»")
    public AdminProductsPageAssert hasNewProductName(String expectedName) {
        isNotNull();
        actual.newProductNameInput().shouldHave(Condition.value(expectedName));
        return this;
    }

    @Step("Проверить значение цены нового товара {expectedPrice}")
    public AdminProductsPageAssert hasNewProductPrice(String expectedPrice) {
        isNotNull();
        actual.newProductPriceInput().shouldHave(Condition.value(expectedPrice));
        return this;
    }

    @Step("Проверить уведомление «{expectedText}»")
    public AdminProductsPageAssert hasNotification(String expectedText) {
        isNotNull();
        actual.notifications().shouldHave(CollectionCondition.sizeGreaterThan(0));
        actual.notifications().last().shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
        return this;
    }

    @Step("Проверить название товара с id {productId}: «{expectedName}»")
    public AdminProductsPageAssert hasProductName(int productId, String expectedName) {
        isNotNull();
        actual.productNameInput(productId)
                .shouldBe(Condition.visible)
                .shouldHave(Condition.value(expectedName));
        actual.productPriceInput(productId).shouldBe(Condition.visible);
        actual.updateProductButton(productId).shouldBe(Condition.visible);
        return this;
    }
}
