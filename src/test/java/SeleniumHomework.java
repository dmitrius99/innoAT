import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("homework")
public class SeleniumHomework {

    private static final TestConfiguration CONFIG = TestConfiguration.getInstance();

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofMillis(CONFIG.elementTimeoutMs()));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void productAddedInAdminIsShownOnStorefront() {
        String productName = createProductInAdmin();

        driver.get(CONFIG.standUrl());

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-card[data-name='" + productName + "']")
        ));

        assertTrue(driver.getPageSource().contains(productName),
                "Добавленный товар должен отображаться на витрине");
    }

    @Test
    void productAddedToCartIsShownInCart() {
        String productName = createProductInAdmin();
        driver.get(CONFIG.standUrl());
        addProductToCart(productName);

        driver.findElement(By.id("open-cart-btn")).click();

        WebElement cartItems = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-items")));
        assertTrue(cartItems.getText().contains(productName),
                "Добавленный товар должен отображаться в корзине");
    }

    @Test
    void cannotLoginToAdminWithInvalidCredentials() {
        driver.get(CONFIG.standUrl() + "/admin");

        driver.findElement(By.id("username")).sendKeys("wrong-login");
        driver.findElement(By.id("password")).sendKeys("wrong-password");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("error"));

        assertTrue(driver.getCurrentUrl().contains("error"),
                "После неверного входа пользователь не должен попасть в админку");
    }

    @Test
    void productsRemainInCartAfterPageRefresh() {
        String productName = createProductInAdmin();
        driver.get(CONFIG.standUrl());
        addProductToCart(productName);

        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-list")));
        driver.findElement(By.id("open-cart-btn")).click();

        WebElement cartItems = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cart-items")));
        assertTrue(cartItems.getText().contains(productName),
                "Товар должен остаться в корзине после обновления страницы");
    }

    private String createProductInAdmin() {
        String productName = CONFIG.starterProductName() + " " + System.currentTimeMillis();

        driver.get(CONFIG.standUrl() + "/admin");
        loginToAdmin();

        driver.findElement(By.id("n-name")).sendKeys(productName);
        driver.findElement(By.id("n-price")).sendKeys(CONFIG.starterProductPrice().toPlainString());
        driver.findElement(By.id("add-btn")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#tbody input[value='" + productName + "']")
        ));

        return productName;
    }

    private void loginToAdmin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                .sendKeys(CONFIG.adminLogin());
        driver.findElement(By.id("password")).sendKeys(CONFIG.adminPassword());
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("n-name")));
    }

    private void addProductToCart(String productName) {
        WebElement productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-card[data-name='" + productName + "']")
        ));
        productCard.findElement(By.cssSelector("button[data-action='add-to-cart']")).click();
    }
}
