@tag
Feature: This feature file would be used to test WLL Sanity Suite

Background: Initialization steps
    Given Perform context cleanup
    And Set suite.order.series to 9973
    
Scenario: WORKFLOW - NGFW Sanity Suite - PR
    Given OMS fixed wireless PR NA data order
    And subTransportType attribute on the order was NextGen
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    When BBNMS receives a wll CPE request
    When BBNMS accepts wll oms order
    When OWA installation succeeds in ol
    And Service Activation succeeds in ol
    And BBNMS accepts a valid wll publish rg notification
    And BBNMS receives a closeout from ISAAC
    When BBNMS receives a wll PTC request
    Then Hold for Delay Timer task is in_progress in OL
    And Update Hold for Delay Timer
    Then Purge order task should be successful in OL
 
 Scenario: WORKFLOW - NGFW Sanity Suite- CE 
    Given a provisioned ngfw subscriber in BBNMS
    Given OMS fixed wireless CE NA data order
    And subTransportType attribute on the order was NextGen
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    And Get Imei Iccid for the account
    When BBNMS receives a wll CPE request
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    When BBNMS receives a wll PTC request
    Then PONR task succeeds in ol
	And Remove Wll Profile task should be successful in OL
    And Remove Wll Policies task should be successful in OL
    And Deactivate Wll Service task should be successful in OL 
    And Purge order task should be successful in OL
    
    Scenario: WORKFLOW - NGFW Sanity Suite- CE 
    Given a provisioned ngfw subscriber in BBNMS
    Given OMS fixed wireless CE NA data order
    And subTransportType attribute on the order was NextGen
    When BBNMS receives a wll PT request
    Then BBNMS-OMS interface accepts the request
    And Get Imei Iccid for the account
    When BBNMS receives a wll CPE request
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    When BBNMS receives a wll PTC request
    Then PONR task succeeds in ol   
    And Remove Wll Profile task should be successful in OL
    And Remove Wll Policies task should be successful in OL
    And Deactivate Wll Service task should be successful in OL 
    And Purge order task should be successful in OL
    
    
    
    
