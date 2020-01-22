@tag
Feature: This feature file would be used to test DTV test scenarios of CDOO Driven SMS PID (US_670376)

Background: Initialization steps
Given Perform context cleanup
#And Set up dmaap Kafka adapter
And Set suite.order.series to 5005

 Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI Subscription Search page
    And user searches ban in Subscription Search page
    Then Subscription Summary page should be displayed
    And Service Type should be populated with required value
    Examples: 
      | productType |
      | Uverse      | 