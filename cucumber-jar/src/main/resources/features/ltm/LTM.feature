Feature: Daily Plan

 
  Scenario: Lunch should not be included in unavailable time in Daily Plan  
    Given planner navigated to ltm
    When planner navigated to ltm ag
   And has navigated to Demand Duration under Demand Summary
Then a new Toggle “Hours/Minutes” will appear at top of the screen with Hours as default 

    