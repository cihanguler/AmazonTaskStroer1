package de.amazon.stepDefinitions;

import de.amazon.pages.HomePage;
import io.cucumber.java.en.When;

import java.net.MalformedURLException;

public class HomePageStepDefs {

    @When("The user navigate to amazon homepage")
    public void theUserNavigateToAmazonHomepage() throws MalformedURLException {
        new HomePage().navigateToHomePage();
    }
}
