Feature: Delete a category with note assigned
  Execute the deletion of a category with a note assigned to it

  Scenario Outline: Choose list all categories, choose a category, delete the category
    Given I have a NotePadActivity
    Given I have a note with "<noteAssignedTitle>" as title and "<body>" as body
    Given I have a category with "<name>" as name and "<iconText>" as icon
    When I long press the note with "<noteAssignedTitle>" as title
    And I select edit note
    And I select the category with name "<name>"
    And I confirm
    Given I have a category with "<name>" as name and a note with title "<noteAssignedTitle>"
    And I long press the category with "<name>" as name
    And I select delete category
    Then I shouldn't see "<name>" on the list

    Examples:
      | name | noteAssignedTitle | iconText | body |
      | new category name assigned 1 | Title to assign 1 | music | body 1 |
      | new category name assigned 2 | Title to assign 2 | study | body 2 |