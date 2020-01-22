Feature: Fiber Jack Granite and Edge Interface Validation

  Background: Initialization steps
    Given Perform context cleanup
    And Get avaialable port from CIM

  Scenario Outline: Fibre Jack EFJ Validation
    Given for uverse <nti> <OrderType>-<Order SubType> order
    And card type was FWLT-B in the order
    When BBNMS receives <omsReqType> for the order from OMS
    And BBNMS shall accept the <omsReqType> Request
    And Activation shall be successful in OL
    Then workInstruction attribute in CreateFiberServiceWorkOrderRequest shall have value as EFJ

    Examples: 
      | nti    | OrderType | Order SubType | omsReqType |
      | xgspon | PR        | NA            | PT         |
      | xgspon | PR        | AM            | PT         |
      | xgspon | PR        | CA            | PT         |
      | xgspon | CH        | NA            | PT         |
      | xgspon | CH        | AM            | PT         |
      | xgspon | CH        | CA            | PT         |

  Scenario: Fibre Jack EFJ Validation - Fttp Gpon
    Given a uverse gpon PR-NA order
    And card type is FWLT-B
    When BBNMS accepts PT Request
    And Activation is successful in OL
    Then workInstruction attribute in CreateFiberServiceWorkOrderRequest has value EFJ

  Scenario Outline: Fibre Jack PFJ Validation
    Given a uverse xgspon <OrderType>-<Order SubType> order
    And card type is FWLT-B for PFJ Validation
    And Product Speedcode is Hsia1000g
    And Video Service is added to the order
    When BBNMS accepts PT Request for PFJ orders
    And Activation is successful in OL
    Then workInstruction attribute in CreateFiberServiceWorkOrderRequest has value PFJ

    Examples: 
      | OrderType | Order SubType |
      | PR        | NA            |
      | PR        | AM            |
      | PR        | CA            |
      | CH        | NA            |
      | CH        | AM            |
      | CH        | CA            |

  Scenario: Fibre Jack PFJ Validation - Fttp Gpon
    Given a uverse gpon PR-NA order
    And card type is FWLT-B
    When BBNMS accepts PT Request
    And Activation is successful in OL
    Then workInstruction attribute in CreateFiberServiceWorkOrderRequest has value PFJ
