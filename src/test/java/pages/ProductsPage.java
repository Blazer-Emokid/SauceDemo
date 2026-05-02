package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class ProductsPage extends BasePage {

    // Локаторы
    private final By productList = By.className("inventory_list");
    private final By addToCartButtons = By.xpath("//button[text()='Add to cart']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private WebElement find(By locator) {
        return driver.findElement(locator);
    }

    private List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    public void addToCart(int index) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productList));
        List<WebElement> buttons = findAll(addToCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void addToCart(String productName) {
        By addButton = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item_description']//button[text()='Add to cart']");
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        find(addButton).click();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        find(cartIcon).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_list")));
    }

    public String getProductName(int index) {
        List<WebElement> products = findAll(productNames);
        return products.get(index).getText();
    }

    public String getProductPrice(int index) {
        List<WebElement> prices = findAll(productPrices);
        return prices.get(index).getText();
    }

    public boolean isProductsPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productList)).isDisplayed();
    }

    public int getProductsCount() {
        return findAll(productNames).size();
    }

    public List<String> getAllProductNames() {
        return findAll(productNames).stream().map(WebElement::getText).toList();
    }
}