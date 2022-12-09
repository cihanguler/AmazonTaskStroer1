package de.amazon.stepDefinitions;

import de.amazon.pages.HomePage;
import de.amazon.pages.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class SearchStepDefs {

    @When("The user search the product {string}")
    public void the_user_search_the_product(String item) {
        HomePage homepage = new HomePage();
        homepage.searchItem(item);
    }

    @And("The user select the first appearing product")
    public void theUserSelectTheFirstAppearingProduct() {
        new SearchPage().selectFirstProduct();
    }
}
