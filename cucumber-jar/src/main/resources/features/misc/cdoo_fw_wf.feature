#Author: your.email@your.domain.com
Feature: CDOO Send WLL (FWI & NGFW) Related Events from BBNMS to DLE. US-670381

  Background: Initialization steps
    Given Perform context cleanup
    And Set up dmaap Kafka adapter
	
	@tagfw
  Scenario Outline: For FWI/NGFW order till ponr, all events should get generated
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order was <subTransportTypeValue>
    When BBNMS accepts wll oms order
    And OWA installation succeeds in ol
    And Service Activation succeeds in ol
    And BBNMS accepts a valid wll publish rg notification
    And OWA closeout succeeds in ol
    And Send Order Level PONR task should be successful in OL
    And BBNMS accepts PTC request
    And Purge order task should be successful in OL
    Then order listeners should be notified for the following events:
      | event_name              | event_status |
      | INSTALLATION_INITIATION | STARTED      |
      | INSTALLATION_INITIATION | COMPLETE     |
      | SERVICE_ACTIVATION      | STARTED      |
      | SERVICE_ACTIVATION      | COMPLETE     |
      | NAD_ACTIVATION          | STARTED      |
      | NAD_ACTIVATION          | COMPLETE     |
      | WORK_ORDER_COMPLETION   | COMPLETE     |

    Examples: 
      | orderType | orderSubType | subTransportTypeValue |
      | PR        | NA           | Blank                 |
      | PV        | NA           | Blank                 |
      | PR        | NA           | NextGen               |
      | PV        | NA           | NextGen               |
      
  
  @FwRainy
  Scenario Outline: For FWI/NGFW order owa installation and nad activation wait tasks fail, corresponding event should get generated
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order was <subTransportTypeValue>
    And appointment Date was in past date
    When BBNMS accepts wll oms order
    And OWA installation fails in ol

    #   And BBNMS accepts an invalid wll publish rg notification
    #  Then order listeners are notified for the following events:
    #   | event_name              | event_status |
    #   | INSTALLATION_INITIATION | FAILED       |
    #   | NAD_ACTIVATION          | FAILED       |
    Examples: 
      | orderType | orderSubType | subTransportTypeValue |
      | PR        | NA           | Blank                 |
      | PR        | NA           | NextGen               |

  #  | PV        | NA           | Blank                 |
  #  | PV        | NA           | NextGen               |
  
  @tagscfail
  Scenario Outline: For FWI / NGFW order service activation fail, corresponding event should get generated.
    Given OMS fixed wireless <orderType> <orderSubType> data order
    And subTransportType attribute on the order was <subTransportTypeValue>
    When BBNMS accepts wll oms order
    And OWA installation succeeds in ol
    And Service Activation fails in ol
    Then order listeners should be notified for the following events:
      | event_name              | event_status |
      | INSTALLATION_INITIATION | STARTED      |
      | INSTALLATION_INITIATION | COMPLETE     |
      | SERVICE_ACTIVATION      | STARTED      |
      | SERVICE_ACTIVATION      | ERROR        |

    Examples: 
      | orderType | orderSubType | subTransportTypeValue |
      | PR        | NA           | Blank                 |
      | PR        | NA           | NextGen               |
    #  | PV        | NA           | Blank                 |
    #  | PV        | NA           | NextGen               |
