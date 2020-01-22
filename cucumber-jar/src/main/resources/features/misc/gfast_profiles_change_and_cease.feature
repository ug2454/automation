Feature: Gfast provisioning and profile based scenarios for change and cease

  Scenario Outline: 7360
    Given an existing uverse gfast subscriber in BBNMS
    And a uverse gfast CH-NA order
    And speedcode is <speedCode> on the order
    When BBNMS accepts the PT Request
    And Activation is successful in CIM
    Then BBNMS-CIM updates the service profile of gfast subscriber

    Examples: 
      | speedCode |
      | Hsia5x5   |
      | 5m5mg     |

  Scenario Outline: 
    Given an existing uverse gfast subscriber in BBNMS
    And a uverse gfast CH-NA order
    And speedcode is <speedCode> on the order
    When BBNMS accepts the PT Request
    And Activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    When BBNMS accepts an Amend PT Request with speedcode <amendedspeedcode>
    Then BBNMS-CIM updates the customer serviceprofile

    Examples: 
      | speedCode | amendedspeedcode |
      | Hsia5x5   | Hsia100g         |
      | 5m5mg     | Hsia100g         |
      | Hsia5X5   | Hsia10X10        |

  Scenario: cease order
    Given an existing uverse gfast subscriber in BBNMS
    And a uverse gfast CE-NA order
    When BBNMS accepts the PT Request
    And Deactivation is successful in CIM
    Then BBNMS-CIM removes subscriber from database
    And Deactivate dpu request response is success in ML
    And Deactivation Request Response is Success in AL
