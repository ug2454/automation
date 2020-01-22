Feature: This feature file would be used to test DTV test scenarios of CDOO Driven SMS PID (US_670376)

Background: Initialization steps
And Set suite.order.series.dtv to 5065


	
	@Suspend
Scenario: Sunny Day, DTV Provide Order
Given PR-NA of dtv order
And dispatch indicator was Tech on the order
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


Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order was provisioned in PR
Given SU-NA of dtv order
And dispatch indicator was Tech on the order
When Dtv PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When Dtv CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
Then Suspend/Resume Services in UDAS task should be successful in OL
When Send Order Level PONR task should be successful in OL
When Dtv PTC Request is accepted
Then Purge order task should be successful in OL

Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order was provisioned in PR
Given AM is received on the order
Given SU-AM DTV Order
And dispatch indicator was Tech on the order
When Dtv PT Request is accepted
Then Rollback Suspend/Resume Services in UDAS task should be successful in OL
When Dtv CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL


Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order was provisioned in PR
Given RS-NA of dtv order
And dispatch indicator was Tech on the order
When Dtv PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When Dtv CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
Then Suspend/Resume Services in UDAS task should be successful in OL 

Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order was provisioned in PR
Given AM is received on the order
Given RS-AM DTV Order
And dispatch indicator was Tech on the order
When Dtv PT Request is accepted
Then Rollback Suspend/Resume Services in UDAS task should be successful in OL

Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order was provisioned in PR
Given CH-NA of dtv order
And dispatch indicator was Tech on the order
When Dtv PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When Dtv CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
Then  Activate Services in UDAS task should be successful in OL
Then  Retrieve WOLI Info from AHL task should be successful in OL
When Send Order Level PONR task should be successful in OL
When Dtv PTC Request is accepted
Then Purge order task should be successful in OL

Scenario: Sunny Day, DTV Suspend Provide Order
Given dtv subscriber with no pending order was provisioned in PR
Given AM is received on the order
Given CH-AM DTV Order
And dispatch indicator was Tech on the order
When Dtv PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When Dtv CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
Then Rollback Services Update in UDAS task should be successful in OL











	