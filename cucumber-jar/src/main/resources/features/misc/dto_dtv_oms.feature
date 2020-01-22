Feature: This feature file would be used to test DTV Provide Order Sanity

  Background: Initialization steps
    And Set suite.order.series.dtv to 5065

  Scenario: Client has parent receiver ID in the PONR response large-Y and N
    Given PR-NA of dtv order
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
    And the client should have the parent receiver ID in the PONR
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: Client has parent receiver ID in the PONR response for CH-NA large-Y and N
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
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
    And the client should have the parent receiver ID in the PONR for CH order

  Scenario: TdarProductLineValidationInfo has the new column GenieReceiver
    #Given GenieReceiver column is present  in the  TdarProductLineValidationInfotable
    Given the value for the Genie receiver under the column GenieReceiver was 1
