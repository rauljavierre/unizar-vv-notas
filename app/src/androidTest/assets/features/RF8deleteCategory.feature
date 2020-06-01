Feature: Delete a category
  Execute the deletion of a category

  Scenario Outline: Choose list all categories, choose a category, delete the category
    Given I have a NotePadActivity
    Given I have a category with "<name>" as name and "<iconText>" as icon
    When I select list all categories
    And I long press the category with "<name>" as name
    And I select delete category
    Then I shouldn't see "<name>" on the list

    Examples:
      | name | iconText |
      | new category name 1 | study |
      | new category name 2 | music |