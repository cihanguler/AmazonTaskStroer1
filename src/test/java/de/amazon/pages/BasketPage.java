package de.amazon.pages;

import de.amazon.utilities.BrowserUtils;
import de.amazon.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class BasketPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public BasketPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    /**
     * Find Page WebElements
     */
    @FindBy(className = "sc-product-price")
    public List<WebElement> productPrices;

    @FindBy(xpath = "//div/span[@id='sc-subtotal-amount-activecart']/span")
    public WebElement subtotal;

    @FindBy(xpath = "//span/input[@data-action='delete']")
    public List<WebElement> deleteButtonsOnCart;

    @FindBy(xpath = "//div[@id='sc-active-cart']/div/form/div[@data-name='Active Items']/div[starts-with(@id, 'sc-active')]")
    public List<WebElement> shoppingCartProducts;

    @FindBy(id = "sc-subtotal-label-activecart")
    public WebElement subtotalQuantity;


    /**
     * sum the product prices in the basket
     *
     * @return
     */
    public double calculateTotalPriceOfProductsNew() {
        double subtotal =0.00;
        int differentProductQuantity = shoppingCartProducts.size();
            for (int i =0; i<differentProductQuantity; i++) {
                int quantity = Integer.parseInt(shoppingCartProducts.get(i).getAttribute("data-quantity"));
                double productPrice = Double.parseDouble(shoppingCartProducts.get(i).getAttribute("data-price"));
                subtotal= quantity*productPrice;
                logger.info("expected subtotal price: " + subtotal);
                }

        return Math.round(subtotal * 100.00) / 100.00;
    }

    public int calculateQuantityOfProducts() {
        int quantity=0;
        int differentProductQuantity = shoppingCartProducts.size();
        for (int i =0; i<differentProductQuantity; i++) {
             quantity += Integer.parseInt(shoppingCartProducts.get(i).getAttribute("data-quantity"));
            logger.info("expected quantity: " + quantity);
        }
        return quantity;
    }

    /**
     * convert the String Price value to double with 2 decimals after point
     *
     * @param text
     * @return
     */
    public int convertSubtotalQuantity2Integer(String text) {
        int value= Integer.parseInt(text);
        return value;

    }

    public double convert2TwoDecimalsDouble(String text) {
        double value= Double.parseDouble(text.substring(1));
        logger.info("actual subtotal price : " + value);
        return Math.round(value * 100.00) / 100.00;
    }

    /**
     * get total amount of prices, written at bottom on the page,  in double (2 decimals) format
     *
     * @return
     */
    public double getSubtotal() {
        return convert2TwoDecimalsDouble(subtotal.getText());
    }

    public int subtotalQuantity() {
        String text = subtotalQuantity.getText().trim();
        int beginIndex= text.indexOf("(")+1;
        int endIndex= text.indexOf("i")-1;
        String subtotalQuantityText = text.substring(beginIndex,endIndex);
        logger.info("actual quantity : " + subtotalQuantityText);
        return convertSubtotalQuantity2Integer(subtotalQuantityText);
    }

    public void assertPriceAndQuantity() {
        double DELTA = 0.00;
        BrowserUtils.waitFor(3);
        Assert.assertEquals("Price assertion",calculateTotalPriceOfProductsNew(), getSubtotal(),DELTA);
        Assert.assertEquals("Quantity assertion",calculateQuantityOfProducts(), subtotalQuantity());
    }

}
