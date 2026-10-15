package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.assertions.AdminProductsPageAssert;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** PageObject списка товаров и формы добавления в админке. */
public class AdminProductsPage {
    private final SelenideElement newProductNameInput = $("#n-name");
    private final SelenideElement newProductPriceInput = $("#n-price");
    private final SelenideElement addProductButton = $("#add-btn");
    private final SelenideElement productsTable = $("#tbody");
    private final ElementsCollection notifications = $$(".toast");

    @Step("Ввести название нового товара «{name}»")
    public AdminProductsPage typeNewProductName(String name) { newProductNameInput.setValue(name); return this; }
    @Step("Ввести цену нового товара {price}")
    public AdminProductsPage typeNewProductPrice(BigDecimal price) {
        newProductPriceInput.setValue(price.toPlainString());
        return this;
    }
    @Step("Нажать «Добавить товар»")
    public AdminProductsPage clickAddProduct() { addProductButton.click(); return this; }

    @Step("Добавить товар «{name}» через административную панель")
    public AdminProductsPage addProduct(String name, BigDecimal price) {
        return typeNewProductName(name).typeNewProductPrice(price).clickAddProduct();
    }

    @Step("Изменить название товара с id {productId} на «{newName}»")
    public AdminProductsPage changeProductName(int productId, String newName) {
        productNameInput(productId).setValue(newName);
        return this;
    }

    @Step("Сохранить изменения товара с id {productId}")
    public AdminProductsPage saveProductChanges(int productId) {
        updateProductButton(productId).click();
        return this;
    }

    @Step("Переименовать товар с id {productId}")
    public AdminProductsPage editProductName(int productId, String newName) {
        return changeProductName(productId, newName).saveProductChanges(productId);
    }

    /** Reloads the products list to verify that edits were persisted by the server. */
    @Step("Обновить список товаров")
    public AdminProductsPage refreshProductsList() {
        com.codeborne.selenide.Selenide.refresh();
        return this;
    }

    @Step("Перейти к проверкам административной панели")
    public AdminProductsPageAssert check() { return new AdminProductsPageAssert(this); }

    public SelenideElement newProductNameInput() { return newProductNameInput; }
    public SelenideElement newProductPriceInput() { return newProductPriceInput; }
    public SelenideElement addProductButton() { return addProductButton; }
    public SelenideElement productsTable() { return productsTable; }
    public ElementsCollection notifications() { return notifications; }

    public SelenideElement productNameInput(int productId) { return $("#nm-" + productId); }
    public SelenideElement productPriceInput(int productId) { return $("#pr-" + productId); }
    public SelenideElement updateProductButton(int productId) {
        return $("button[data-action='update'][data-id='" + productId + "']");
    }
}
