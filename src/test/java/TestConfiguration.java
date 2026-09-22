import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

/** Loads test-run settings from test.properties. */
public final class TestConfiguration {

    private static final String CONFIG_FILE = "test.properties";
    private static final TestConfiguration INSTANCE = new TestConfiguration(loadProperties());

    private final Properties properties;

    private TestConfiguration(Properties properties) {
        this.properties = properties;
    }

    public static TestConfiguration getInstance() {
        return INSTANCE;
    }

    public String standUrl() {
        return required("stand.url");
    }

    public String apiUrl() {
        return required("api.url");
    }

    public long elementTimeoutMs() {
        return Long.parseLong(required("element.timeout.ms"));
    }

    public String loggingMode() {
        return required("logging.mode");
    }

    public String adminLogin() {
        return required("admin.login");
    }

    public String adminPassword() {
        return required("admin.password");
    }

    public String starterProductName() {
        return required("starter.product.name");
    }

    public BigDecimal starterProductPrice() {
        return new BigDecimal(required("starter.product.price"));
    }

    private String required(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("В конфигурации не задан параметр: " + key);
        }
        return value;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestConfiguration.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Не найден конфигурационный файл " + CONFIG_FILE);
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Не удалось прочитать " + CONFIG_FILE, exception);
        }
    }
}
