package de.amazon.stepDefinitions;

import de.amazon.pages.BasketPage;
import de.amazon.pages.HomePage;
import de.amazon.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class BasketStepDefs {

    @When("The user navigate to basket page")
    public void the_user_navigate_to_basket_page() {
        new HomePage().basketButton.click();
    }

    @Then("The price and quantity are calculated correctly in Cart")
    public void thePriceAndQuantityAreCalculatedCorrectlyInCart() {
        new BasketPage().assertPriceAndQuantity();
    }

    @When("The user change as {string} the {string} of product {string} in Cart")
    public void the_user_change_as_the_of_product_in_cart(String newQuantity, String selectValue, String productNumber) {
        new ProductPage().defineQuantity(newQuantity, productNumber, selectValue);
    }

    @Then("The total price and the quantity changes correctly")
    public void theTotalPriceAndTheQuantityChangesCorrectly() {
        new BasketPage().assertPriceAndQuantity();
    }
}
