package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private static final String EXPECTED_ITEM_NAME = "Sauce Labs Backpack";
    private static final String EXPECTED_ITEM_PRICE = "$29.99";

    @Test(groups = {"smoke", "regression", "cart"},
            description = "Добавление товара в корзину и проверка соответствия названия товара",
            testName = "Проверка названия товара в корзине")
    public void testCartPageNavigationAndItemName() {
        loginAsStandardUser();
        productsPage.addToCart(0);
        productsPage.goToCart();

        Assert.assertEquals(cartPage.getFirstItemName(), EXPECTED_ITEM_NAME,
                "Название товара в корзине не совпадает");
    }

    @Test(groups = {"regression", "cart"},
            description = "Проверка соответствия цены товара после добавления в корзину",
            testName = "Проверка цены товара в корзине")
    public void testCartItemPrice() {
        loginAsStandardUser();
        productsPage.addToCart(0);
        productsPage.goToCart();

        Assert.assertEquals(cartPage.getFirstItemPrice(), EXPECTED_ITEM_PRICE,
                "Цена товара в корзине не совпадает");
    }

    @Test(groups = {"regression", "cart"},
            description = "Удаление товара из корзины и проверка что корзина стала пустой",
            testName = "Проверка удаления товара из корзины")
    public void testRemoveItemFromCart() {
        loginAsStandardUser();
        productsPage.addToCart(0);
        productsPage.goToCart();

        softAssert.assertTrue(cartPage.isItemInCart(EXPECTED_ITEM_NAME),
                "Прежде чем удалять надобно в корзину товар добавить");

        cartPage.removeFirstItem();

        softAssert.assertTrue(cartPage.isCartEmpty(),
                "После удаления корзина должна быть пустой");

        softAssert.assertTrue(cartPage.isEmptyCartItemInDom(),
                "Должно отображаться сообщение о пустой корзине");

        softAssert.assertAll();
    }

    @Test(groups = {"regression", "checkout", "cart"},
            description = "Переход к оформлению заказа из корзины",
            testName = "Проверка перехода к оформлению заказа")
    public void testGoToCheckout() {
        loginAsStandardUser();
        productsPage.addToCart(0);
        productsPage.goToCart();

        cartPage.clickCheckout();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/checkout-step-one.html"),
                "Должна открыться страница оформления заказа");
    }

    @Test(groups = {"regression", "cart", "fullflow"},
            description = "Полный сценарий: добавление товара, проверка корзины и переход к оформлению",
            testName = "Полный тест корзины от добавления до оформления")
    public void testFullCartFlow() {
        loginAsStandardUser();

        productsPage.addToCart(0);
        productsPage.goToCart();

        softAssert.assertEquals(cartPage.getFirstItemName(), EXPECTED_ITEM_NAME,
                "Название товара не совпадает");
        softAssert.assertEquals(cartPage.getFirstItemPrice(), EXPECTED_ITEM_PRICE,
                "Цена товара не совпадает");

        cartPage.clickCheckout();
        softAssert.assertTrue(getDriver().getCurrentUrl().contains("/checkout-step-one.html"),
                "URL не соответствует странице оформления");

        softAssert.assertAll();
    }
}