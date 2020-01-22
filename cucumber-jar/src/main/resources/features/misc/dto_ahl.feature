Feature: This feature file would be used to test DTV Provide Order Sanity

  Background: Initialization steps
    And Set suite.order.series.dtv to 8060

  Scenario: PR-NA multiple Genies and TunerCount>30
    Given PR-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    When there are multiple DSWM30 and the power insertor on the order
    Then the Counts of multiple DSWM30 and the power insertor for PR NA order should be validated
    When ISAAC Initiate request is accepted
    Then Wait For Initiate WOLI Activation task should be successful in OL
    When ISAAC Process request for Multiple Genies activation is accepted
    Then Receive Initiate WOLI Activation task should be successful in OL
    And ISAAC Process request for NIRDs was accepted
    When ISAAC Closeout request is accepted
    Then Receive Complete WOLI Activation task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: CH-NA multiple Genies and TunerCount>30
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    When there are multiple DSWM30 and the power insertor on the order
    Then the Counts of multiple DSWM30 and the power insertor for CH NA order should be validated
    Then the IRD-REASSIGN Woli for the Clients to be assigned should be present
    When ISAAC Initiate request is accepted
    Then Wait For Initiate WOLI Activation task should be successful in OL
    When ISAAC Process request for Multiple Genies activation is accepted
    Then Receive Initiate WOLI Activation task should be successful in OL
    And ISAAC Process request for NIRDs was accepted
    When ISAAC Closeout request is accepted
    Then Receive Complete WOLI Activation task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: IRD _REASSIGN CH-NA
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    Then the IRD-REASSIGN Woli for the Clients to be assigned should be present

  Scenario: IRD _REASSIGN CH-AM
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    Then the IRD-REASSIGN Woli for the Clients to be assigned should be present

  Scenario: CH-AM multiple Genies and TunerCount>30
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given CH-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    When there are multiple DSWM30 and the power insertor on the order
    Then the Counts of multiple DSWM30 and the power insertor for CH NA order should be validated

  Scenario: PV-NA multiple Genies and TunerCount>30
    Given dtv subscriber with no pending order was provisioned in PR
    Given PV-NA of dtv order
    And dispatch indicator was TECH on the order
    When BBNMS accepts an DTV F&T PT request
    Then Receive Transport Request from OMS task should be successful in OL
    When BBNMS accepts an DTV F&T CPE request
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    Then there are multiple DSWM30 and the power insertor on the order
