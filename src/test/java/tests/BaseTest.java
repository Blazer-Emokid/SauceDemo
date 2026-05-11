package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.HashMap;

public class BaseTest {

    protected SoftAssert softAssert;
    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;

    protected static final String STANDARD_USER = "standard_user";
    protected static final String LOCKED_OUT_USER = "locked_out_user";
    protected static final String PROBLEM_USER = "problem_user";
    protected static final String PERFORMANCE_GLITCH_USER = "performance_glitch_user";
    protected static final String PASSWORD = "secret_sauce";

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(String browser) {
        WebDriver driver = createDriver(browser);
        DriverManager.setDriver(driver);
        DriverManager.setBrowser(browser);

        softAssert = new SoftAssert();

        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();

        getDriver().get("https://www.saucedemo.com/");
    }

    private WebDriver createDriver(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--log-level=3");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--private");
                firefoxOptions.addArguments("--disable-notifications");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }

        return driver;
    }

    protected void loginAsStandardUser() {
        loginPage.login(STANDARD_USER, PASSWORD);
    }

    protected void loginAs(String username, String password) {
        loginPage.login(username, password);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}