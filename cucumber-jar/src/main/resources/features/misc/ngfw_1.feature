Feature: remaining NGFW scenarios.

  @preeti
  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with omsOrderNumber and circuitId
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | Blank            |
      | PR        | NA           | NextGen          |
      | PV        | NA           | Blank            |
      | PV        | NA           | NextGen          |

  @preeti
  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with Ban and circuitId
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | Blank            |
      | PR        | NA           | NextGen          |
      | PV        | NA           | Blank            |
      | PV        | NA           | NextGen          |

  @preeti
  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given a provisioned ngfw subscriber in BBNMS
    And OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with Ban and circuitId
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | orderType | orderSubType | subTransportType |
      | CH        | NA           | Blank            |
      | CH        | NA           | NextGen          |
      | SU        | NA           | Blank            |
      | SU        | NA           | NextGen          |

  @Preeti
  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given a suspend subscriber exists in BBNMS
    And OMS fixed wireless RS NA data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with omsOrderNumber and circuitId
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | subTransportType |
      | Blank            |
      | NextGen          |

  @Amit
  Scenario: US705044 R1904: BBNMS-LS 303332a (NGFW) - WORKFLOW: Support Query to GEARS on OWA Swap Flow
    Given a provisioned ngfw subscriber in BBNMS
    When BBNMS accepts an OWA Swap request from ISAAC
    Then owa swap order is initiated in BBNMS
    When Cell Site Assignment Update Activity task succeeds in ol
    Then workflow will execute the new task Query GEARS for eGCI task

  @Amit
  Scenario: US703123 BBNMS-LS Service Maintenance For any unsolicited FW/NGFW orders (NAD swap) correct NTI subtype for the BAN is present on the order versions.
    Given a provisioned ngfw subscriber in BBNMS
    When BBNMS accepts a Nad Swap request from GCAS
    Then Nad Swap flow is initiated in BBNMS
    And subtransporttype in wllversion for the nad swap order is NextGen

  @Gaurangi
  Scenario: US703123 BBNMS-LS Service Maintenance For any unsolicited FW/NGFW orders (NAD swap) correct NTI subtype for the BAN is present on the order versions.
    Given a provisioned fw subscriber in BBNMS
    When BBNMS accepts a Nad Swap request from GCAS
    Then Nad Swap flow is initiated in BBNMS
    And subtransporttype in wllversion for the nad swap order is Blank

  @Gaurangi
  Scenario: US700289 R1904 303332a BBNMS-LS Service Maintenance For any unsolicited FW/NGFW orders (OWA swap) correct NTI subtype and Cell site Info attributes for the BAN is present on the order versions.
    Given a provisioned fw subscriber in BBNMS
    When BBNMS accepts an Owa Swap request from ISAAC
    Then owa swap order is initiated in BBNMS
    And subtransporttype in wllversion for the owa swap order is NextGen
