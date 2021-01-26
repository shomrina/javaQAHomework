Feature: The LK page (personal account) test

  Background:
    Given Browser is started

    Scenario: Fill personal data
      Given User opens main page
      And fills personal credential data: "milagrous@gmail.com", "fJ!ntyy2wRg9Fdh"
      And signs in personal account
      When user fill personal data: "Марина", "Marina", "Клипперт", "Klippert", "06.06.1987"
      And user click button 'Save and continue'
      And close browser
      And User open new browser
      And User opens main page
      And fills personal credential data: "milagrous@gmail.com", "fJ!ntyy2wRg9Fdh"
      And signs in personal account
      Then filled personal data is correct: "Марина", "Marina", "Клипперт", "Klippert", "06.06.1987"
