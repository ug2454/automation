Feature: 305709 - CDOO GUI order search page implementation for 1904 release

  @Gui @OrderSearch
  Scenario Outline: US_676521 - BBNMS GUI backend enhancement to display milestone data.
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to BBNMS gui order search page
    And user searches an order for <productType> product in order search page
    Then order search screen will display workflow status and workflow description same as from the workflow microservice
    When user clicks on order number link and navigates to order summary page
    Then order summary screen will display workflow status and description same as from the workflow microservice

    Examples: 
      | productType |
      | DTV         |
      | Uverse      |
      | WLL         |
