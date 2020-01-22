Feature: This feature file lists gfast profile related provide order scenarios.

  Scenario Outline: 7360
    Given a uverse gfast PR-NA order
    And speedcode is <speedcode> on the order
    When BBNMS accepts the PT Request
    And activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    And ActivateResponse is successful in AL

    Examples: 
      | speedcode |
      | Hsia10x10 |
      | 5m5mg     |

  Scenario Outline: 7342
    Given a uverse gfast PR-NA order
    And speedcode is <speedcode> on the order
    When BBNMS accepts the PT Request
    And activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    And ActivateResponse is successful in AL
    And ActivateDPUPortRequest is successful in ML

    Examples: 
      | speedcode |
      | Hsia5x5   |
      | Hsia10x10 |

  Scenario Outline: 7342
    
    Given	a uverse gfast PR-NA order

    And speedcode is <speedcode> on the order
    When BBNMS accepts the PT Request
    And Activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    When BBNMS accepts a PR-CA Order
    Then BBNMS-CIM removes subscriber from database

    Examples: 
      | speedcode |
      | Hsia5x5   |
      | 200m40mg  |
      | 25m25mg   |

  Scenario Outline: 7360
    
    Given	a uverse gfast PR-NA order

    And speedcode is <speedcode> on the order
    When BBNMS accepts the PT Request
    And Activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    When BBNMS accepts a PR-CA Order
    Then BBNMS-CIM removes subscriber from database

    Examples: 
      | speedcode |
      | Hsia5x5   |
      | 200m40mg  |
      | 25m25mg   |

  Scenario Outline: 7360
    Given a uverse gfast PR-NA order
    And speedcode is <speedcode> on the order
    When BBNMS accepts the PT Request
    And Activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    When BBNMS accepts an Amend PT Request with speedcode <amendedspeedcode>
    Then BBNMS-CIM updates the customer serviceprofile

    Examples: 
      | speedcode | amendedspeedcode |
      | Hsia500a  | Hsia5x5          |
      | 10m10mg   | 25m25mg          |
      | Hsia5x5   | 10m10mg          |

  Scenario Outline: 7342
    Given a uverse gfast PR-NA order
    And speedcode is <speedcode> on the order
    When BBNMS accepts the PT Request
    And Activation is successful in CIM
    Then BBNMS-CIM marks the customer as gfast customer
    When BBNMS accepts an Amend PT Request with speedcode <amendedspeedcode>
    Then BBNMS-CIM updates the customer serviceprofile

    Examples: 
      | speedcode | amendedspeedcode |
      | Hsia5x5   | Hsia100g         |
      | 50m50mg   | 100m20mg         |
      | 100m100mg | 100m20mg         |
