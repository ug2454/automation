
@tag
Feature: This feature file would be used to test WVB for DTV test scenarios 

Background: Initialization steps
And Set suite.order.series.dtv to 5080
 

  @tag1
Scenario: Pass Model Number for the Wireless Video Bridge in the PONR response
Given a PR-NA dtv order
	And dispatch indicator on the order is TECH
	When BBNMS accepts dtv PT request
	Then Receive Transport Request from OMS task should be successful in OL
	When BBNMS accepts dtv CPE request
	Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
	And Activate Services in UDAS task should be successful in OL
	And Retrieve WOLI Info from AHL task should be successful in OL
	When BBNMS accept ISAAC Initiate request
	Then Wait For Initiate WOLI Activation task should be successful in OL
	When BBNMS accept ISAAC Process request
	Then Receive Initiate WOLI Activation task should be successful in OL
	When BBNMS accept ISAAC Closeout request
	Then Receive Complete WOLI Activation task should be successful in OL
Then  PONR should contain modelNumber for the WVB
And   PONR should contain manufacturer for the WVB

 