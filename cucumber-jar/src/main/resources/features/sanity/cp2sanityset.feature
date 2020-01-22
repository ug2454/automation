Feature: CP2 sanity suite : sanity test for the set% config and automated resolutions in config tab

  @setsanity @Cpgui
  Scenario: planner is trying to add a set% for a default configuration
    Given planner was able to navigate to SET% in config tab
    When planner clicks on add row button
    And fills the required set data
    Then the record will be added successfully
    When planner deletes the added set configuration
    Then configuration is successfully deleted
    
  @autoressanity @Cpgui
  Scenario: planner is trying to add a automated resolution for a default configuration
    Given planner was able to navigate to Automated Resolutions in config tab
    When planner clicks on add row button
    And fills the required automated resolutions data
    Then the record will be added successfully