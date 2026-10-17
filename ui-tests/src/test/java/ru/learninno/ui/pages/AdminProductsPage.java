package ru.learninno.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.learninno.common.config.TestConfig;
import ru.learninno.ui.assertions.AdminProductsAssert;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.refresh;

/** PageObject of product management in the administrator panel. */
public final class AdminProductsPage {
    private final TestConfig config = TestConfig.get();

    @Step("Ввести название нового товара {name}")
    public AdminProductsPage enterName(String name) {
        newProductName().setValue(name);
        return this;
    }

    @Step("Ввести цену нового товара {price}")
    public AdminProductsPage enterPrice(BigDecimal price) {
        newProductPrice().setValue(price.toPlainString());
        return this;
    }

    @Step("Добавить товар")
    public AdminProductsPage addProduct() {
        addButton().click();
        return this;
    }

    @Step("Добавить товар {name}")
    public AdminProductsPage addProduct(String name, BigDecimal price) {
        return enterName(name).enterPrice(price).addProduct();
    }

    @Step("Изменить название товара {productId}")
    public AdminProductsPage changeName(int productId, String name) {
        productName(productId).setValue(name);
        return this;
    }

    @Step("Сохранить изменения товара {productId}")
    public AdminProductsPage saveChanges(int productId) {
        updateButton(productId).click();
        return this;
    }

    @Step("Переименовать товар {productId}")
    public AdminProductsPage renameProduct(int productId, String name) {
        return changeName(productId, name).saveChanges(productId);
    }

    @Step("Обновить список товаров")
    public AdminProductsPage refreshList() {
        refresh();
        return this;
    }

    @Step("Перейти к проверкам административной панели")
    public AdminProductsAssert check() { return new AdminProductsAssert(this); }

    public SelenideElement newProductName() { return $(config.selector("admin.product.name")); }
    public SelenideElement newProductPrice() { return $(config.selector("admin.product.price")); }
    public SelenideElement addButton() { return $(config.selector("admin.product.add")); }
    public SelenideElement table() { return $(config.selector("admin.products.table")); }
    public ElementsCollection notifications() { return $$(config.selector("notification")); }
    public SelenideElement productName(int id) { return $(config.selector("admin.product.name.by.id", id)); }
    public SelenideElement productPrice(int id) { return $(config.selector("admin.product.price.by.id", id)); }
    public SelenideElement updateButton(int id) { return $(config.selector("admin.product.update.by.id", id)); }
}
