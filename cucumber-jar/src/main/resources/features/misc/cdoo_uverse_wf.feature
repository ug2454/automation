Feature: uverse non bvoip cdoo senarios validating nad activation and port change events

Background: Initialization steps    
    Given Perform context cleanup
    And Set up dmaap Kafka adapter
    And Set suite.order.series.uverse to 15646
    
  @sunnyday_publishRG_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And oms Request was accepted
    When valid publish rg notification is accepted
    Then Receive NAD Info task should be successful in OL
    And PONR is succeeded
	  And PTC request was accepted
	  And Order is Purged
    And order listeners should be notified for the following events:    
      | event_name     | event_status |
      | NAD_ACTIVATION | COMPLETE     |


    Examples: 
      | ordertype | subtype | NTI       |
      | PR        | NA      | FTTN      |
      #| PR        | NA      | FTTP-GPON |

  @rainyday_publishRG_PR
  Scenario Outline: Rainy Day, uVerse Copper Order with no BVOIP
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And oms Request was accepted
    When invalid publish rg notification is accepted
    Then Receive NAD Info task should fail in ol
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | ERROR        |

    Examples: 
      | ordertype | subtype | NTI  |
      | PR        | NA      | FTTN |
      | PR        | NA      | FTTP-GPON |

  @sunnyday_publishRG_CH
  Scenario Outline: Sunny Day, uVerse Copper CH NAD SWAP Order with no BVOIP   
    Given Purge a non BVOIP Uverse PR <NTI> order
    And a non BVOIP Uverse <ordertype> <subtype> <NTI> order 
    And Order is set up as Nad Swap
    And oms Request was accepted
    When CPE request was accepted 
    When valid publish rg notification for CH order is accepted
    Then Receive NAD Info task should be successful in OL
    And PONR is succeeded
	  And PTC request was accepted
	  And Order is Purged
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | COMPLETE    |

    Examples: 
      | ordertype | subtype | NTI       |
      | CH        | NA      | FTTN      |
      | CH        | NA      | FTTP-GPON |

  @rainyday_publishRG_CH
  Scenario Outline: Rainy Day, uVerse CH NA Copper Order with no BVOIP
    Given Purge a non BVOIP Uverse PR <NTI> order
    And a non BVOIP Uverse <ordertype> <subtype> <NTI> order 
    And Order is set up as Nad Swap
    And oms Request was accepted
    When CPE request was accepted 
    When invalid publish rg notification is accepted
    Then Receive NAD Info task should fail in ol
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | ERROR       |

    Examples: 
      | ordertype | subtype | NTI       |
      | CH        | NA      | FTTN      |
      
      @sunnyday_queryforNadInfo_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And appointment Date was in past date
    And the PT Request was accepted
    When E/C/S Activation is successful
    Then CMS DB with valid NAD Info is updated
    When CPE request was accepted 
    Then Initiate Handle SDP Provisioning Request task should be successful in OL
    And Query for NAD Info task should be successful in OL
    And PONR is succeeded
	  And PTC request was accepted
	  And Order is Purged
    And order listeners should be notified for the following events:
    | event_name     | event_status |
     | NAD_ACTIVATION | COMPLETE       |
     
    Examples: 
      | ordertype | subtype | NTI    |
      | PR        | NA           | FTTN   |
     # | PR        | NA     			 | FTTP-GPON |
      

  @rainyday_queryfornadinfo_PR
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And appointment Date was in past date
    And the PT Request was accepted
    When E/C/S Activation is successful
    Then CMS DB with invalid NAD Info is updated
    When CPE request was accepted 
    Then Initiate Handle SDP Provisioning Request task should be successful in OL
    And Query for NAD Info task should fail in ol
    And order listeners should be notified for the following events:
      | event_name     | event_status |
      | NAD_ACTIVATION | ERROR        |

    Examples: 
      | ordertype | subtype | NTI       |
      | PR        | NA      | FTTN      |
      | PR        | NA      | FTTP-GPON |



  @sunnyday_queryfornadinfo_CH
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given Purge a non BVOIP Uverse PR <NTI> order
    And a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And appointment Date was in past date
    And Order is set up as Nad Swap 
    And the PT Request was accepted
    When CPE request was accepted 
    Then CMS DB with valid NAD Info is updated   
    And Query for NAD Info task should be successful in OL
    And PONR is succeeded
	  And PTC request was accepted
	  And Order is Purged
    And order listeners should be notified for the following events:
   	 | event_name     | event_status |
     | NAD_ACTIVATION | COMPLETE       |

    Examples: 
      | ordertype | subtype | NTI       |
      | CH        | NA      | FTTN      |
      #| CH        | NA      | FTTP-GPON      |


  @rainyday_queryfornadinfo_CH
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given Purge a non BVOIP Uverse PR <NTI> order
    And a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And appointment Date was in past date
    And Order is set up as Nad Swap
    And installType is tech for the order
    And the PT Request was accepted
    When CPE request was accepted 
    When E/C/S Activation is successful
    Then CMS DB with invalid NAD Info is updated    
    Then Query for NAD Info task should fail in ol
    And order listeners should be notified for the following events:
   	 | event_name     | event_status |
     | NAD_ACTIVATION | ERROR       |

    Examples: 
      | ordertype | subtype | NTI       |
      | CH        | NA      | FTTN      |

@PR_Provision
  Scenario Outline: Sunny Day, uVerse Copper Order with no BVOIP
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And oms Request was accepted
    When valid publish rg notification is accepted
    And Initiate SDP is succeeded
    Then Initiate Handle SDP Provisioning Request task should be successful in OL
    And Send Order Level PONR task should be successful in OL
    And PTC request was accepted
    And Order is Purged
    Examples: 
      | ordertype | subtype | NTI      |
      | PR        | NA      | FTTN     |
      
      
 