Feature: CP2 sanity suite : sanity test for the set% config and automated resolutions in config tab

  @setsanity @Cpgui
  Scenario: planner is trying to add a set% for a default configuration
    Given planner was able to navigate to SET% in config tab
    When planner clicks on add row button
    And fills the required set data
    Then i want to validate this
    