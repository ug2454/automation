Feature: FBS product related scenarios for CDOO project

  Background: Initialization steps
    Given Perform context cleanup
    And Set up dmaap Kafka adapter
    And Set suite.order.series.uverse to 15646

  @sunnyday_publishRG_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a FBS <orderType> <orderSubType> <NTI> order
    When the PT Request was accepted
    When BBNMS updates cim queries
    And CPE request was accepted
    And valid publish rg notification is accepted
    Then Receive NAD Info task should be successful in OL
   Then CANOPI Activation task should be successful in OL
   #And Send Order Level PONR task should be successful in OL
    #And PTC request was accepted

    And order listeners should be notified for the following events:
    | event_name     | event_status |
     | NAD_ACTIVATION | COMPLETE     |
    Examples: 
      | orderType | orderSubType | NTI    |
      | PR        | NA           | FTTB-C |
      | PR        | NA           | FTTB-F |

  @rainyday_publishRG_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a FBS <orderType> <orderSubType> <NTI> order
    When the PT Request was accepted
    #When BBNMS updates cim queries
    And invalid publish rg notification is accepted
    #And Send Order Level PONR task should be successful in OL
    #And PTC request was accepted
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | ERROR       |

    Examples: 
      | orderType | orderSubType | NTI    |
      | PR        | NA           | FTTB-C |
      | PR        | NA           | FTTB-F |

  @sunnyday_publishRG_CH
  Scenario Outline: Sunny Day, uVerse Copper CH NAD SWAP Order with no BVOIP
    Given a FBS uverse subscriber with no pending order is provisioned in BBNMS
    And a FBS <ordertype> <subtype> <NTI> order
    When the PT Request was accepted
     #When BBNMS updates cim queries
    #And CPE request was accepted
    And valid publish rg notification is accepted
    Then Receive NAD Info task should be successful in OL
    And order listeners should be notified for the following events:
     | event_name     | event_status |
     | NAD_ACTIVATION | COMPLETE     |
    Examples: 
      | ordertype | subtype | NTI    |
     | CH        | NA      | FTTB-C |
      | CH        | NA      | FTTB-F |

  @rainyday_publishRG_CH
  Scenario Outline: Sunny Day, uVerse Copper CH NAD SWAP Order with no BVOIP
    Given a FBS uverse subscriber with no pending order is provisioned in BBNMS
    And a FBS <ordertype> <subtype> <NTI> order
    When the PT Request was accepted
   # When BBNMS updates cim queries
    #And CPE request was accepted
    And invalid publish rg notification is accepted
    And Receive NAD Info task should fail in ol
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | ERROR        |

    Examples: 
      | ordertype | subtype | NTI    |
      | CH        | NA      | FTTB-C |
      | CH        | NA      | FTTB-F |

  @sunnyday_queryfornadinfo_CH
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a FBS uverse subscriber with no pending order is provisioned in BBNMS
    And a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    When the PT Request was accepted
    When BBNMS updates cim queries
    When CMS DB with valid NAD Info is updated
    And CPE request was accepted
    And Query for NAD Info task should be successful in OL
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | COMPLETE     |

    Examples: 
      | ordertype | subtype | NTI    |
      | CH        | NA      | FTTB-C |
      
      @sunnyday_queryfornadinfo_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP    
    Given a FBS <ordertype> <subtype> <NTI> order
    When the PT Request was accepted
    When BBNMS updates cim queries
    When CMS DB with valid NAD Info is updated
    And CPE request was accepted
    And Query for NAD Info task should be successful in OL
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | COMPLETE     |

    Examples: 
      | ordertype | subtype | NTI    |
      | PR        | NA      | FTTB-C |
      
      @rainyday_queryfornadinfo_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP    
    Given a FBS <ordertype> <subtype> <NTI> order
    When the PT Request was accepted
    When BBNMS updates cim queries
    Then CMS DB with invalid NAD Info is updated
    And CPE request was accepted
    And Query for NAD Info task should be successful in OL
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | ERROR     |

    Examples: 
      | ordertype | subtype | NTI    |
      | PR        | NA      | FTTB-C |
