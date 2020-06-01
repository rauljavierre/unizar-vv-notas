Feature: Create a category
  Execute the creation of a category

  Scenario Outline: Choose create category, type name, choose icon, confirm
    Given I have a NotePadActivity
    When I select create category
    And I type "<name>" as name
    And I select the icon "<iconName>"
    And I confirm without scrolling
    And I select list all categories
    Then I should see the category "<result>" on the list

    Examples:
      | name | iconName | result |
      | category name 1 | study | category name 1 |
      | category name 2 | music | category name 2 |