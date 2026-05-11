package tests;

import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression", "positive"},
            description = "Проверка успешного входа в систему с валидными учетными данными standard_user",
            testName = "Успешная авторизация")
    public void testSuccessfulLogin() {
        loginPage.login(STANDARD_USER, PASSWORD);
        softAssert.assertTrue(productsPage.isProductsPageLoaded(),
                "Страница товаров должна загрузиться после успешного входа");
        softAssert.assertAll();
    }

    @Test(groups = {"regression", "negative"},
            description = "Проверка входа заблокированного пользователя locked_out_user с ожиданием сообщения об ошибке",
            testName = "Вход заблокированного пользователя")
    public void testLockedUserLogin() {
        loginPage.loginWithoutWait(LOCKED_OUT_USER, PASSWORD);

        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно появиться сообщение об ошибке для заблокированного пользователя");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("locked out"),
                "Сообщение об ошибке должно указывать на блокировку пользователя. Фактическое сообщение: " + errorMessage);

        softAssert.assertFalse(loginPage.isProductsPageLoaded(),
                "Страница товаров НЕ должна загрузиться для заблокированного пользователя");

        softAssert.assertAll();
    }

    @Test(groups = {"regression", "negative"},
            description = "Альтернативная проверка входа заблокированного пользователя с использованием метода loginAndCheckSuccess",
            testName = "Вход заблокированного пользователя (альтернативный метод)")
    public void testLockedUserLoginAlternative() {
        boolean isLoginSuccessful = loginPage.loginAndCheckSuccess(LOCKED_OUT_USER, PASSWORD);

        softAssert.assertFalse(isLoginSuccessful,
                "Вход для заблокированного пользователя должен быть неудачным");

        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.",
                "Текст сообщения об ошибке не соответствует ожидаемому");

        softAssert.assertAll();
    }

    @Test(groups = {"regression", "negative"},
            description = "Проверка входа с неверными учетными данными",
            testName = "Вход с неверными учетными данными")
    public void testInvalidCredentials() {
        loginPage.loginWithoutWait("invalid_user", "invalid_password");

        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно появиться сообщение об ошибке при неверных учетных данных");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Username and password do not match"),
                "Сообщение об ошибке должно указывать на неверные учетные данные");

        softAssert.assertAll();
    }

    @Test(groups = {"regression", "negative"},
            description = "Проверка входа с пустыми полями username и password",
            testName = "Вход с пустыми учетными данными")
    public void testEmptyCredentials() {
        loginPage.loginWithoutWait("", "");

        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно появиться сообщение об ошибке при пустых полях");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Username is required"),
                "Сообщение должно указывать на отсутствие имени пользователя");

        softAssert.assertAll();
    }

    @Test(groups = {"regression", "positive"},
            description = "Проверка входа пользователя problem_user (страница загружается, но с багами)",
            testName = "Вход problem_user")
    public void testProblemUserLogin() {
        loginPage.login(PROBLEM_USER, PASSWORD);
        softAssert.assertTrue(productsPage.isProductsPageLoaded(),
                "Страница товаров должна загрузиться для problem user");
        softAssert.assertAll();
    }
}