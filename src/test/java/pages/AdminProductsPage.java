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
        $("#nm-" + productId).setValue(newName);
        return this;
    }
    public AdminProductsPage saveProductChanges(int productId) {
        $("button[data-action='update'][data-id='" + productId + "']").click();
        return this;
    }
    public AdminProductsPage editProductName(int productId, String newName) {
        return changeProductName(productId, newName).saveProductChanges(productId);
    }
    public AdminProductsPageAssert check() { return new AdminProductsPageAssert(this); }

    public SelenideElement newProductNameInput() { return newProductNameInput; }
    public SelenideElement newProductPriceInput() { return newProductPriceInput; }
    public SelenideElement addProductButton() { return addProductButton; }
    public SelenideElement productsTable() { return productsTable; }
    public ElementsCollection notifications() { return notifications; }
}
