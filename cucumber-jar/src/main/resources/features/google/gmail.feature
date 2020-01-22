Feature: Browser Automation

  Scenario Outline: Enter username, password and hit enter
    #Given User has logged into gmail
    #When User entered username and password and hit login
    #Then Gmail page should be opened
    Given user has opened make my trip
    When user clicked on departure date
    Then user selects a random date of <month> <date>

    Examples: 
      | month | date |
      | Jan   |   19 |
