package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.assertions.AdminLoginPageAssert;

import static com.codeborne.selenide.Selenide.$;

/** PageObject формы авторизации: логин, пароль и кнопка входа. */
public class AdminLoginPage {
    private final SelenideElement loginInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");

    @Step("Открыть страницу администратора")
    public AdminLoginPage open(String baseUrl) {
        com.codeborne.selenide.Selenide.open(baseUrl + "/admin");
        return this;
    }
    @Step("Ввести логин «{login}»")
    public AdminLoginPage typeLogin(String login) { loginInput.setValue(login); return this; }
    @Step("Ввести пароль")
    public AdminLoginPage typePassword(String password) { passwordInput.setValue(password); return this; }
    @Step("Нажать кнопку входа")
    public AdminProductsPage submit() { submitButton.click(); return new AdminProductsPage(); }
    @Step("Войти в административную панель")
    public AdminProductsPage loginAs(String login, String password) {
        return typeLogin(login).typePassword(password).submit();
    }
    @Step("Перейти к проверкам формы входа")
    public AdminLoginPageAssert check() { return new AdminLoginPageAssert(this); }

    public SelenideElement loginInput() { return loginInput; }
    public SelenideElement passwordInput() { return passwordInput; }
    public SelenideElement submitButton() { return submitButton; }
}
