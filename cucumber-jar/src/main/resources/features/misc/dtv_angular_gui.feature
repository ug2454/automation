Feature: Order summary screen

  @dtv_historytab
  Scenario Outline: Activity Search History Tab
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <prodectType> by BAN in Angular GUI
    #And user click on Order link
    Then user verify history tab
   # And quit driver

    Examples: 
      | productType |
      |DTV       |
      
      
      @dtv_summary_duedate
      Scenario Outline: Order Summer Due Date
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <prodectType> by BAN in Angular GUI
   Then user verify ban,orderno, bbnmsorder no, due date, creation date, order status
   # And quit driver

    Examples: 
      | productType |
      |DTV       |
      
      @dtv_Subscriptioninfo
      Scenario Outline: Order Summer Due Date
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When open subscription search window
    And user searches for dtv ban
   Then user verify 4kcontent, swm, mrv, countyfips value
   # And quit driver

    Examples: 
      | productType |
      |DTV       |
      
       @dtv_manualcloseout
      Scenario Outline: Order Summer Due Date
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <productType> by BAN in Angular GUI
    Then user click on workflow
   Then user click on CloseoutWoli Activation
   # And quit driver

    Examples: 
      | productType |
      |DTV       |
      
      

 

      