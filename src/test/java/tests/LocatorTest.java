package tests;

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

import java.time.Duration;
import java.util.HashMap;

public class LocatorTest {

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
    public void checkLocators() {

        // ========== СТРАНИЦА ЛОГИНА ==========
        // Ожидаем загрузки страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        // id
        WebElement usernameById = driver.findElement(By.id("user-name"));
        WebElement passwordById = driver.findElement(By.id("password"));
        WebElement loginBtnById = driver.findElement(By.id("login-button"));

        // name
        WebElement usernameByName = driver.findElement(By.name("user-name"));
        WebElement passwordByName = driver.findElement(By.name("password"));

        // classname
        WebElement loginLogoByClass = driver.findElement(By.className("login_logo"));
        WebElement loginWrapperByClass = driver.findElement(By.className("login_wrapper"));

        // tagname
        WebElement firstDivByTag = driver.findElement(By.tagName("div"));
        WebElement formByTag = driver.findElement(By.tagName("form"));

        // xpath - поиск по атрибуту
        WebElement usernameByXpathAttr = driver.findElement(By.xpath("//input[@data-test='username']"));
        WebElement passwordByXpathAttr = driver.findElement(By.xpath("//input[@data-test='password']"));

        // xpath - поиск по тексту
        WebElement loginTextByXpath = driver.findElement(By.xpath("//h4[text()='Accepted usernames are:']"));
        WebElement passwordTextByXpath = driver.findElement(By.xpath("//h4[text()='Password for all users:']"));

        // xpath - поиск по частичному совпадению атрибута
        WebElement usernameByXpathContainsAttr = driver.findElement(By.xpath("//input[contains(@data-test,'user')]"));

        // xpath - поиск по частичному совпадению текста
        WebElement partialTextElement = driver.findElement(By.xpath("//h4[contains(text(),'usernames')]"));

        // xpath - ancestor (поднимаемся от поля username до формы)
        WebElement ancestorExample = driver.findElement(By.xpath("//input[@id='user-name']//ancestor::form"));

        // xpath - descendant (спускаемся от формы ко всем полям ввода)
        WebElement descendantExample = driver.findElement(By.xpath("//div[@class='login_wrapper']//descendant::input"));

        // xpath - following (ищем кнопку логина после поля password)
        WebElement followingExample = driver.findElement(By.xpath("//input[@id='password']//following::input[@type='submit']"));

        // xpath - parent (родительский div для поля username)
        WebElement parentExample = driver.findElement(By.xpath("//input[@id='user-name']//parent::div"));

        // xpath - preceding (ищем поле username перед полем password)
        WebElement precedingExample = driver.findElement(By.xpath("//input[@id='password']//preceding::input[@id='user-name']"));

        // xpath - AND условие
        WebElement andConditionExample = driver.findElement(By.xpath("//input[@class='input_error form_input' and @data-test='username']"));

        // CSS - .class
        WebElement cssClass = driver.findElement(By.cssSelector(".login_logo"));

        // CSS - .class1.class2
        WebElement cssClassClass = driver.findElement(By.cssSelector(".input_error.form_input"));

        // CSS - .class1 .class2
        WebElement cssClassSpace = driver.findElement(By.cssSelector(".login_container .login_wrapper"));

        // CSS - #id
        WebElement cssId = driver.findElement(By.cssSelector("#user-name"));

        // CSS - tagname
        WebElement cssTag = driver.findElement(By.cssSelector("form"));

        // CSS - tagname.class
        WebElement cssTagClass = driver.findElement(By.cssSelector("input.input_error"));

        // CSS - [attribute=value]
        WebElement cssAttr = driver.findElement(By.cssSelector("[data-test='username']"));

        // Вход в систему
        usernameById.sendKeys("standard_user");
        passwordById.sendKeys("secret_sauce");
        loginBtnById.click();

        // Ожидаем загрузки страницы каталога
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-list']")));

        // ========== СТРАНИЦА КАТАЛОГА/ПРОДУКТОВ ==========

        // CSS - [attribute~=value] (содержит слово)
        WebElement cssAttrWord = driver.findElement(By.cssSelector("[class~='inventory_item']"));

        // CSS - [attribute^=value] (начинается с)
        WebElement cssAttrStartsWith = driver.findElement(By.cssSelector("[class^='inventory_']"));

        // CSS - [attribute$=value] (заканчивается на)
        WebElement cssAttrEndsWith = driver.findElement(By.cssSelector("[class$='_item']"));

        // CSS - [attribute*=value] (содержит подстроку)
        WebElement cssAttrContains = driver.findElement(By.cssSelector("[class*='btn_inventory']"));

        // linktext
        WebElement linkText = driver.findElement(By.linkText("Twitter"));

        // partiallinktext
        WebElement partialLinkText = driver.findElement(By.partialLinkText("Linked"));

        // xpath - поиск по тексту (кнопка добавления в корзину)
        WebElement addToCartBtn = driver.findElement(By.xpath("//button[text()='Add to cart']"));

        // xpath - ancestor (поднимаемся от кнопки до карточки товара)
        WebElement ancestorNav = driver.findElement(By.xpath("//button[text()='Add to cart']//ancestor::div[@class='inventory_item']"));

        // xpath - descendant (спускаемся от inventory_list ко всем карточкам товаров)
        WebElement descendantItems = driver.findElement(By.xpath("//div[@class='inventory_list']//descendant::div[@class='inventory_item']"));

        // xpath - following (после названия товара ищем кнопку)
        WebElement followingElements = driver.findElement(By.xpath("//div[@class='inventory_item_label']//following::button"));

        // xpath - parent (родительский элемент для кнопки)
        WebElement parentElement = driver.findElement(By.xpath("//button[text()='Add to cart']//parent::div"));

        // xpath - preceding (перед кнопкой ищем название товара)
        WebElement precedingElements = driver.findElement(By.xpath("//button[text()='Add to cart']//preceding::div[@class='inventory_item_label']"));

        // xpath - AND условие
        WebElement andConditionProduct = driver.findElement(By.xpath("//button[contains(@class, 'btn_inventory') and @data-test='add-to-cart-sauce-labs-backpack']"));

        // CSS для элемента с data-test
        WebElement dataTestElement = driver.findElement(By.cssSelector("[data-test='inventory-item']"));

        // xpath для поиска по частичному совпадению текста в названии товара
        WebElement productName = driver.findElement(By.xpath("//div[contains(text(),'Backpack')]"));

        // CSS для кнопки с определенным атрибутом
        WebElement specificButton = driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
