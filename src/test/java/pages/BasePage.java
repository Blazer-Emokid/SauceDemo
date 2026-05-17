package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.DriverManager;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriverWait wait;
    public final String BASE_URL = "https://www.saucedemo.com";

    public BasePage() {
        this.wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WebElement find(By locator) {
        return getDriver().findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return getDriver().findElements(locator);
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void openBaseUrl() {
        getDriver().get(BASE_URL);
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void navigateBack() {
        getDriver().navigate().back();
    }

    public void navigateForward() {
        getDriver().navigate().forward();
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}