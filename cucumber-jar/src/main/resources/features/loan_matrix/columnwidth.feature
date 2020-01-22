Feature: US402382_Sprint23 : functionality of adjusting column widths in Loan Matrix module

  @loanmatrix @402382 @Cpgui @Sprint23
  Scenario: planner is trying to adjust column widths in Loan Matrix module
    Given planner was able to navigate to Loan constraints in loan matrix tab
    And planner clicks on loan constraints option
    When planner adjusts width of columns
    Then the column width shall be successfully adjusted
    When planner clicks on loan graphs option
    And planner adjusts width of columns
    Then the column width shall be successfully adjusted