Feature: This feature file would be used to test DTV test scenarios of CDOO Driven SMS PID (US_670376)

Background: Initialization steps
And Set suite.order.series.dtv to 5065

#@waitforInitTimer
#Scenario: Sunny Day, DTV Provide Order
#Given a PR-NA dtv order
#And dispatch indicator on the order is TECH
#When BBNMS accepts dtv PT request
#Then Receive Transport Request from OMS task should be successful in OL
#When BBNMS accepts dtv CPE request
#Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
#And Activate Services in UDAS task should be successful in OL
#And Retrieve WOLI Info from AHL task should be successful in OL
#When BBNMS accept ISAAC Initiate request
#Then Wait For Initiate WOLI Activation task should be successful in OL
#When BBNMS accept ISAAC Process Multiple activation request
#	Then Receive Initiate WOLI Activation task should be successful in OL
#	And validate order will wait for 6 min
#	When BBNMS accept ISAAC Closeout request
	
	@Suspend
Scenario: Sunny Day, DTV Provide Order
Given a PR-NA dtv order
And dispatch indicator on the order is Tech
When BBNMS accepts dtv PT request
Then Receive Transport Request from OMS task should be successful in OL
When BBNMS accepts dtv CPE request
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
And Activate Services in UDAS task should be successful in OL
And Retrieve WOLI Info from AHL task should be successful in OL
When BBNMS accept ISAAC Initiate request
Then Wait For Initiate WOLI Activation task should be successful in OL
When BBNMS accept ISAAC Process Multiple activation request
Then Receive Initiate WOLI Activation task should be successful in OL
When BBNMS accept ISAAC Closeout request
Then Receive Complete WOLI Activation task should be successful in OL
When BBNMS accepts dtv PTC request
Then Purge order task should be successful in OL


Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order is provisioned in PR
Given a SU-NA dtv order
And dispatch indicator on the order is Tech
When BBNMS accepts dtv PT request
Then Rollback Suspend/Resume Services in UDAS task should be successful in OL

Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order is provisioned in PR
Given AM is received on the order
Given SU-AM DTV Order
And dispatch indicator on the order is Tech
When BBNMS accepts dtv PT request
Then Receive Transport Request from OMS task should be successful in OL
When BBNMS accepts dtv CPE request
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
When Send Order Level PONR task should be successful in OL
When BBNMS accepts dtv PTC request
Then Purge order task should be successful in OL

#Scenario: Sunny Day, DTV Suspend Provide Order
#Given dtv subscriber with no pending order is provisioned in PR
#Given a RS-NA dtv order
#And dispatch indicator on the order is Tech
#When BBNMS accepts dtv PT request
#Then Receive Transport Request from OMS task should be successful in OL



	