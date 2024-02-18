
@tag
Feature: Error Validation
  I want to use this template for my feature file

  @ErrorValidation
  Scenario Outline: Title of your scenario outline
    Given I landed on Ecommerce page
    Given Logged in with username <email> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples: 
      | email  							| password | productName			|
      | blackjack@gmail.com | Test3424 | ZARA COAT 3			|
