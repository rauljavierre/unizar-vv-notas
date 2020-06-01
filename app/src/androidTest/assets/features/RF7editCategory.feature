Feature: Edit a category
  Execute the modification of a category

  Scenario Outline: Choose list all categories, choose a category, edit the category, confirm
    Given I have a NotePadActivity
    Given I have a category with "<oldName>" as name and "<iconText>" as icon
    When I select list all categories
    And I long press the category with "<oldName>" as name
    And I select edit category
    And I type "<newName>" as name
    And I confirm without scrolling
    Then I should see the category "<result>" on the list

    Examples:
      | oldName | newName | result | iconText |
      | category name 1 | new category name 1 | new category name 1 | food |
      | category name 2 | new category name 2 | new category name 2 | music |