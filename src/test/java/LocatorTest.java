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
        System.out.println("=== СТРАНИЦА ЛОГИНА ===");

        // Ожидаем загрузки страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        // id
        WebElement usernameById = driver.findElement(By.id("user-name"));
        WebElement passwordById = driver.findElement(By.id("password"));
        WebElement loginBtnById = driver.findElement(By.id("login-button"));
        System.out.println("[OK] id: найден элемент " + usernameById.getTagName());

        // name
        WebElement usernameByName = driver.findElement(By.name("user-name"));
        WebElement passwordByName = driver.findElement(By.name("password"));
        System.out.println("[OK] name: найден элемент " + usernameByName.getTagName());

        // classname
        WebElement loginLogoByClass = driver.findElement(By.className("login_logo"));
        WebElement loginWrapperByClass = driver.findElement(By.className("login_wrapper"));
        System.out.println("[OK] classname: найден элемент с текстом '" + loginLogoByClass.getText() + "'");

        // tagname
        WebElement firstDivByTag = driver.findElement(By.tagName("div"));
        WebElement formByTag = driver.findElement(By.tagName("form"));
        System.out.println("[OK] tagname: найден элемент " + firstDivByTag.getTagName());

        // xpath - поиск по атрибуту
        WebElement usernameByXpathAttr = driver.findElement(By.xpath("//input[@data-test='username']"));
        WebElement passwordByXpathAttr = driver.findElement(By.xpath("//input[@data-test='password']"));
        System.out.println("[OK] xpath (по атрибуту): найден элемент " + usernameByXpathAttr.getTagName());

        // xpath - поиск по тексту
        WebElement loginTextByXpath = driver.findElement(By.xpath("//h4[text()='Accepted usernames are:']"));
        WebElement passwordTextByXpath = driver.findElement(By.xpath("//h4[text()='Password for all users:']"));
        System.out.println("[OK] xpath (по тексту): найден элемент с текстом '" + loginTextByXpath.getText() + "'");

        // xpath - поиск по частичному совпадению атрибута
        WebElement usernameByXpathContainsAttr = driver.findElement(By.xpath("//input[contains(@data-test,'user')]"));
        System.out.println("[OK] xpath (contains атрибут): найден элемент " + usernameByXpathContainsAttr.getTagName());

        // xpath - поиск по частичному совпадению текста
        WebElement partialTextElement = driver.findElement(By.xpath("//h4[contains(text(),'usernames')]"));
        System.out.println("[OK] xpath (contains текст): найден элемент с текстом '" + partialTextElement.getText() + "'");

        // xpath - ancestor (поднимаемся от поля username до формы)
        WebElement ancestorExample = driver.findElement(By.xpath("//input[@id='user-name']//ancestor::form"));
        System.out.println("[OK] xpath ancestor: найден элемент " + ancestorExample.getTagName());

        // xpath - descendant (спускаемся от формы ко всем полям ввода)
        WebElement descendantExample = driver.findElement(By.xpath("//div[@class='login_wrapper']//descendant::input"));
        System.out.println("[OK] xpath descendant: найден элемент " + descendantExample.getTagName());

        // xpath - following (ищем кнопку логина после поля password)
        WebElement followingExample = driver.findElement(By.xpath("//input[@id='password']//following::input[@type='submit']"));
        System.out.println("[OK] xpath following: найден элемент " + followingExample.getTagName());

        // xpath - parent (родительский div для поля username)
        WebElement parentExample = driver.findElement(By.xpath("//input[@id='user-name']//parent::div"));
        System.out.println("[OK] xpath parent: найден элемент " + parentExample.getTagName());

        // xpath - preceding (ищем поле username перед полем password)
        WebElement precedingExample = driver.findElement(By.xpath("//input[@id='password']//preceding::input[@id='user-name']"));
        System.out.println("[OK] xpath preceding: найден элемент " + precedingExample.getTagName());

        // xpath - AND условие
        WebElement andConditionExample = driver.findElement(By.xpath("//input[@class='input_error form_input' and @data-test='username']"));
        System.out.println("[OK] xpath AND: найден элемент " + andConditionExample.getTagName());

        // CSS - .class
        WebElement cssClass = driver.findElement(By.cssSelector(".login_logo"));
        System.out.println("[OK] CSS .class: найден элемент с текстом '" + cssClass.getText() + "'");

        // CSS - .class1.class2
        WebElement cssClassClass = driver.findElement(By.cssSelector(".input_error.form_input"));
        System.out.println("[OK] CSS .class1.class2: найден элемент " + cssClassClass.getTagName());

        // CSS - .class1 .class2
        WebElement cssClassSpace = driver.findElement(By.cssSelector(".login_container .login_wrapper"));
        System.out.println("[OK] CSS .class1 .class2: найден элемент " + cssClassSpace.getTagName());

        // CSS - #id
        WebElement cssId = driver.findElement(By.cssSelector("#user-name"));
        System.out.println("[OK] CSS #id: найден элемент " + cssId.getTagName());

        // CSS - tagname
        WebElement cssTag = driver.findElement(By.cssSelector("form"));
        System.out.println("[OK] CSS tagname: найден элемент " + cssTag.getTagName());

        // CSS - tagname.class
        WebElement cssTagClass = driver.findElement(By.cssSelector("input.input_error"));
        System.out.println("[OK] CSS tagname.class: найден элемент " + cssTagClass.getTagName());

        // CSS - [attribute=value]
        WebElement cssAttr = driver.findElement(By.cssSelector("[data-test='username']"));
        System.out.println("[OK] CSS [attribute=value]: найден элемент " + cssAttr.getTagName());

        // Вход в систему
        usernameById.sendKeys("standard_user");
        passwordById.sendKeys("secret_sauce");
        loginBtnById.click();

        // Ожидаем загрузки страницы каталога
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-list']")));

        // ========== СТРАНИЦА КАТАЛОГА/ПРОДУКТОВ ==========
        System.out.println("\n=== СТРАНИЦА КАТАЛОГА ===");

        // CSS - [attribute~=value] (содержит слово)
        WebElement cssAttrWord = driver.findElement(By.cssSelector("[class~='inventory_item']"));
        System.out.println("[OK] CSS [attribute~=value]: найден элемент " + cssAttrWord.getTagName());

        // CSS - [attribute^=value] (начинается с)
        WebElement cssAttrStartsWith = driver.findElement(By.cssSelector("[class^='inventory_']"));
        System.out.println("[OK] CSS [attribute^=value]: найден элемент " + cssAttrStartsWith.getTagName());

        // CSS - [attribute$=value] (заканчивается на)
        WebElement cssAttrEndsWith = driver.findElement(By.cssSelector("[class$='_item']"));
        System.out.println("[OK] CSS [attribute$=value]: найден элемент " + cssAttrEndsWith.getTagName());

        // CSS - [attribute*=value] (содержит подстроку)
        WebElement cssAttrContains = driver.findElement(By.cssSelector("[class*='btn_inventory']"));
        System.out.println("[OK] CSS [attribute*=value]: найден элемент " + cssAttrContains.getTagName());

        // linktext
        WebElement linkText = driver.findElement(By.linkText("Twitter"));
        System.out.println("[OK] linktext: найден элемент " + linkText.getTagName());

        // partiallinktext
        WebElement partialLinkText = driver.findElement(By.partialLinkText("Linked"));
        System.out.println("[OK] partiallinktext: найден элемент " + partialLinkText.getTagName());

        // xpath - поиск по тексту (кнопка добавления в корзину)
        WebElement addToCartBtn = driver.findElement(By.xpath("//button[text()='Add to cart']"));
        System.out.println("[OK] xpath (по тексту) на странице каталога: найден элемент " + addToCartBtn.getTagName());

        // xpath - ancestor (поднимаемся от кнопки до карточки товара)
        WebElement ancestorNav = driver.findElement(By.xpath("//button[text()='Add to cart']//ancestor::div[@class='inventory_item']"));
        System.out.println("[OK] xpath ancestor: найден элемент " + ancestorNav.getTagName());

        // xpath - descendant (спускаемся от inventory_list ко всем карточкам товаров)
        WebElement descendantItems = driver.findElement(By.xpath("//div[@class='inventory_list']//descendant::div[@class='inventory_item']"));
        System.out.println("[OK] xpath descendant: найден элемент " + descendantItems.getTagName());

        // xpath - following (после названия товара ищем кнопку)
        WebElement followingElements = driver.findElement(By.xpath("//div[@class='inventory_item_label']//following::button"));
        System.out.println("[OK] xpath following: найден элемент " + followingElements.getTagName());

        // xpath - parent (родительский элемент для кнопки)
        WebElement parentElement = driver.findElement(By.xpath("//button[text()='Add to cart']//parent::div"));
        System.out.println("[OK] xpath parent: найден элемент " + parentElement.getTagName());

        // xpath - preceding (перед кнопкой ищем название товара)
        WebElement precedingElements = driver.findElement(By.xpath("//button[text()='Add to cart']//preceding::div[@class='inventory_item_label']"));
        System.out.println("[OK] xpath preceding: найден элемент с текстом '" + precedingElements.getText() + "'");

        // xpath - AND условие
        WebElement andConditionProduct = driver.findElement(By.xpath("//button[contains(@class, 'btn_inventory') and @data-test='add-to-cart-sauce-labs-backpack']"));
        System.out.println("[OK] xpath AND: найден элемент " + andConditionProduct.getTagName());

        // CSS для элемента с data-test
        WebElement dataTestElement = driver.findElement(By.cssSelector("[data-test='inventory-item']"));
        System.out.println("[OK] CSS [data-test='inventory-item']: найден элемент " + dataTestElement.getTagName());

        // xpath для поиска по частичному совпадению текста в названии товара
        WebElement productName = driver.findElement(By.xpath("//div[contains(text(),'Backpack')]"));
        System.out.println("[OK] xpath (contains текст) для товара: найден элемент с текстом '" + productName.getText() + "'");

        // CSS для кнопки с определенным атрибутом
        WebElement specificButton = driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']"));
        System.out.println("[OK] CSS [data-test='add-to-cart-sauce-labs-bike-light']: найден элемент " + specificButton.getTagName());

        System.out.println("\n=== ВСЕ ЛОКАТОРЫ УСПЕШНО НАЙДЕНЫ ===");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}