package de.amazon.pages;

import de.amazon.utilities.ConfigurationReader;
import de.amazon.utilities.Driver;


public class HomePage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */

    public void navigateToHomePage() {
        String url = ConfigurationReader.get("url");
        Driver.get().navigate().to(url);
    }



}
