Feature: This feature file would be used to convert qtp tc to bdd cucumber

Background: Initialization steps
And Set suite.order.series.dtv to 5065


	
	@NFFL_Receiver
Scenario: NFFL_SSCPET_PR
		Given a dtv PR-NA order
    And dispatch indicator was TECH on the NFFL order
    And nffl Indicator and dealer code was passed
    When Dtv PT Request is accepted
    Then Receive Transport Request from OMS task should be successful in OL
    When Dtv CPE Request is accepted
    Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    And Activate Services in UDAS task should be successful in OL
    And Retrieve WOLI Info from AHL task should be successful in OL
    When ISAAC Initiate request is accepted
    Then Wait For Initiate WOLI Activation task should be successful in OL
    When ISAAC Process activation request for NFFL order is accepted
    Then Receive Initiate WOLI Activation task should be successful in OL
    When ISAAC Closeout request is accepted
    Then Receive Complete WOLI Activation task should be successful in OL
    When Dtv PTC Request is accepted
    Then Purge order task should be successful in OL
    
    #@NFFL_SWAP
#Scenario: NFFL_SSCPET_PR
#Given a dtv PR-NA order
    #And dispatch indicator was TECH on the NFFL order
    #And nffl Indicator and dealer code was passed
    #When Dtv PT Request is accepted
    #Then Receive Transport Request from OMS task should be successful in OL
    #When Dtv CPE Request is accepted
    #Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    #And Activate Services in UDAS task should be successful in OL
    #And Retrieve WOLI Info from AHL task should be successful in OL
    #When ISAAC Initiate request is accepted
    #Then Wait For Initiate WOLI Activation task should be successful in OL
    #When ISAAC Process activation request for NFFL order is accepted
    #Then Receive Initiate WOLI Activation task should be successful in OL
    #When ISAAC DOA Swap activation request on pending order is accepted
    #Then Receive Initiate WOLI Activation task should be successful in OL
    #Then Dealer code for swap should be reflected in tdarwolitable 
    #When ISAAC Closeout request is accepted
    #Then Receive Complete WOLI Activation task should be successful in OL
    #When Dtv PTC Request is accepted
    #Then Purge order task should be successful in OL
    
 #@NFFL_Hardware
 #Scenario: NFFL_SSCPET_PR_HARDWARE
#Given a dtv PR-NA order
    #And dispatch indicator was TECH on the NFFL order
    #And nffl Indicator and dealer code was passed
    #When Dtv PT Request is accepted
    #Then Receive Transport Request from OMS task should be successful in OL
    #When Dtv CPE Request is accepted
    #Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    #And Activate Services in UDAS task should be successful in OL
    #And Retrieve WOLI Info from AHL task should be successful in OL
    #When ISAAC Initiate request is accepted
    #Then Wait For Initiate WOLI Activation task should be successful in OL
    #When ISAAC Process activation request for NFFL order is accepted
    #Then Receive Initiate WOLI Activation task should be successful in OL
    #Then Dealer code for hardware should be reflected in tdarwolitable 
    #When ISAAC Closeout request is accepted
    #Then Receive Complete WOLI Activation task should be successful in OL
    #When Dtv PTC Request is accepted
    #Then Purge order task should be successful in OL

#@NFFL_Asset
#Scenario: NFFL_SSCPET_PR_HARDWARE
#Given a dtv PR-NA order
    #And dispatch indicator was TECH on the NFFL order
    #And nffl Indicator and dealer code was passed
    #When Dtv PT Request is accepted
    #Then Receive Transport Request from OMS task should be successful in OL
    #When Dtv CPE Request is accepted
    #Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
    #And Activate Services in UDAS task should be successful in OL
    #And Retrieve WOLI Info from AHL task should be successful in OL
    #When ISAAC Initiate request is accepted
    #Then Wait For Initiate WOLI Activation task should be successful in OL
    #When ISAAC Process activation request for NFFL order is accepted
    #Then Receive Initiate WOLI Activation task should be successful in OL
    #Then Dealer code for hardware should be reflected in tdarwolitable 
    #When ISAAC Closeout request is accepted
    #Then Receive Complete WOLI Activation task should be successful in OL
    #When Dtv PTC Request is accepted
    #When Update Asset DB task should be successful in OL
    #Then Dealer code should be reflected in asset db
    #Then Purge order task should be successful in OL


#Scenario: Sunny Day, DTV Suspend Provide Order
#Given a dtv PR-NA order
    #And dispatch indicator was TECH on the NFFL order
    #And nffl Indicator and dealer code was passed
#		And dispatch indicator was TECH on the NFFL order
#		When Dtv PT Request is accepted
#		Then Receive Transport Request from OMS task should be successful in OL
#		When Dtv CPE Request is accepted
#		Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
#		Then  Activate Services in UDAS task should be successful in OL
#		Then  Retrieve WOLI Info from AHL task should be successful in OL
#		Then Dealer code and NFFL indicator should be reflected in tdarwoli table
#		When ISAAC Initiate request is accepted
  #	Then Wait For Initiate WOLI Activation task should be successful in OL
    #When ISAAC Process activation request for NFFL order is accepted
    #Then Receive Initiate WOLI Activation task should be successful in OL
    #When ISAAC Closeout request is accepted
    #Then Receive Complete WOLI Activation task should be successful in OL
#		When Send Order Level PONR task should be successful in OL
#		When Dtv PTC Request is accepted
#		Then Purge order task should be successful in OL

#Scenario: Sunny Day, DTV Suspend Provide Order
#Given dtv subscriber with no pending order was provisioned in PR
#Given AM is received on the order
#Given CH-AM DTV Order
#And dispatch indicator was Tech on the order
#When Dtv PT Request is accepted
#Then Receive Transport Request from OMS task should be successful in OL
#When Dtv CPE Request is accepted
#Then Receive Exchange CPE Data 1st Call - CPE Data task should be successful in OL
#Then Rollback Services Update in UDAS task should be successful in OL











	