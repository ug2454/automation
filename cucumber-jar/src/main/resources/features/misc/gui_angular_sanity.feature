Feature: This feature file tests the acceptance scenarios for Angular GUI Sanity

  @Gui
  Scenario: Gui is loading successfully for the environment
    Given the user has access to BBNMS GUI
    When the user navigates to BBNMS GUI
    Then the BBNMS GUI is successfully loaded

  @Gui
  Scenario Outline: Order Search Acceptance Scenarios
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to BBNMS gui order search page
    And searches an order for <productType> product in order search page
    Then search should be successful
    And result should be displayed
    When User clicks on orderlink
    Then order summary page should load successfully
    When user clicks on BAN link
    Then subscription summary page should load successfully

    Examples: 
      | productType |
      | DTV         |
      | Uverse      |
      | WLL         |

  @Gui @Subscription
  Scenario Outline: Subscription Search Acceptance Scenarios
    Given a <productType> customer with pending order was existing
    When user searches the BAN in Gui
    Then subscription summary page should load successfully
    When user clicks on pending order link
    Then order summary page should load successfully

    Examples: 
      | productType |
      | DTV         |
      | Uverse      |
      | WLL         |
