package de.amazon.stepDefinitions;

import de.amazon.pages.HomePage;
import io.cucumber.java.en.When;

public class HomePageStepDefs {

    @When("The user navigate to amazon homepage")
    public void theUserNavigateToAmazonHomepage() {
        new HomePage().navigateToHomePage();
    }
}
