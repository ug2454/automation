@tag
Feature: This feature file would be used to test DTV test scenarios of CDOO Driven SMS PID (US_670376)

Background: Initialization steps
And Set suite.order.series.dtv to 5065

@tag1
Scenario: Sunny Day, DTV Provide Order
Then validate Delayedremove column from Dtvversion should be 1
Then validate LargeCustomer column from Dtvversion should be 1
Given PR-NA of dtv order
And dispatch indicator was Tech on the order 
When DTV PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When DTV CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
And Activate Services in UDAS task should be successful in OL
And Retrieve WOLI Info from AHL task should be successful in OL
When ISAAC Initiate request is accepted
Then Wait For Initiate WOLI Activation task should be successful in OL
When ISAAC Process request for Multiple activations are accepted
Then Receive Initiate WOLI Activation task should be successful in OL
When ISAAC Closeout request is accepted
Then Receive Complete WOLI Activation task should be successful in OL
When DTV PTC Request is accepted
Then Purge order task should be successful in OL


#When ISAAC DOA Swap activation request on pending order is accepted
#Then Receive Initiate WOLI Activation task should be successful in OL


#Given dtv subscriber with no pending order was provisioned in PR
#When ISAAC Initiate Repair request is accepted
#Then Wait For Initiate WOLI Activation task should be successful in OL
#When ISAAC Multiple Repair swap requests are accepted
#Then Receive Initiate WOLI Activation task should be successful in OL
#When ISAAC Closeout Repair request is accepted
#Then Receive Complete WOLI Activation task should fail in ol


#When ISAAC Process Update notify request is accepted
#Then Receive Initiate WOLI Activation task should be successful in OL


 Scenario: Sunny Day, DTV Change Order
Given dtv subscriber with no pending order was provisioned in PR
Given CH-NA of dtv order
And dispatch indicator was Tech on the order 
When DTV PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When DTV CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
And Activate Services in UDAS task should be successful in OL
And Retrieve WOLI Info from AHL task should be successful in OL
When ISAAC Initiate request is accepted
Then Wait For Initiate WOLI Activation task should be successful in OL
When ISAAC Swap request of Client swap from one Genie to another Genie is accepted
Then Receive Initiate WOLI Activation task should be successful in OL
Then Validate ISAAC interface should reject the deactivation request with error message
Then validate Delayedremove value from Dtvversion should be 1



#When ISAAC Closeout request is accepted
#Then Receive Complete WOLI Activation task should fail in ol

#When Deactivation requests are sent on the SSCPET api for the client WOLIs
#Then Receive Initiate WOLI Activation task should be successful in OL
#When Reactivation requests are sent on the SSCPET api for the client WOLIs
#Then Receive Initiate WOLI Activation task should be successful in OL








#Scenario: Sunny Day, DTV Change Amend Order
#Given dtv subscriber with no pending order was provisioned in PR
#Given is received on the order
#And dispatch indicator on the order is Tech
#When DTV PT Request is accepted
#Then Receive Transport Request from OMS task should be successful in OL
#When DTV CPE Request is accepted
#Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
#And Activate Services in UDAS task should be successful in OL
#And Retrieve WOLI Info from AHL task should be successful in OL


#When Multiple Deactivation requests are sent on the SSCPET api for the client WOLIs
#Then Receive Initiate WOLI Activation task should be successful in OL








#When Deactivation requests for AI I are sent on the SSCPET api for the client WOLIs
#Then Receive Initiate WOLI Activation task should be successful in OL




#When ISAAC Process request for NIRDs was accepted
#Then Receive Initiate WOLI Activation task should be successful in OL



#When BBNMS accept ISAAC Closeout request
#Then Receive Complete WOLI Activation task should be successful in OL
#When BBNMS accepts dtv PTC request
#Then Purge order task should be successful in OL


 
 Scenario: Sunny Day, DTV PV Order
Given dtv subscriber with no pending order was provisioned in PR
Given PV-NA of dtv order
And dispatch indicator was Tech on the order 
When DTV F&T PT Request is accepted
Then Receive Transport Request from OMS task should be successful in OL
When DTV F&T CPE Request is accepted
Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
And Activate Services in UDAS task should be successful in OL
And Retrieve WOLI Info from AHL task should be successful in OL
When ISAAC Initiate request is accepted
Then Wait For Initiate WOLI Activation task should be successful in OL
When ISAAC Process request for Multiple activations are accepted
Then Receive Initiate WOLI Activation task should be successful in OL
When ISAAC Closeout request is accepted
Then Receive Complete WOLI Activation task should be successful in OL
When DTV PTC Request is accepted
Then Purge order task should be successful in OL

  
#Then Validate LargeCustomer Indicator value should be 1










	