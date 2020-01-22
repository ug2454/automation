@ngfw_oms_1902
Feature: BBNMS-OMS Interface to implement a validation on attribute "subTransportType" under WDataMain component for Fixed Wireless Product orders
  that should reject a service order if the value for subTransportType attribute is not "" or "NextGen".

Background: Initialization steps
Given Perform context cleanup
And Set suite.order.series to 9973

  @sunnyday_oms
  Scenario Outline: valid oms request with subTransportType as "NextGen" or Blank should get accepted in BBNMS.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    When BBNMS receives a <ptRequest> request
    Then BBNMS-OMS interface accepts the request
    And a service order workflow gets initiated in BBNMS
    When BBNMS receives a <cpeRequest> request
    Then BBNMS-OMS interface accepts the request

    Examples: 
      | orderType | orderSubType | subTransportTypeValue | ptRequest | cpeRequest |
      | PR        | NA           | NextGen               | PT        | CPE        |
      | PR        | AM           | NextGen               | PT        | CPE        |
      | PR        | NA           | Blank                 | PT        | CPE        |
      | PR        | AM           | Blank                 | PT        | CPE        |
      | PV        | NA           | NextGen               | PT        | CPE        |

  @rainyday_oms
  Scenario Outline: valid oms request with subTransportType not blank or  "NextGen" should get rejected in BBNMS.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    When BBNMS receives a <ptRequest> request
    Then BBNMS-OMS interface rejects the request

    Examples: 
      | orderType | orderSubType | subTransportTypeValue | ptRequest |
      | PR        | NA           | NextGeneration        | PT        |
      | PR        | AM           | GenNext               | PT        |

  Scenario Outline: valid oms request with subTransportType as "NextGen" should get accepted in BBNMS
    Given a provisioned ngfw subscriber in BBNMS
    And OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    When BBNMS receives a <ptRequest> request
    Then BBNMS-OMS interface accepts the request
    And a service order workflow gets initiated in BBNMS
    When BBNMS receives a <cpeRequest> request
    Then BBNMS-OMS interface accepts the request

    Examples: 
      | orderType | orderSubType | subTransportTypeValue | ptRequest | cpeRequest |
      | CH        | NA           | NextGen               | PT        | CPE        |
