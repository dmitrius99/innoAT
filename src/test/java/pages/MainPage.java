package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import pages.assertions.MainPageAssert;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/** PageObject витрины SmartShop. */
public class MainPage {
    private final SelenideElement mainTitle = $("#main-title");
    private final SelenideElement productsList = $("#products-list");
    private final ElementsCollection productCards = $$(".product-card");
    private final SelenideElement administrationLink = $("a[href='/admin']");
    private final SelenideElement openCartButton = $("#open-cart-btn");
    private final SelenideElement cartCount = $("#cart-count");
    private final SelenideElement cartModal = $("#cartModal");
    private final SelenideElement closeCartButton = $("#close-modal");
    private final SelenideElement cartItems = $("#cart-items");
    private final SelenideElement totalPrice = $("#total-price");
    private final SelenideElement makeOrderButton = $("#makeOrder");
    private final ElementsCollection notifications = $$(".toast");

    public MainPage open(String baseUrl) { open(baseUrl); return this; }
    public MainPage openAdministration() { administrationLink.click(); return this; }
    public MainPage openCart() { openCartButton.click(); return this; }
    public MainPage closeCart() { closeCartButton.click(); return this; }
    public MainPage setProductQuantity(int productId, int quantity) {
        productCard(productId).$(".qty-input").setValue(String.valueOf(quantity));
        return this;
    }
    public MainPage addProductToCart(int productId, int quantity) {
        return setProductQuantity(productId, quantity).addProductToCart(productId);
    }
    public MainPage addProductToCart(int productId) {
        productCard(productId).$("button[data-action='add-to-cart']").click();
        return this;
    }
    public MainPage checkout() { makeOrderButton.click(); return this; }
    public MainPageAssert check() { return new MainPageAssert(this); }

    public SelenideElement mainTitle() { return mainTitle; }
    public SelenideElement productsList() { return productsList; }
    public ElementsCollection productCards() { return productCards; }
    public SelenideElement administrationLink() { return administrationLink; }
    public SelenideElement openCartButton() { return openCartButton; }
    public SelenideElement cartCount() { return cartCount; }
    public SelenideElement cartModal() { return cartModal; }
    public SelenideElement closeCartButton() { return closeCartButton; }
    public SelenideElement cartItems() { return cartItems; }
    public SelenideElement totalPrice() { return totalPrice; }
    public SelenideElement makeOrderButton() { return makeOrderButton; }
    public ElementsCollection notifications() { return notifications; }
    public String totalPriceText() { return totalPrice.text(); }
    public BigDecimal totalPriceValue() { return new BigDecimal(totalPriceText()); }

    private SelenideElement productCard(int productId) {
        return $(".product-card[data-id='" + productId + "']");
    }
}
