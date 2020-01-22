@ngfw_oms_1902
Feature: BBNMS-OMS Interface to implement a validation on attribute "subTransportType" under WDataMain component for Fixed Wireless Product orders
  that should reject a service order if the value for subTransportType attribute is not "" or "NextGen".

Background: Initialization steps
Given Set suite.order.series to 9973

 
  Scenario Outline: BBNMS-OMS Interface shall reject CH, SU, RS CE, CM (NA, AM) orders where subTransportType value is different than that on Subscription currently.
    Given OMS fixed wireless provide data order exits in BBNMS
    And OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    When BBNMS receives a wll <ptRequest> request
    Then BBNMS-OMS interface rejects the request

   #And reject reason is <reason>
    Examples: 
      | orderType | orderSubType | subTransportTypeValue | ptRequest | reason |
      | CH        | NA           | NextGen               | PT        |        |
      | CH        | AM           | NextGen               | PT        |        |
      | CE        | NA           | NextGen               | PT        |        |
      | CE        | AM           | NextGen               | PT        |        |
      | SU        | NA           | NextGen               | PT        |        |
      | SU        | AM           | NextGen               | PT        |        |
      | RS        | NA           | NextGen               | PT        |        |
      | RS        | AM           | NextGen               | PT        |        |

   
   
    @rainyday_oms
 Scenario Outline: valid oms request with subTransportType NOT as "NextGen" or Blank should get rejected in BBNMS.
 
	 Given OMS fixed wireless provide data order exits in BBNMS
	 And OMS fixed wireless <orderType> <orderSubType> data order
	 And subTransportType attribute on the order has a value <subTransportTypeValue>
	 When BBNMS receives a wll <ptRequest> request
	 Then BBNMS-OMS interface rejects the request
	 
	 Examples:
	 | orderType | orderSubType | subTransportTypeValue | ptRequest |
	 | CH        | NA           | GEN                   | PT        |
	 | CH        | AM           | TGEN                  | PT        |
	 | CE        | NA           | GEN                   | PT        |
	 | CE        | AM           | GEN                   | PT        |
	 | SU        | NA           | TGEN                  | PT        |
	 | SU        | AM           | TGEN                  | PT        |
	 | RS        | NA           | TGEN                  | PT        |
	 | RS        | AM           | TGEN                  | PT        |
	 | PV        | NA           | TGEN                  | PT        |
