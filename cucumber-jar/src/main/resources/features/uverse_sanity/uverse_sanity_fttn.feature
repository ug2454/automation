@tag
Feature: This feature file would be used to test Uverse scenarios daily Sanity for FTTN NTI

Background: Initialization steps
#Given Perform context cleanup
And Set suite.order.series.uverse to 5065


      
  @Sanity_PR_Order
  Scenario Outline: Sunny Day, uVerse Provide Order for FTTN
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And oms Request was accepted
    When valid publish rg notification is accepted
    Then Receive NAD Info task should be successful in OL
    And E/C/S Activation is successful
    And PONR is succeeded
	And PTC request was accepted
	And Order is Purged
	
    Examples: 
      | ordertype | subtype | NTI       |
      | PR        | NA      | FTTN      |
    

  @Sanity_CH_Order
  Scenario Outline: Sunny Day, uVerse Change Order for FTTN
  Given uverse subscriber with no pending order was provisioned in PR
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And oms Request was accepted
    And E/C/S Activation is successful
    And PONR is succeeded
	And PTC request was accepted
	And Order is Purged
	
    Examples: 
      | ordertype | subtype | NTI       |
      | CH        | NA      | FTTN      |
     



  @Sanity_CE_Order
    Scenario Outline: Sunny Day, uVerse Cease Order for FTTN
    Given uverse subscriber with no pending order was provisioned in PR
    Given a non BVOIP Uverse <ordertype> <subtype> <NTI> order
    And oms Request was accepted
    And PONR is succeeded
    And PTC request was accepted
    And Deactivate Policy in G2 is succeeded
    And E/C/S DeActivation is successful
    And Order is Purged
    Given Perform context cleanup

    Examples: 
      | ordertype | subtype | NTI       |
      | CE        | NA      | FTTN      |
     
