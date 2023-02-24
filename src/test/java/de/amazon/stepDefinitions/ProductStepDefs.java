package de.amazon.stepDefinitions;

import de.amazon.pages.ProductPage;
import io.cucumber.java.en.When;

public class ProductStepDefs {

    @When("The user add {string} {string} of the selected product {string} to the basket")
    public void theUserAddOfTheSelectedProductToTheBasket(String newQuantity, String selectValue, String productNumber) {
        new ProductPage().defineQuantity(newQuantity, productNumber, selectValue);
        new ProductPage().navigateToCart();
        new ProductPage().savePriceAndQuantity();
    }

}
