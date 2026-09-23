package pages.assertions;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;
import pages.AdminLoginPage;

/** Базовые проверки всех элементов формы входа. */
public class AdminLoginPageAssert extends AbstractAssert<AdminLoginPageAssert, AdminLoginPage> {
    public AdminLoginPageAssert(AdminLoginPage actual) { super(actual, AdminLoginPageAssert.class); }

    public AdminLoginPageAssert hasVisibleLoginForm() {
        isNotNull();
        actual.loginInput().shouldBe(Condition.visible);
        actual.passwordInput().shouldBe(Condition.visible);
        actual.submitButton().shouldBe(Condition.visible);
        return this;
    }

    public AdminLoginPageAssert hasLogin(String expectedLogin) {
        isNotNull();
        actual.loginInput().shouldHave(Condition.value(expectedLogin));
        return this;
    }

    public AdminLoginPageAssert hasPassword(String expectedPassword) {
        isNotNull();
        actual.passwordInput().shouldHave(Condition.value(expectedPassword));
        return this;
    }
}
