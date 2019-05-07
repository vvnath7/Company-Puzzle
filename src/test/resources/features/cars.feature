Feature: Car selection feature
  As a customer, I want to select the type of car I want to sell,
  so that I can receive an offer from auto1.com

  @Scenario001
  Scenario: Fetch a list of Manufacturers
    Given customer retrieves a list with below inputs
      | apiName      | locale |
      | MANUFACTURER | Berlin |
    And verifies whether the response code is '200'
    Then verifies whether the following are available in the list
      | MANUFACTURER  |
      | Porsche       |
      | Honda         |
      | Chevrolet     |
      | Chrysler      |
      | Jaguar        |
      | Mercedes-Benz |
      | Tesla         |

  @Scenario002
  Scenario: Fetch a list of car types for a manufacturer
    Given customer retrieves a list with below inputs
      | apiName | locale | manufacturer |
      | CARTYPE | Berlin | 710          |
    And verifies whether the response code is '200'
    Then verifies whether the following are available in the list
      | CARTYPE                  |
      | '718 Boxster'            |
      | '918 Spyder'             |
      | Cayenne                  |
      | Panamera                 |
      | 'Panamera Sport Turismo' |

  @Scenario003
  Scenario: Negative scenario - Fetch a list of car types for an invalid manufacturer
    Given customer retrieves a list with below inputs
      | apiName | locale | manufacturer |
      | CARTYPE | Berlin | 110          |
    And verifies whether the response code is '404'

  @Scenario004
  Scenario: Negative Scenario-Fetch a list of car types without a manufacturer code
    Given customer retrieves a list with below inputs
      | apiName | locale |
      | CARTYPE | Berlin |
    And verifies whether the response code is '400'
    And validate whether the error message contains the following text
      | message                                                 |
      | Required String parameter 'manufacturer' is not present |

  @Scenario005
  Scenario: Fetch a list of built years for a specific car type under a manufacturer
    Given customer retrieves a list with below inputs
      | apiName   | locale | manufacturer | main-type   |
      | BUILTYEAR | Berlin | 710          | 718 Boxster |
    And verifies whether the response code is '200'
    Then verifies whether the following are available in the list
      | BUILTYEAR |
      | 2017      |
      | 2018      |

  @Scenario006
  Scenario: Negative Scenario - Fetch a list of built years with an invalid car type
    Given customer retrieves a list with below inputs
      | apiName   | locale | manufacturer | main-type   |
      | BUILTYEAR | Berlin | 710          | 775 Boxster |
    And verifies whether the response code is '400'

  @Scenario007
  Scenario: Negative Scenario - Fetch a list of built years without car type
    Given customer retrieves a list with below inputs
      | apiName   | locale | manufacturer |
      | BUILTYEAR | Berlin | 710          |
    And verifies whether the response code is '400'
    And validate whether the error message contains the following text
      | error                                                |
      | Required String parameter 'main-type' is not present |

  @Scenario008
  Scenario: Negative Scenario - Fetch a list of built years with an invalid manufacturer code
    Given customer retrieves a list with below inputs
      | apiName   | locale | manufacturer | main-type |
      | BUILTYEAR | Berlin | 110          | Boxster   |
    And verifies whether the response code is '400'

  @Scenario009
  Scenario: Negative Scenario - Fetch a list of built year without a manufacturer code
    Given customer retrieves a list with below inputs
      | apiName   | locale | main-type |
      | BUILTYEAR | Berlin | Boxster   |
    And verifies whether the response code is '400'
    And validate whether the error message contains the following text
      | message                                                 |
      | Required String parameter 'manufacturer' is not present |