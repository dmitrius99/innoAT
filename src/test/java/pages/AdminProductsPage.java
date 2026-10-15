package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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

    public AdminProductsPage typeNewProductName(String name) { newProductNameInput.setValue(name); return this; }
    public AdminProductsPage typeNewProductPrice(BigDecimal price) {
        newProductPriceInput.setValue(price.toPlainString());
        return this;
    }
    public AdminProductsPage clickAddProduct() { addProductButton.click(); return this; }

    public AdminProductsPage addProduct(String name, BigDecimal price) {
        return typeNewProductName(name).typeNewProductPrice(price).clickAddProduct();
    }

    public AdminProductsPage changeProductName(int productId, String newName) {
        productNameInput(productId).setValue(newName);
        return this;
    }

    public AdminProductsPage saveProductChanges(int productId) {
        updateProductButton(productId).click();
        return this;
    }

    public AdminProductsPage editProductName(int productId, String newName) {
        return changeProductName(productId, newName).saveProductChanges(productId);
    }

    /** Reloads the products list to verify that edits were persisted by the server. */
    public AdminProductsPage refreshProductsList() {
        com.codeborne.selenide.Selenide.refresh();
        return this;
    }

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
