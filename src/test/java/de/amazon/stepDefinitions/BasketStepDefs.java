package de.amazon.stepDefinitions;

import de.amazon.pages.BasketPage;
import de.amazon.pages.HomePage;
import de.amazon.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.MalformedURLException;


public class BasketStepDefs {

    @When("The user navigate to basket page")
    public void the_user_navigate_to_basket_page() throws MalformedURLException {
        new HomePage().basketButton.click();
    }

    @Then("The price and quantity are calculated correctly in Cart")
    public void thePriceAndQuantityAreCalculatedCorrectlyInCart() throws MalformedURLException {
        new BasketPage().assertPriceAndQuantity();
    }

    @When("The user change as {string} the {string} of product {string} in Cart")
    public void the_user_change_as_the_of_product_in_cart(String newQuantity, String selectValue, String productNumber) throws MalformedURLException {
        new ProductPage().defineQuantity(newQuantity, productNumber, selectValue);
    }

    @Then("The total price and the quantity changes correctly")
    public void theTotalPriceAndTheQuantityChangesCorrectly() throws MalformedURLException {
        new BasketPage().assertPriceAndQuantity();
    }

    @Then("assertions for the price and quantity are successful in Cart")
    public void assertions_for_the_price_and_quantity_are_successful_in_cart() throws MalformedURLException {
        new BasketPage().assertPriceAndQuantityWithSessionStateHandler();
    }
}

