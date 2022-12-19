package de.amazon.pages;

import de.amazon.utilities.BrowserUtils;
import de.amazon.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.amazon.utilities.BrowserUtils.*;

public class ProductPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public ProductPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    /**
     * create logger to log infos, errors etc.
     */
    Logger logger = LoggerFactory.getLogger(ProductPage.class);

    /**
     * Find Page WebElements
     */
    /**
     * On some product pages, "add to basket" button names are different
     * if any of them is visible, assign it to WebElement
     */

    @FindBy(id = "add-to-cart-button")
    public WebElement addToCartBtn;

    public void defineQuantity(String quantity, String productNumber, String selectValue) {
        try {
            selectDropDownElementByValue(quantity, productNumber, selectValue);
        } catch (Exception e){
            logger.info("WARNING: You can choose only 1 quantity of that product");
        }
    }

    public void navigateToCart() {
        addToCartBtn.click();
    }

    public void savePriceAndQuantity() {
        BrowserUtils.waitFor(2);
        int quantity = 1;
        double price= 0.00;
        try {
            quantity = Integer.parseInt(Driver.get().findElement(By.xpath("//input[@id='sw-total-quantity']")).getAttribute("value").trim());
        } catch (Exception e){
            logger.info("WARNING: You can choose only 1 quantity of that product");
        }

        try {
            price = Double.parseDouble(Driver.get().findElement(By.xpath("//div[@id='sw-subtotal']")).getAttribute("data-price").replace("USD","").trim());
        } catch (Exception e){
            logger.info("WARNING: There is no chosen product or price for that product");
        }

        BrowserUtils.setKeyAndValueWithScenarioNumber("price", price);
        BrowserUtils.setKeyAndValueWithScenarioNumber("quantity", quantity);

    }
}
