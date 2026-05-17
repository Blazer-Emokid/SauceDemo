package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {

    // Локаторы как константы (вместо аннотаций @FindBy)
    private final By cartList = By.className("cart_list");
    private final By cartItems = By.className("cart_item");
    private final By firstItemName = By.xpath("//div[@class='cart_item'][1]//div[@class='inventory_item_name']");
    private final By firstItemPrice = By.xpath("//div[@class='cart_item'][1]//div[@class='inventory_item_price']");
    private final By removeFirstItemButton = By.xpath("//div[@class='cart_item'][1]//button[contains(@id, 'remove')]");
    private final By checkoutButton = By.id("checkout");
    private final By emptyCartMessage = By.className("removed_cart_item");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Вспомогательный метод для получения элемента (опционально)
    private WebElement find(By locator) {
        return driver.findElement(locator);
    }

    private List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    public String getFirstItemName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
        return find(firstItemName).getText();
    }

    public String getFirstItemPrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
        return find(firstItemPrice).getText();
    }

    public boolean isItemInCart(String itemName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
        By itemByName = By.xpath("//div[@class='inventory_item_name' and text()='" + itemName + "']");
        List<WebElement> items = findAll(itemByName);
        return !items.isEmpty();
    }

    public void removeFirstItem() {
        wait.until(ExpectedConditions.elementToBeClickable(removeFirstItemButton));
        find(removeFirstItemButton).click();
    }

    public boolean isCartEmpty() {
        List<WebElement> items = findAll(cartItems);
        return items.isEmpty();
    }

    public boolean isEmptyCartItemInDom() {
        List<WebElement> emptyMessage = findAll(emptyCartMessage);
        return !emptyMessage.isEmpty();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        find(checkoutButton).click();
    }

    public int getCartItemsCount() {
        List<WebElement> items = findAll(cartItems);
        return items.size();
    }

    public boolean isCartPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartList)).isDisplayed();
    }

    // Удаление конкретного товара по имени
    public void removeItemByName(String itemName) {
        By removeButton = By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='cart_item']//button[contains(@id, 'remove')]");
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        find(removeButton).click();
    }

    // Получение всех названий товаров в корзине
    public List<String> getAllItemNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
        List<WebElement> items = findAll(By.className("inventory_item_name"));
        return items.stream().map(WebElement::getText).toList();
    }

    // Получение всех цен товаров в корзине
    public List<String> getAllItemPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
        List<WebElement> items = findAll(By.className("inventory_item_price"));
        return items.stream().map(WebElement::getText).toList();
    }
}