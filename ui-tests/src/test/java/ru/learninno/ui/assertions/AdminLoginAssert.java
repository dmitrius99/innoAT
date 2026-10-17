package ru.learninno.ui.assertions;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import ru.learninno.ui.pages.AdminLoginPage;

/** Assertions for the login page, rendered as Allure steps. */
public final class AdminLoginAssert extends AbstractAssert<AdminLoginAssert, AdminLoginPage> {
    public AdminLoginAssert(AdminLoginPage actual) {
        super(actual, AdminLoginAssert.class);
    }

    @Step("Проверить видимость формы авторизации")
    public AdminLoginAssert formIsVisible() {
        actual.loginInput().shouldBe(Condition.visible);
        actual.passwordInput().shouldBe(Condition.visible);
        actual.submitButton().shouldBe(Condition.visible);
        return this;
    }
}
