Feature: purchaseTerms validation

  Background: Initialization steps
    Given Set suite.order.series.dtv to 9981

  Scenario Outline: purchase terms value validation
    Given for a <dtvBan>
    And <orderType>-<orderSubtype>, <productType> order was received by BBNMS
    When purchaseTerms attribute is present under DTSTB component on provisionTransport request
    And actionIndicator of respective DTSTB is <actionIndicatorValue>
    And purchaseTerms attribute value is <purchaseTermsValue>
    Then BBNMS <omsAction> the PT for the order

    Examples: 
      | dtvBan    | orderType | orderSubtype | productType | actionIndicatorValue | purchaseTermsValue | omsAction |
      | 123456789 | PR        | NA           | RC1         | I                    | Rent               | Accepts   |
      | 123456789 | PR        | AM           | RC1         | I                    | Rent               | Accepts   |
      | 123456789 | PR        | NA           | NFFL        | I                    | Purchase           | Accepts   |
      | 123456789 | PR        | NA           | MDU         | I                    | BYOE               | Rejects   |
      | 123456789 | PR        | NA           | RC1         | C                    | BYOE               | Accepts   |
      | 123456789 | CH        | NA           | RC1         | I                    | Rent               | Accepts   |
      | 123456789 | CH        | NA           | RC1         | I                    | BYOE               | Rejects   |
      | 123456789 | CH        | NA           | RC1         | M                    | BYOE               | Accepts   |
      | 123456789 | CH        | NA           | RC1         | D                    | BYOE               | Accepts   |
      | 123456789 | PV        | NA           | RC1         | I                    | Rent               | Accepts   |
      | 123456789 | PV        | NA           | RC1         | I                    | Purchase           | Accepts   |
      | 123456789 | PV        | NA           | RC1         | I                    | BYOE               | Rejects   |
      | 123456789 | PV        | NA           | RC1         | C                    | BYOE               | Accepts   |
