package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.HashMap;

public class BaseTest {

    protected WebDriver driver;
    protected SoftAssert softAssert;

    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;

    // Константы для тестов
    protected static final String STANDARD_USER = "standard_user";
    protected static final String LOCKED_OUT_USER = "locked_out_user";
    protected static final String PROBLEM_USER = "problem_user";
    protected static final String PASSWORD = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--log-level=3");

        driver = new ChromeDriver(options);
        softAssert = new SoftAssert();

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);

        driver.get("https://www.saucedemo.com/");
    }

    // Метод для логина стандартным пользователем
    protected void loginAsStandardUser() {
        loginPage.login(STANDARD_USER, PASSWORD);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}