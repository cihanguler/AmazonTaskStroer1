package de.amazon.pages;

import de.amazon.utilities.ConfigurationReader;
import de.amazon.utilities.Driver;

import java.net.MalformedURLException;


public class HomePage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public HomePage() throws MalformedURLException {
    }

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */

    public void navigateToHomePage() throws MalformedURLException {
        String url = ConfigurationReader.get("url");
        Driver.get().navigate().to(url);
    }



}
