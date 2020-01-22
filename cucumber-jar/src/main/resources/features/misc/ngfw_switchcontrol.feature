Feature: Acceptance scenarios for switch control interface for ngfw product

  Scenario Outline: validate BBNMS sends the correct APN values in switch control request for NGFW Order
  Given OMS fixed wireless <orderType> <orderSubType> data order
  And subTransportType attribute on the order has a value <subTransportType>
  When WF invokes the switch control interface
  Then switch control ManageWLLProvisioningRequest sends the following attributes and values:
  | defaultApn | Apn1    | Apn2        | Apn3        |
  | CBRSSAS    | CBRSSAS | WLLDATASILV | WLLDATAV4V6 |
  And serviceTransactionType is <serviceTransactionType> in ManageWLLProvisioningRequest
  And ntiType is <ntiType> ntiValue is <ntiValue> in ManageWLLProvisioningRequest
  
  Examples:
  | orderType | orderSubType | subTransportType | serviceTransactionType | ntiType | ntiValue |
  | PR        | NA           | NextGen          | ACT                    | NTITYPE | NEXTGEN  |
  | PV        | NA           | NextGen          | ACT                    | NTITYPE | NEXTGEN  |
  
  
  Scenario Outline: validate BBNMS sends the correct APN values in switch control req for ngfw cancel order
  Given a ngfw <orderType>-<orderSubType> order succeeded at wll service activation
  When BBNMS accepts a cancel on the <orderType> order
  Then cancel flow gets initiated
  When Deactivation service activation task succeeds in ol
  Then switch control ManageWLLProvisioningRequest sends the following attributes and values:
  | defaultApn | Apn1    | Apn2        | Apn3        |
  | CBRSSAS    | CBRSSAS | WLLDATASILV | WLLDATAV4V6 |
  And serviceTransactionType is DAC in ManageWLLProvisioningRequest
  And ntiType is <ntiType> ntiValue is <ntiValue> in ManageWLLProvisioningRequest
  
  Examples:
  | orderType | orderSubType | serviceTransactionType | ntiType | ntiValue |
  | PR        | NA           | DAC                    | NTITYPE | NEXTGEN  |
  | PV        | NA           | DAC                    | NTITYPE | NEXTGEN  |
  
  
  Scenario Outline: validate BBNMS sends the correct APN values in switch control request for NGFW Order
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When WF invokes the switch control interface
    Then switch control ManageWLLProvisioningRequest sends the following attributes and values:
      | defaultApn | Apn1    | Apn2        | Apn3        |
      | CBRSSAS    | CBRSSAS | WLLDATASILV | WLLDATAV4V6 |
    And serviceTransactionType is <serviceTransactionType> in ManageWLLProvisioningRequest

    Examples: 
      | orderType | orderSubType | subTransportType | serviceTransactionType |
      | CH        | NA           | NextGen          | CCN                    |
      | CH        | AM           | NextGen          | CCN                    |
      | CE        | NA           | NextGen          | DAC                    |
      | CE        | AM           | NextGen          | DAC                    |
