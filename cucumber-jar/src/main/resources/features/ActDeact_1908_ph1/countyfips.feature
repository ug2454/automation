Feature: CountyFIPS exists under DTVTECH component, is a numeric value and of character length 5

  Background: Initialization steps
    Given Set suite.order.series.dtv to 9981

  Scenario Outline: fips Code validation when present
    Given for a <dtvBan>
    And <orderType>-<orderSubtype>, <productType> order was received by BBNMS
    When countyFips attribute is present under DTT component on provisionTransport request
    And attribute value is <fipsValue>
    Then BBNMS <omsAction> the PT for the order

    Examples: 
      | dtvBan    | orderType | orderSubtype | productType | fipsValue | omsAction |
      | 123456789 | PR        | NA           | RC1         | ABCDE     | Rejects   |
      | 123456789 | PR        | NA           | RC1         |    123456 | Rejects   |
      | 123456789 | PR        | NA           | NFFL        |      1234 | Rejects   |
      | 123456789 | PR        | NA           | MDU         | A1C2E     | Rejects   |
      | 123456789 | PR        | NA           | RC1         | null      | Rejects   |
      | 123456789 | PR        | NA           | RC1         |     12345 | Accepts   |
      | 123456789 | CH        | NA           | RC1         | ABCDE     | Rejects   |
      | 123456789 | CH        | NA           | RC1         |     12345 | Accepts   |
      | 123456789 | CH        | NA           | MDU         |      1234 | Rejects   |
      | 123456789 | PV        | NA           | RC1         | ABCDE     | Rejects   |
      | 123456789 | PV        | NA           | RC1         |     12345 | Accepts   |

  Scenario Outline: fips Code validation when not present
    Given for a <dtvBan>
    And <orderType>-<orderSubtype>, <productType> order was received by BBNMS
    When countyFips attribute is not present under DTT component on provisionTransport request
    Then BBNMS <omsAction> the PT for the order

    Examples: 
      | dtvBan    | orderType | orderSubtype | productType | omsAction |
      | 123456789 | CH        | NA           | MDU         | Rejects   |
      | 123456789 | PV        | NA           | RC1         | Rejects   |
      | 123456789 | PR        | NA           | RC1         | Rejects   |
