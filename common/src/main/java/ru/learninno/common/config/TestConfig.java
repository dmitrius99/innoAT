package ru.learninno.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/** Single source of test settings and test data for every module. */
public final class TestConfig {
    private static final String CONFIG_RESOURCE = "test.properties";
    private static final TestConfig INSTANCE = new TestConfig(load());

    private final Properties properties;

    private TestConfig(Properties properties) {
        this.properties = properties;
    }

    public static TestConfig get() { return INSTANCE; }

    public String standUrl() { return value("stand.url"); }
    public String apiUrl() { return value("api.url"); }
    public String adminLogin() { return value("admin.login"); }
    public String adminPassword() { return value("admin.password"); }
    public String invalidLogin() { return value("invalid.login"); }
    public String invalidPassword() { return value("invalid.password"); }
    public String loginPath() { return value("api.login.path"); }
    public String addGoodPath() { return value("api.goods.add.path"); }
    public String goodPath() { return value("api.goods.item.path"); }
    public String goodsListPath() { return value("api.goods.list.path"); }
    public int successStatus() { return integer("api.status.success"); }
    public int validationErrorStatus() { return integer("api.status.validation.error"); }
    public int notFoundStatus() { return integer("api.status.not.found"); }
    public int redirectStatus() { return integer("api.status.redirect"); }
    public int listPage() { return integer("api.list.page"); }
    public int listSize() { return integer("api.list.size"); }
    public int missingId() { return integer("api.missing.id"); }
    public String browserName() { return value("browser.name"); }
    public boolean browserHeadless() { return Boolean.parseBoolean(value("browser.headless")); }
    public String browserSize() { return value("browser.size"); }
    public long elementTimeoutMs() { return Long.parseLong(value("element.timeout.ms")); }
    public String productNamePrefix() { return value("product.name.prefix"); }
    public String starterProductName() { return value("product.starter.name"); }
    public BigDecimal defaultPrice() { return decimal("product.price.default"); }
    public BigDecimal updatedPrice() { return decimal("product.price.updated"); }
    public BigDecimal firstProductPrice() { return decimal("product.price.first"); }
    public BigDecimal secondProductPrice() { return decimal("product.price.second"); }
    public BigDecimal orderProductPrice() { return decimal("product.price.order"); }
    public int orderQuantity() { return integer("product.quantity.order"); }
    public BigDecimal expectedOrderTotal() { return decimal("order.total"); }
    public BigDecimal orderLimit() { return decimal("order.limit"); }
    public String adminUrl() { return standUrl() + value("ui.admin.path"); }
    public String selector(String name) { return value("selector." + name); }
    public String selector(String name, Object... args) { return selector(name).formatted(args); }
    public String message(String name) { return value("message." + name); }

    private int integer(String key) { return Integer.parseInt(value(key)); }
    private BigDecimal decimal(String key) { return new BigDecimal(value(key)); }

    private String value(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required configuration key: " + key);
        }
        return value;
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream stream = TestConfig.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE)) {
            if (stream == null) {
                throw new IllegalStateException("Configuration resource not found: " + CONFIG_RESOURCE);
            }
            properties.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load test configuration", exception);
        }
    }
}
