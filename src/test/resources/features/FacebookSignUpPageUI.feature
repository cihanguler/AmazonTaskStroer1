Feature: Facebook sign-up page

  Background:
    Given User is on the sign-up page
    Given ensure that the language is English
    Given the cookies are deleted


  Scenario: Verify that texts, the fields are available and editable
    Then "facebook" "create a new account" "It’s quick and easy." texts are visible
    And the "first name" field must be avaliable and editable
    And the "last name" field must be avaliable and editable
    And the "mobile number or email" field must be avaliable and editable
    When the user enter a valid email adress
    Then "re-enter email" field must be displayed
    And the "new password" field must be avaliable and editable
    And the "new password" field must be avaliable and editable
    And the "birthday" selection fields are avaliable and editable
    And birthday date is defined as today
    And "gender" fields "female" "male" and "custom" are avaliable and selectable
    When the user select "custom" as gender
    Then "select your pronoun" and "gender optional" fields are displayed
    When the user click on "select your pronoun" field
    Then "she" "he" and "they" options are available and selectable
    And "sign up button" is available and clickable
    And "already have an account" text is available and clickable
    When the user click on "already have an account" link
    Then the user should be navigated to facebook "login" page
    And the "learn more" "privacy policy" and "cookies policy" links are available
    When the user click on "learn more" link
    Then the user should be navigated to facebook "help center" "Information for people who don’t use Meta Products" page on an new tab
    When the user click on "privacy policy" link
    Then the user should be navigated to facebook "privacy center" "privacy policy" page on an new tab
    When the user click on "cookies policy" link
    Then the user should be navigated to facebook "privacy center" "cookies policy" page on an new tab
    And language options are available and extendable


  @password @signUp @all
  Scenario: Verify the "password" field warning and encryption
    When the user click on password text field
    Then the "password combination warning" must be displayed
    When the user type password on password field
    Then password text format must be encrypted

  @name @signUp @all
  Scenario Outline: Verify that user is allowed to use only allowed characters on the "name" field
    When the enter an invalid "<name>"
    Then the warning message is displayed
    Examples:
      | name           |
      | tester2        |
      | john*          |
      | smith007       |
      | johnsmith!     |
      | other examples |

  @surname @signUp @all
  Scenario Outline: Verify that user is allowed to use only allowed characters on the "surname" field
    When the enter an invalid "<surname>"
    Then the warning message is displayed
    Examples:
      | surname          |
      | tester-gmail.com |
      | tester@gnail.com |
      | tester@gmail     |
      | tester@gmail.con |
      | other examples   |

  @birthday @signUp @all
  Scenario: Verify the birthday dropdown selections
    When the user enter a birthday which is less than 5 years from today
    Then a warning must be displayed
    When the user click two times to sign up button after entering invalid birthday
    Then "age" field must be displayed
    When the user enter "string text" to age field?
    Then red exclamation mark must be displayed?

  @negativeSignUp @signUp @all @InvalidBirthday
  Scenario: Verify that user is not able to sign up without entering date of birth
    When the user enter valid "name" "surname" "mail/phone" "password"
    And the user does not select any date as birthday "day" "month" "year"
    And the user click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed

  @negativeSignUp @signUp @all @NoBirthday
  Scenario: Verify that user is not able to sign up without entering a valid date of birth
    When the user enter valid "name" "surname" "mail/phone" "password"
    And the user select a birthday which is less than 5 years from today as date "day" "month" "year"
    And the user click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed

  @negativeSignUp @signUp @all @NoGender
  Scenario: Verify that user is not able to sign up without entering gender
    When the user enter valid "name" "surname" "mail/phone" "password"
    And the user select valid "day" "month" "year"
    And the user does not click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed

  @negativeSignUp @signUp @all @NoName
  Scenario: Verify that user is not able to sign up without entering username
    When the user enter valid "surname" "mail/phone" "password" but no "name"
    And the user select valid "day" "month" "year"
    And the user click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed

  @negativeSignUp @signUp @all @NoSurname
  Scenario: Verify that user is not able to sign up without entering username
    When the user enter valid "name" "mail/phone" "password" but no "surname"
    And the user select valid "day" "month" "year"
    And the user click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed

  @negativeSignUp @signUp @all @InvalidPassword
  Scenario Outline: Verify that user is not able to sign up without entering a valid password
    When the user enter valid "name" "surname" "mail/phone" but invalid/no "<password>"
    And the user select valid "day" "month" "year"
    And the user click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed
    Examples:
      | password       |
      | Xx.-0          |
      | other examples |

  @mail/phone @onlyPhone @signUp @all
  Scenario Outline: Verify the "mail/phone" field with phone
    When the user enter valid "surname" "name" "password" and "<phone>"
    And the user select valid "day" "month" "year"
    And the user click on a type of "gender"
    Then user should be navigated to confirmation page
    When user click on logout button and confirm it
    Then user should be able to logout
    Examples:
      | phone          |
      | 0176415693xx   |
      | 053536385xx    |
      | other examples |

  @negativeSignUp @signUp @InvalidEmail
  Scenario Outline: Verify that user is not able to sign up without entering an invalid/no email
    When the user enter valid "surname" "name" "password" but an invalid/no "<mail/phone>"
    Then the re-enter email field is not displayed
    And the user select valid "day" "month" "year"
    And the user click on a type of "gender"
    Then the user should not be able to signup
    And a warning message should be displayed
    Examples:
      | mail/phone       |
      | tester-gmail.com |
      | tester@com       |
      | tester@gmail     |
      |                  |
      | other examples   |

  @validCredentials @signUp @positiveSignUp
  Scenario Outline: Verify that user is able to sign up with valid credentials
    When the user enter and select valid credentials "<name>" "<surname>" "<mail/phone>" "<password>" "<day>" "<month>" "<year>" "<gender>"
    Then the user should be able to sign up
    And credentials should match
    Examples:
      | mail/phone      | password       | name           | surname        | day            | month          | year           | gender         |
      | test1@gmail.com | Test.3764374!  | john           | smith          | 1              | 1              | 2017           | male           |
      | test2@gmail.com | Cc.20=         | johny          | deep           | 15             | 6              | 2000           | female         |
      | test3@gmail.com | Fc.20212021_   | muster         | mustermann     | 30             | 12             | 1905           | custom         |
      | other examples  | other examples | other examples | other examples | other examples | other examples | other examples | other examples |



