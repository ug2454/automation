Feature: This feature file would be used to test DTV test scenarios of CDOO Driven SMS PID (US_670376)

  Background: Initialization steps
    And Set suite.order.series.dtv to 8075

  Scenario: PR-NA order
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
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: DelayedRemoveTrue CH-NA order.ActionIndicator=D and swapaction and previousAPID as null
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    Then Udas interface should populate SwapAction='DelayedRemove' from TdarReceiverProcessInfo table

  Scenario: DelayedRemoveTrue CH-NA order.ActionIndicator=D and swapaction and previousAPID as not null
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    Then Udas interface should populate SwapAction='DelayedRemove' from TdarReceiverProcessInfo table

  Scenario: DelayedRemoveFalse CH order.ActionIndicator!=D and swapaction and previousAPID as null
    Given dtv subscriber with no pending order was provisioned in PR
    Given CH-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    Then Udas interface should populate SwapAction is null from TdarReceiverProcessInfo table

  Scenario: Sunny Day, DTV Change Amend Order
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given CH-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL

  Scenario: CH_AM.uDAS interface will no-op the rollback task.Same date,Past date,Future date before CPE
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given CH-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    Then the rollback activity should be no-op

  Scenario: Sunny Day, DTV Suspend Provide Order
    Given dtv subscriber with no pending order was provisioned in PR
    Given SU-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Rollback Suspend/Resume Services in UDAS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    When Send Order Level PONR task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: SU_AM.uDAS interface will send HardRestore in action attribute under accountDetail structure.Future date after CPE
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given SU-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Rollback Suspend/Resume Services in UDAS task should be successful in OL
     And uDAS interface has sent HardRestore in action attribute under accountDetail structure
 
  Scenario: SU_AM.uDAS interface will no-op the rollback task.Same date,Past date,Future date before CPE
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given SU-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Rollback Suspend/Resume Services in UDAS task should be successful in OL
     Then the rollback activity should be no-op

  Scenario: RS_NA.uDAS interface will send HardSuspend in action attribute under accountDetail structure.Future date after CPE
    Given dtv subscriber with no pending order was provisioned in PR
    Given RS-NA of dtv order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    When Send Order Level PONR task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL

  Scenario: RS_AM.uDAS interface will no-op the rollback task.Same date,Past date,Future date before CPE
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given RS-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Rollback Suspend/Resume Services in UDAS task should be successful in OL
    And uDAS interface has sent HardSuspend in action attribute under accountDetail structure

  Scenario: RU_AM.uDAS interface will no-op the rollback task.Same date,Past date,Future date before CPE
    Given dtv subscriber with no pending order was provisioned in PR
    Given AM is received on the order
    Given RS-AM DTV Order
    And dispatch indicator was TECH on the order
    When Dtv PT Request is accepted
    Then Rollback Suspend/Resume Services in UDAS task should be successful in OL
    Then the rollback activity should be no-op 
