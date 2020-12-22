Feature: The Main Page tests
  
  Background:
   Given Browser is started

  Scenario: Open otus page
    When User opens main page
    Then Title of page is right.
