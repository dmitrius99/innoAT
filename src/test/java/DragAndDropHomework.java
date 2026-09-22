import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("homework")
public class DragAndDropHomework {

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
    void productCanBeDraggedToCart() {
        String productName = createProductInAdmin();

        driver.get(CONFIG.standUrl());
        String productId = dragProductToCart(productName);

        driver.findElement(By.id("open-cart-btn")).click();

        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("cart-item-" + productId)
        ));
        assertTrue(cartItem.getText().contains(productName),
                "Товар после перетаскивания должен быть в корзине");
    }

    @Test
    void productCanBeRemovedFromCart() {
        String productName = createProductInAdmin();

        driver.get(CONFIG.standUrl());
        String productId = dragProductToCart(productName);
        driver.findElement(By.id("open-cart-btn")).click();

        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("cart-item-" + productId)
        ));
        cartItem.findElement(By.cssSelector("button[data-action='remove']")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("cart-item-" + productId)));
        assertTrue(driver.findElements(By.id("cart-item-" + productId)).isEmpty(),
                "Удалённый товар не должен отображаться в корзине");
    }

    private String createProductInAdmin() {
        String productName = CONFIG.starterProductName() + " " + UUID.randomUUID();

        driver.get(CONFIG.standUrl() + "/admin");
        driver.findElement(By.id("username")).sendKeys(CONFIG.adminLogin());
        driver.findElement(By.id("password")).sendKeys(CONFIG.adminPassword());
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("n-name")));
        driver.findElement(By.id("n-name")).sendKeys(productName);
        driver.findElement(By.id("n-price")).sendKeys(CONFIG.starterProductPrice().toPlainString());
        driver.findElement(By.id("add-btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".toast")
        ));
        return productName;
    }

    private String dragProductToCart(String productName) {
        WebElement productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-card[data-name='" + productName + "']")
        ));
        String productId = productCard.getAttribute("data-id");
        WebElement cartButton = driver.findElement(By.id("open-cart-btn"));

        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("""
                const source = arguments[0];
                const target = arguments[1];
                const dataTransfer = new DataTransfer();

                source.dispatchEvent(new DragEvent('dragstart', {
                    bubbles: true,
                    dataTransfer: dataTransfer
                }));
                target.dispatchEvent(new DragEvent('dragover', {
                    bubbles: true,
                    cancelable: true,
                    dataTransfer: dataTransfer
                }));
                target.dispatchEvent(new DragEvent('drop', {
                    bubbles: true,
                    cancelable: true,
                    dataTransfer: dataTransfer
                }));
                source.dispatchEvent(new DragEvent('dragend', {
                    bubbles: true,
                    dataTransfer: dataTransfer
                }));
                """, productCard, cartButton);

        wait.until(ExpectedConditions.textToBe(By.id("cart-count"), "1"));
        return productId;
    }
}
