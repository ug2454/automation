Feature: This feature file would be used to test DTV Provide Order Sanity

  Background: Initialization steps
    And Set suite.order.series.dtv to 5065

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
    When ISAAC Process activation request is accepted
    Then Receive Initiate WOLI Activation task should be successful in OL
    When ISAAC Closeout request is accepted
    Then Receive Complete WOLI Activation task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: Sunny Day, DTV Change Order
    Given dtv subscriber with no pending order was provisioned in PR
    Given a dtv CH-NA order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    When ISAAC Initiate request is accepted
    Then Wait For Initiate WOLI Activation task should be successful in OL
    When ISAAC Process activation request is accepted
    Then Receive Initiate WOLI Activation task should be successful in OL
    When ISAAC Closeout request is accepted
    Then Receive Complete WOLI Activation task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: Sunny Day, DTV Cease Order
    Given dtv subscriber with no pending order was provisioned in PR
    Given a dtv CE-NA order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Send Order Level PONR task should be successful in OL
    When Dtv PTC Request is accepted
    And Deactivate Services in UDAS task should be successful in OL
    And Deactivate Video Wireless Account task should be successful in OL
    Then Purge order task should be successful in OL
