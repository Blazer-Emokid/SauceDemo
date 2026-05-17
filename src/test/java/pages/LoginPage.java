package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

    public class LoginPage extends BasePage {

        private final By usernameField = By.id("user-name");
        private final By passwordField = By.id("password");
        private final By loginButton = By.id("login-button");
        private final By errorMessage = By.cssSelector("[data-test='error']");
        private final By successMessage = By.className("inventory_list");

        public void login(String username, String password) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            find(usernameField).sendKeys(username);
            find(passwordField).sendKeys(password);
            find(loginButton).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        }

        public void loginWithoutWait(String username, String password) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            find(usernameField).sendKeys(username);
            find(passwordField).sendKeys(password);
            find(loginButton).click();
        }

        public boolean loginAndCheckSuccess(String username, String password) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            find(usernameField).sendKeys(username);
            find(passwordField).sendKeys(password);
            find(loginButton).click();

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
                return true;
            } catch (Exception e) {
                return false;
            }
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

        public boolean isProductsPageLoaded() {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public void clearLoginForm() {
            find(usernameField).clear();
            find(passwordField).clear();
        }
    }