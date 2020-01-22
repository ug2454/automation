Feature: 303332a next gen fixed wireless gui scenarios

@gui
  Scenario Outline: US_664849 -WLL Service Order Summary shall display subTransportType attribute.
   Given wll orders with sub transport type as <subTransportTypeValue> present in the system
    Given a service-provisioned wll subscriber exist in BBNMS
   When user navigates to BBNMS gui order search page
    And user selects wll from service order search dropdown
    And user selects nti as none and subtransportType value as <subTransportTypeValue>
    Then WLL service order summary page opens
    And quit driver 
    
    Examples:
  | subTransportTypeValue |
  | NextGen               |

  
  @gui
  Scenario Outline: US_664849 -WLL Service Order Summary shall display subTransportType attribute.
    Given wll orders with sub transport type as <subTransportTypeValue> present in the system
    When user navigates to BBNMS order search page
    And user selects WLL from service order search dropdown
    And user selects nti as <nti> and subtransportType value as <subTransportType>
    And user clicks on search button
    Then Wll order search gui shall display a list of order
    When user clicks on any wll data order number
    Then WLL service order summary page opens
    And subtransportType value is <subTransportTypeValue>

    Examples: 
      | subTransportType | nti  | subTransportTypeValue |
      | NextGen          | none | NextGen               |
      | NA               | none | Blank                 |

  
  @gui
  Scenario Outline: US_664850 - WLL subscription summary GUI to display Default APN value value.
  Given a provisioned wll subscription with sub transport type as <subTransportTypeValue>
  When user navigates to BBNMS gui subscription search page
  And user enters the ban and click on search
  Then WLL subscription summary gui shall display subtransportType value as <subTransportTypeValue>
  And user clicks on Product Details tab
  And Default APN value is CBRSSAS    
  
  
  Examples:
  | subTransportTypeValue |
  | NextGen               |
  | Blank                 |
  
  
  @gui
  Scenario Outline: US_664849 -WLL Service Order Summary shall display failed orders and allow Batch resubmission.
    Given wll orders with sub transport type as <subTransportTypeValue> present in the system
    When user navigates to BBNMS order search page
    And user selects WLL from service order search dropdown
    And user selects nti as <nti> , subtransportType value as <subTransportType> and order status as Failed
    And user clicks on search button
    Then Wll order search gui shall display a list of order
    When user clicks on any wll data order number
    Then WLL service order summary page opens
    And user can resubmit the Failed order

    Examples: 
      | subTransportType | nti  | subTransportTypeValue |
      | NextGen          | none | NextGen               |
      | NA               | none | Blank                 |
  
  
  
  @gui
  Scenario Outline: US_664849 -WLL -NGFW Service Order Summary between specified date intervals.
    Given wll orders with sub transport type as NextGen present in the system
    When user navigates to BBNMS order search page
    And user selects WLL from service order search dropdown
    And user selects nti as none and subtransportType value as NextGen
    And user gives Start date as <startDate> and End date as Current_Date
    And user clicks on search button
    Then WLL service order summary page opens
  
  
   Examples: 
      
  | startDate |
  | 04/15/2019|
  | 04/16/2019|
  | 04/17/2019|
  
  
  
   @gui
  Scenario Outline: US_664849 -WLL - FW Service Order Summary between specified date intervals.
    Given wll orders with sub transport type as NA present in the system
    When user navigates to BBNMS order search page
    And user selects WLL from service order search dropdown
    And user selects nti as none and subtransportType value as Blank
    And user gives Start date as <startDate> and End date as Current_Date
    And user clicks on search button
    Then WLL service order summary page opens
  
  
   Examples: 
      
  | startDate |
  | 04/15/2019|
  | 04/16/2019|
  | 04/17/2019|
  
 