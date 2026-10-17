package ru.learninno.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.learninno.common.config.TestConfig;
import ru.learninno.ui.assertions.AdminLoginAssert;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/** PageObject of the administrator login page. */
public final class AdminLoginPage {
    private final TestConfig config = TestConfig.get();

    @Step("Открыть страницу администратора")
    public AdminLoginPage open() {
        open(config.adminUrl());
        return this;
    }

    @Step("Ввести логин {login}")
    public AdminLoginPage enterLogin(String login) {
        loginInput().setValue(login);
        return this;
    }

    @Step("Ввести пароль")
    public AdminLoginPage enterPassword(String password) {
        passwordInput().setValue(password);
        return this;
    }

    @Step("Подтвердить вход")
    public AdminProductsPage submit() {
        submitButton().click();
        return new AdminProductsPage();
    }

    @Step("Авторизоваться в административной панели")
    public AdminProductsPage loginAsAdmin() {
        return enterLogin(config.adminLogin()).enterPassword(config.adminPassword()).submit();
    }

    @Step("Перейти к проверкам формы авторизации")
    public AdminLoginAssert check() { return new AdminLoginAssert(this); }

    public SelenideElement loginInput() { return $(config.selector("login.username")); }
    public SelenideElement passwordInput() { return $(config.selector("login.password")); }
    public SelenideElement submitButton() { return $(config.selector("login.submit")); }
}
