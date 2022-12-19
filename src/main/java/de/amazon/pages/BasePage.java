package de.amazon.pages;

import de.amazon.utilities.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public BasePage() {
        PageFactory.initElements(Driver.get(), this);
    }

    /**
     * create logger to log infos, errors etc.
     */
    Logger logger = LoggerFactory.getLogger(BasePage.class);

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(id = "nav-cart")
    public WebElement basketButton;

    /**
     * type a product name to search bar and search it
     *
     * @param item
     */
    public void searchItem(String item) {
        searchBox.clear();
        searchBox.sendKeys(item, Keys.ENTER);
    }

}
