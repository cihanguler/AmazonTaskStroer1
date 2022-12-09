package de.amazon.pages;

import de.amazon.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class SearchPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */

    @FindBy(xpath = "//div[@cel_widget_id='MAIN-SEARCH_RESULTS-1']/div/div/div/div/div/div/h2/a")
    public List <WebElement> firstSearchedProductLink;

    public SearchPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    public void selectFirstProduct() {
        String url = firstSearchedProductLink.get(0).getAttribute("href");
        Driver.get().navigate().to(url);
    }

}
