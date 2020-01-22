@tag
Feature: This feature file would be used to test DTV test scenarios of CDOO Driven SMS PID (US_670376)

  Background: Initialization steps
    Given Set up dmaap Kafka adapter
    And Set suite.order.series.dtv to 5065

 
  #@tag1 
  Scenario: Sunny Day, DTV Provide Order 
    Given a dtv PR-NA order 
    And dispatch indicator was TECH on the order  
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL 
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL 
    And Activate Services in UDAS task should be successful in OL 
    And Retrieve WOLI Info from AHL task should be successful in OL 
    When ISAAC Initiate request is accepted
    Then Wait For Initiate WOLI Activation task should be successful in OL 
    When ISAAC Process request for Multiple activation is accepted
    Then Receive Initiate WOLI Activation task should be successful in OL 
    When ISAAC Closeout request is accepted
    Then Receive Complete WOLI Activation task should be successful in OL 
    When Dtv PTC Request is accepted 
    Then Purge order task should be successful in OL 
    Then order listeners should be notified for the following events: 
      | event_name              | event_status | 
      | STB_COUNT               | STARTED      | 
      | STB_COUNT               | COMPLETE     | 
      | INSTALLATION_INITIATION | COMPLETE     | 
      | STB_ACTIVATION          | STARTED      | 
      | STB_ACTIVATION          | COMPLETE     | 
      | STB_ACTIVATION          | STARTED      | 
      | STB_ACTIVATION          | COMPLETE     | 
      | WORK_ORDER_COMPLETION   | COMPLETE     | 
 
 
Scenario: Sunny Day, DTV Change Order 
Given dtv subscriber with no pending order was provisioned in PR 
Given a dtv CH-NA order 
And dispatch indicator was TECH on the order 
And 2 STBs have action Indicator I
When Dtv PT Request is accepted 
Then Receive Transport Request from OMS task should be successful in OL 
When Dtv CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL 
Then Activate Services in UDAS task should be successful in OL 
And Retrieve WOLI Info from AHL task should be successful in OL
When ISAAC Initiate request is accepted
Then Wait For Initiate WOLI Activation task should be successful in OL
When ISAAC Process request for Multiple activation is accepted
Then Receive Initiate WOLI Activation task should be successful in OL
When ISAAC Closeout request is accepted
Then Receive Complete WOLI Activation task should be successful in OL
When Send Order Level PONR task should be successful in OL 
When Dtv PTC Request is accepted 
Then Purge order task should be successful in OL 
Then order listeners should be notified for the following events: 
      | event_name              | event_status | 
      | STB_COUNT               | STARTED      | 
      | STB_COUNT               | COMPLETE     | 
      | INSTALLATION_INITIATION | COMPLETE     | 
      | STB_ACTIVATION          | STARTED      | 
      | STB_ACTIVATION          | COMPLETE     | 
      | STB_ACTIVATION          | STARTED      | 
      | STB_ACTIVATION          | COMPLETE     | 
      | WORK_ORDER_COMPLETION   | COMPLETE     |
