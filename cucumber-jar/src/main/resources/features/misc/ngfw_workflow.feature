Feature: Contains test only scenarios for suspend resume orders for ngfw.New product line for NGFW generated from AHL gets propagated
  to CSI WF.It also validates sub transport type value gets propagated to csi wf and first interface.

  Background: Initialization steps
    Given Perform context cleanup
    And Set suite.order.series to 9973

  Scenario: US 671568 NextGen Suspend order is received, the existing flow should run and the customer's service should successfully be suspended.
    Given a provisioned ngfw subscriber in BBNMS
    And OMS fixed wireless SU NA data order
    And subTransportType attribute on the order has a value NextGen
    When BBNMS accepts the suspend order
    Then suspend order successfully gets completed in BBNMS

  Scenario: US 671568 NextGen Resume order is received, the existing flow should run and the customer's service should successfully be restored.
    Given a suspend subscriber exists in BBNMS
    And OMS fixed wireless RS NA data order
    And subTransportType attribute on the order has a value NextGen
    When BBNMS accepts the resume order
    Then resume order successfully gets completed in BBNMS

  Scenario Outline: US 664738 - New WOLI for NGFW Orders
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    And dispatch is specified on the order
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS receives a wll CPE request
    And AHL runs and generates the appropriate WOLI for the NGFW service
    And CSI-WF interface creates the dispatch ticket
    Then WOLI <productLine> shall be sent on the message to CSI-WF

    Examples: 
      | orderType | orderSubType | subTransportType | productLine          |
      | PR        | NA           | NextGen          | OUTDOOR ANTENNA NGFW |
      | PR        | AM           | NextGen          | OUTDOOR ANTENNA NGFW |

  Scenario Outline: US664728 - BBNMS-LS CSI- WF (EDGE) - Support new Transport Subtype on the CSI-WF interface  (TEST ONLY)
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    And dispatch is specified on the order
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS receives a wll CPE request
    And AHL runs and generates the appropriate WOLI for the NGFW service
    And CSI-WF interface creates the dispatch ticket
    Then NTI <subTransportType> shall be sent on the message to CSI-WF

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | NextGen          |
      | PR        | AM           | NextGen          |

  Scenario Outline: US 664738 - New WOLI for NGFW Orders
    Given a provisioned ngfw subscriber in BBNMS
    And OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    And dispatch is specified on the order
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS receives a wll CPE request
    And AHL runs and generates the appropriate WOLI for the NGFW service
    And CSI-WF interface creates the dispatch ticket
    Then WOLI <productLine> shall be sent on the message to CSI-WF

    Examples: 
      | orderType | orderSubType | subTransportType | productLine          |
      | CH        | NA           | NextGen          | OUTDOOR ANTENNA NGFW |
      | CH        | AM           | NextGen          | OUTDOOR ANTENNA NGFW |

  Scenario Outline: US-62299 For NGFW order the fallout message shall include NTI SubTransportType
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When order fails in BBNMS
    Then BBNMS sends <subTransportType> on notifyFIRSTfalloutProvision exception request to first

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | NextGen          |
      | PR        | NA           | Blank            |
      | PR        | AM           | NextGen          |

  Scenario Outline: US658293 gNetworkType in the provisionWLLRequest formatted as NTI/NTIsubtype "WLL-LTE/NextGen"
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When Create Account in G2 task successful in OL
    Then gNetworkType value is <gNetworkType> in provisionWLLRequest

    Examples: 
      | orderType | orderSubType | subTransportType | gNetworkType    |
      | PR        | NA           | NextGen          | WLL-LTE/NextGen |
      | PR        | NA           | Blank            | WLL-LTE         |
      | PV        | NA           | NextGen          | WLL-LTE/NextGen |
      | PV        | NA           | Blank            | WLL-LTE         |

  Scenario Outline: US664757 WORKFLOW -- Change to FWI (WLL) PR and PV flows regarding Query OWA data
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS receives a wll CPE request
    Then Receive Exchange CPE Data 1st Call - CPE Data task successful in OL
    When OWA installation succeeds in ol
    And Service Activation succeeds in ol
    And BBNMS accepts a valid wll publish rg notification
    And BBNMS receives a closeout from ISAAC
    Then Send FW CPE Info to IOMIP task successful in OL
    When BBNMS receives a wll PTC request
    Then Hold for Delay Timer task successful in OL
    And Query OWA Data task successful in OL

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | NextGen          |
      | PR        | NA           | Blank            |
      | PV        | NA           | NextGen          |
      | PV        | NA           | Blank            |

  Scenario: US 693189 - For NGFW orders enhance EDW interface to support NTI SubTransportType
    Given a provisioned ngfw subscriber in BBNMS
    When EDW batch file is searched for subTransportType
    Then NextGen is found in EDW batch file
