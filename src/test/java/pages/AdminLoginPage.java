package pages;

import com.codeborne.selenide.SelenideElement;
import pages.assertions.AdminLoginPageAssert;

import static com.codeborne.selenide.Selenide.$;

/** PageObject формы авторизации: логин, пароль и кнопка входа. */
public class AdminLoginPage {
    private final SelenideElement loginInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");

    public AdminLoginPage open(String baseUrl) {
        com.codeborne.selenide.Selenide.open(baseUrl + "/admin");
        return this;
    }
    public AdminLoginPage typeLogin(String login) { loginInput.setValue(login); return this; }
    public AdminLoginPage typePassword(String password) { passwordInput.setValue(password); return this; }
    public AdminProductsPage submit() { submitButton.click(); return new AdminProductsPage(); }
    public AdminProductsPage loginAs(String login, String password) {
        return typeLogin(login).typePassword(password).submit();
    }
    public AdminLoginPageAssert check() { return new AdminLoginPageAssert(this); }

    public SelenideElement loginInput() { return loginInput; }
    public SelenideElement passwordInput() { return passwordInput; }
    public SelenideElement submitButton() { return submitButton; }
}
