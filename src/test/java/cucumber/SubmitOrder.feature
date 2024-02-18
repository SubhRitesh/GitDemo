
@tag
Feature: Purchase the order from ecommerce website
  I want to use this template for my feature file

	Background:
	Given I landed on Ecommerce page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <email> and password <password>
    When I add product <productName> to the Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on Confirmation Page

    Examples: 
      | email  							| password | productName			|
      | blackjack@gmail.com | Test!234 | ZARA COAT 3			|
      
