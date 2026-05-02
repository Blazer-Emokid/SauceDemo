package tests;

import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        // Используем обычный метод login, который ждет загрузки страницы
        loginPage.login(STANDARD_USER, PASSWORD);
        softAssert.assertTrue(productsPage.isProductsPageLoaded(),
                "Страница товаров должна загрузиться после успешного входа");
        softAssert.assertAll();
    }

    @Test
    public void testLockedUserLogin() {
        // Вариант 1: Используем loginWithoutWait для негативного сценария
        loginPage.loginWithoutWait(LOCKED_OUT_USER, PASSWORD);

        // Проверяем, что появилось сообщение об ошибке
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно появиться сообщение об ошибке для заблокированного пользователя");

        // Проверяем текст сообщения об ошибке
        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("locked out"),
                "Сообщение об ошибке должно указывать на блокировку пользователя. Фактическое сообщение: " + errorMessage);

        // Проверяем, что страница товаров НЕ загрузилась
        softAssert.assertFalse(loginPage.isProductsPageLoaded(),
                "Страница товаров НЕ должна загрузиться для заблокированного пользователя");

        softAssert.assertAll();
    }

    @Test
    public void testLockedUserLoginAlternative() {
        // Вариант 2: Используем метод с возвратом boolean
        boolean isLoginSuccessful = loginPage.loginAndCheckSuccess(LOCKED_OUT_USER, PASSWORD);

        // Проверяем, что вход не удался
        softAssert.assertFalse(isLoginSuccessful,
                "Вход для заблокированного пользователя должен быть неудачным");

        // Проверяем наличие сообщения об ошибке
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.",
                "Текст сообщения об ошибке не соответствует ожидаемому");

        softAssert.assertAll();
    }

    @Test
    public void testInvalidCredentials() {
        // Тест с неверными учетными данными
        loginPage.loginWithoutWait("invalid_user", "invalid_password");

        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно появиться сообщение об ошибке при неверных учетных данных");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Username and password do not match"),
                "Сообщение об ошибке должно указывать на неверные учетные данные");

        softAssert.assertAll();
    }

    @Test
    public void testEmptyCredentials() {
        // Тест с пустыми полями
        loginPage.loginWithoutWait("", "");

        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно появиться сообщение об ошибке при пустых полях");

        String errorMessage = loginPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Username is required"),
                "Сообщение должно указывать на отсутствие имени пользователя");

        softAssert.assertAll();
    }

    @Test
    public void testProblemUserLogin() {
        // Для problem_user страница все равно загружается, но с багами
        loginPage.login(PROBLEM_USER, PASSWORD);
        softAssert.assertTrue(productsPage.isProductsPageLoaded(),
                "Страница товаров должна загрузиться для problem user");
        softAssert.assertAll();
    }
}