package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // Локаторы
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By successMessage = By.className("inventory_list");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private WebElement find(By locator) {
        return driver.findElement(locator);
    }

    // Обычный вход (ждем загрузки страницы товаров)
    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        find(usernameField).sendKeys(username);
        find(passwordField).sendKeys(password);
        find(loginButton).click();
        // Ждем загрузки страницы с товарами
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
    }

    // Вход без ожидания страницы товаров (для негативных тестов)
    public void loginWithoutWait(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        find(usernameField).sendKeys(username);
        find(passwordField).sendKeys(password);
        find(loginButton).click();
    }

    // Вход с обработкой разных сценариев
    public boolean loginAndCheckSuccess(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        find(usernameField).sendKeys(username);
        find(passwordField).sendKeys(password);
        find(loginButton).click();

        // Проверяем, появилась ли страница с товарами или сообщение об ошибке
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true; // Успешный вход
        } catch (Exception e) {
            return false; // Вход не удался
        }
    }

    public boolean isLoginPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).isDisplayed();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return find(errorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clearLoginForm() {
        find(usernameField).clear();
        find(passwordField).clear();
    }

    public boolean isProductsPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}