Feature: Daily Plan

  @sdnd @402320 @Cpgui @Sprint23
  Scenario: Lunch should not be included in unavailable time in Daily Plan  
    Given planner was able to navigate to AG hierarchahy in daily plan tab
    When planner clicks on AG option
    Then planned hours under unproductive time is displayed
    And the hours do not include lunch
    
    @401488
   Scenario: Loan Popup 
    Given the user is logged into CP2.0		
		And User has navigated to Daily plan
		And user has selected Geography to which he has access under
		When User clicks on the edit icon in Loan section of Daily Plan
		Then User should see a popup with bread crumbs showing geo hierarchy on top left and Date from the plan on top right
		And In Bucket/Total = In Bucket/Total for the bucket name in column 1 or main bucket in case of header. Read only. This field should display In Bucket and Total number of Demand Technicians ONLY. Value to be displayed for Total = Technicians - Demand - Planned+6th Day OT Techs. Value to be displayed for In Bucket = Technicians - Demand - Planned + Loan In-Loaned Out+6th Day OT Techs. This field is recalculated and rendered every time Loan To and/or Loan From are edited.
		And Hours/Tech = Hours/Tech for the bucket name in column 1 or main bucket in case of header. Read only. Tech count used in this formula should be In Bucket = Technicians - Demand - Planned + Loan In-Loaned Out+6th Day OT Techs
		And Jobs/Tech = Jobs/Tech for the bucket name in column 1 or main bucket in case of header. Read only. This field is recalculated and rendered every time Loan To and/or Loan From are edited. Tech count used in this formula should be In Bucket = Technicians - Demand - Planned + Loan In-Loaned Out+6th Day OT Techs
		And OT Hours/Tech = OT Hours/Tech for the bucket name in column 1 or main bucket in case of header. Read only. This field is recalculated and rendered every time Loan To and/or Loan From are edited. Tech count used in this formula should be In Bucket = Technicians - Demand - Planned + Loan In-Loaned Out+6th Day OT Techs
				