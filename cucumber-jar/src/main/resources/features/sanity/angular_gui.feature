Feature: Order summary screen

  @subscription_search
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And user searches an order for <productType> in Angular GUI
    And user click on Order link
    Then user click on BAN
   # And quit driver

    Examples: 
      | productType |
      |Uverse       |

  @woli_item
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And user searches an order for <productType>  in Angular GUI
    And user click on Order link
    And user click on WOLI ID
    And quit driver

    Examples: 
      | productType |
      | DTV         |

  @searchbyBAN_ValidateConntedProp
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <productType> by BAN in Angular GUI
    #Then user click on workflow
    And verify value of MDU Connected Propert is No
    And quit driver

    Examples: 
      | productType |
      | DTV         |

  @searchbyBAN_click_workflow
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <productType> by BAN in Angular GUI
    Then user click on workflow
    And quit driver

    Examples: 
      | productType |
      | DTV         |

  @portswapscreenenable
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Admin Page
    And user enable BBExpress PortSwap
    Then user validate success message for enable
    And quit driver

    Examples: 
      | productType |
      | Uverse      |
      
      @portswapscreendisable
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Admin Page
    And user disable BBExpress PortSwap
    Then user validate success message for disable
    And quit driver

    Examples: 
      | productType |
      | Uverse      |
      
      
      #completed
       @DeactivateClient   
  Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <productType> by BAN in Angular GUI
    Then user click on workflow
    And user navigates to Reassign Client Screen
    And user click dropdown receiver
    When Deactivate or Reassign based on status which is Active
    And user click Refresh Button
    Then Verify Client is Deactivated
    
   # And quit driver

    Examples: 
      | productType |
      | DTV         |

      
       @ReassignClient
      Scenario Outline: Navigate to Workflow
    Given a service-provisioned <productType> subscriber exist in BBNMS
    When user navigates to Angular GUI page
    And search an order for <productType> by BAN in Angular GUI
    Then user click on workflow
    And user navigates to Reassign Client Screen
    And user click dropdown receiver
    When Deactivate or Reassign based on status which is InActive
    And user click Refresh Button
    Then Verify Client is Reassinged
   
    
   # And quit driver

    Examples: 
      | productType |
      | DTV         |
      
       @cvoip_activity_failed
  Scenario Outline: Validate cvoip failed activities
    Given a service-provisioned <productType> subscriber exist in BBNMS
    And user opens Angular GUI   
    When cvoip failed activities was searched by user    
    And user sort by failed activities
    Then result and activity name should be verified for failed order
    
    Examples: 
      | productType |
      | Uverse         |
      
      @cvoip_activity_inprog
  Scenario Outline: Validate cvoip failed activities
    Given a service-provisioned <productType> subscriber exist in BBNMS
    And user opens Angular GUI   
    When cvoip failed activities was searched by user
    Then result and activity name should be verified for inprogress order
    
    Examples: 
      | productType |
      | Uverse      |
      
       @reset_order_inprogress
  Scenario Outline: Validate cvoip failed activities
    Given a service-provisioned <productType> subscriber exist in BBNMS
    And user opens Angular GUI 
    When user searches for Inprogress Activity 
    And user click on workflow
    Then verify reset button should be working 
    
     Examples: 
      | productType |
      | Uverse      | 
      
      