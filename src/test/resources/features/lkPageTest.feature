Feature: The LK page (personal account) test

  Background:
    Given Browser is started

    Scenario: Fill personal data
      Given User opens main page
      And fills personal credential data
      And signs in personal account
      When user fill personal data
      And user click button 'Save and continue'
      And close browser
      And User open new browser
      And User opens main page
      And fills personal credential data
      And signs in personal account
      Then filled personal data is correct
