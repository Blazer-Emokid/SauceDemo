import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

public class CartTest {

    private WebDriver driver;
    private WebDriverWait wait;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void checkCartItem() {
        // Создаем объект SoftAssert
        SoftAssert softAssert = new SoftAssert();

        // Шаг a: Авторизация
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Ожидаем загрузки страницы с товарами
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

        // Шаг b: Добавить товар в корзину (добавляем первый товар - Sauce Labs Backpack)
        String expectedProductName = "Sauce Labs Backpack";
        String expectedProductPrice = "$29.99";

        // Находим и запоминаем информацию о товаре до добавления в корзину
        WebElement productNameElement = driver.findElement(By.xpath("//div[contains(@class, 'inventory_item_name') and text()='" + expectedProductName + "']"));
        WebElement productPriceElement = driver.findElement(By.xpath("//div[text()='" + expectedProductName + "']/ancestor::div[@class='inventory_item_description']//div[@class='inventory_item_price']"));

        String actualProductName = productNameElement.getText();
        String actualProductPrice = productPriceElement.getText();

        // Добавляем товар в корзину
        WebElement addToCartButton = driver.findElement(By.xpath("//div[text()='" + expectedProductName + "']/ancestor::div[@class='inventory_item_description']//button[text()='Add to cart']"));
        addToCartButton.click();

        // Небольшая пауза для обновления UI
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Шаг с: Перейти в корзину
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        // Ожидаем загрузки страницы корзины
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_list")));

        // Шаг d: Проверить стоимость товара и его имя в корзине:
        // Находим название товара в корзине
        WebElement cartProductName = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + expectedProductName + "']"));
        String cartProductNameText = cartProductName.getText();

        // Находим цену товара в корзине
        WebElement cartProductPrice = driver.findElement(By.xpath("//div[text()='" + expectedProductName + "']/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']"));
        String cartProductPriceText = cartProductPrice.getText();

        // Проверки с использованием SoftAssert
        softAssert.assertEquals(cartProductNameText, expectedProductName,
                "Название товара в корзине не соответствует ожидаемому!");
        softAssert.assertEquals(cartProductPriceText, expectedProductPrice,
                "Цена товара в корзине не соответствует ожидаемой!");

        // Выполняем все проверки и собираем результаты
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
