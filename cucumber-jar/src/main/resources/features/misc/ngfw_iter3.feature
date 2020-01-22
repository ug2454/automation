Feature: NGFW Iter3 user stories

Background: Initialization steps
Given Perform context cleanup

	 Scenario: US700245 - Verify that wllproductvalidationinfo contains correct make and model entries for Fixed Wireless customer
	    Given ol db is up and running
	    When wllproductvalidationinfo table is queried for make and model with nti as WLLLTE and subtransporttype is null
	    Then make is NETCOMM
	    And model is OWAR0-35
	    
	  Scenario: US700245 - Verify that wllproductvalidationinfo contains correct make and model entries for Nextgen Fixed Wireless customer
	    Given ol db is up and running
	    When wllproductvalidationinfo table is queried for make and model with nti as WLLLTE and subtransporttype is NextGen
	    Then make is NETCOMM
	    And model is OWAR1-35

  Scenario Outline: US669718 - Add validations on the CSI-In interface for OWA activations for Pending Orders for FW and NGFW service
    Given OMS fixed wireless PR NA data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    And NGFW_CSI_IN_WLL_VALIDATION setting is set to true in ol
    When BBNMS accepts wll oms order
    And ManageWLLInstallationDetails request does not contains the following fields:
      | fields |
      | make   |
      | model  |
    And BBNMS receives ManageWLLInstallationDetails Process request
    Then CSIIN interface rejects the request with reason:
      | reason                                                                            |
      | Invalid Input. Required field is missing :  Imei/SimIccid/Make/Model/SerialNumber |

    Examples: 
      | subTransportTypeValue |
      | Blank                 |
      | NextGen               |

  Scenario Outline: US669718 - Add validations on the CSI-In interface for OWA activations - sunnyday
    Given OMS fixed wireless PR NA data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    And NGFW_CSI_IN_WLL_VALIDATION setting is set to true in ol
    When BBNMS accepts wll oms order
    And ManageWLLInstallationDetails request does not contains the following fields:
      | fields |
    And Process request has attribute make as <make>
    And Process request has attribute model as <model>
    And BBNMS receives ManageWLLInstallationDetails Process request
    Then CSIIN interface accepts the request
    And Receive OWA Installation task successful in OL

    Examples: 
      | subTransportTypeValue | make    | model    |
      | Blank                 | NETCOMM | OWAR0-35 |
      | NextGen               | NETCOMM | OWAR1-35 |

  Scenario Outline: US669718 - Add validations on the CSI-In interface for OWA activations - Negative scenario for 2 back to back Process requests
    Given OMS fixed wireless PR NA data order
    And subTransportType attribute on the order has a value <subTransportTypeValue>
    And NGFW_CSI_IN_WLL_VALIDATION setting is set to true in ol
    When BBNMS accepts wll oms order
    And ManageWLLInstallationDetails request does not contains the following fields:
      | fields |
    And Process request has attribute make as <make>
    And Process request has attribute model as <model>
    And BBNMS receives ManageWLLInstallationDetails Process request
    Then CSIIN interface accepts the request
    And Receive OWA Installation task successful in OL
    When Process request has a new imei value
    And BBNMS receives ManageWLLInstallationDetails Process request
    Then CSIIN interface rejects the request with reason:
      | reason                                                                        |
      | Activation request is already received. Only Swap request can be accepted now |

    Examples: 
      | subTransportTypeValue | make    | model    |
      | Blank                 | NETCOMM | OWAR0-35 |
      | NextGen               | NETCOMM | OWAR1-35 |

  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with omsOrderNumber
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | Blank            |
      | PR        | NA           | NextGen          |
      | PV        | NA           | Blank            |
      | PV        | NA           | NextGen          |

  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with Ban
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | orderType | orderSubType | subTransportType |
      | PR        | NA           | Blank            |
      | PR        | NA           | NextGen          |
      | PV        | NA           | Blank            |
      | PV        | NA           | NextGen          |

  Scenario Outline: US705837: For NGFW order enhance FIRST-RT retrieveProvisioningStatusInformation API to support NTI SubTransportType.
    Given a provisioned ngfw subscriber in BBNMS
    And OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order has a value <subTransportType>
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with Ban
    Then BBNMS sends <subTransportType> on retrieveProvisioningStatusInformation response

    Examples: 
      | orderType | orderSubType | subTransportType |
      | CH        | NA           | NextGen          |
      | SU        | NA           | NextGen          |

  
 
 
