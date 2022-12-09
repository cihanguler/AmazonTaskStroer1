@all
Feature: Verifying search and cart processes

  Background:
    When The user navigate to amazon homepage

  @UnregisteredUser @wip
  Scenario: Verify product search and cart processes
    And The user search the product "hat for men"
    And The user select the first appearing product
    When The user add "2" "quantity" of the selected product "1" to the basket
    And The user navigate to basket page
    Then The price and quantity are calculated correctly in Cart
    When The user change as "1" the "quantity" of product "1" in Cart
    Then The total price and the quantity changes correctly

