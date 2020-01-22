Feature: This file contains scenarios for ART migration

  Scenario: Scenario to add a new migration from GUI
    Given bulk migration gui was successfully launched
    When user adds a new rehome migration
    And add migration gui tags are correct
    Then the add migration is successfull

  Scenario: VALIDATE Step validate
    Given rehome move was existing
    When move id is searched in gui
    And migration details screen has updated tags
    Then Validate step is successfull

  Scenario: CIM_DATA_SETUP Step validate
    Given rehome move was existing
    When user starts CIM_DATA_SETUP step from gui
    Then CIM_DATA_SETUP step is successfull

  Scenario: CREATE_SAPS Step validate
    Given rehome move was existing
    When user starts CREATE_SAPS step from gui
    Then CREATE_SAPS step is successfull

  Scenario: CREATE_ASE_SAPS Step validate
    Given rehome move was existing
    When user starts CREATE_ASE_SAPS step from gui
    Then CREATE_ASE_SAPS step is successfull

  Scenario: GENERATE_IPADDRESS Step validate
    Given rehome move was existing
    When user starts GENERATE_IPADDRESS step from gui
    Then GENERATE_IPADDRESS step is successfull

  Scenario: COLLECT_IPADDRESS and ASSIGN_IPADDRESS Step validate
    Given rehome move was existing
    When user starts COLLECT_IPADDRESS step from gui
    Then COLLECT_IPADDRESS step is successfull
    And ASSIGN_IPADDRESS step is successfull

  Scenario: DELETE_ASE_SDP_BINDINGS Step validate
    Given rehome move was existing
    When user starts DELETE_ASE_SDP_BINDINGS step from gui
    Then DELETE_ASE_SDP_BINDINGS step is successfull

  @Migration
  Scenario: CREATE_ASE_SDP_BINDINGS Step validate
    Given rehome move was existing
    When user starts CREATE_ASE_SDP_BINDINGS step from gui
    Then CREATE_ASE_SDP_BINDINGS step is successfull

  @Rollback
  Scenario: Rollback existing migration
    Given rehome move was existing
    When user performs rollback from gui
    Then rollback migration should be successfull
