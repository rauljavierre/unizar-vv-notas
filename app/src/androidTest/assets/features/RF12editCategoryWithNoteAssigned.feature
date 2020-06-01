Feature: Edit a category with note assigned
  Execute the modification of a category with a note assigned to it

  Scenario Outline: Choose list all categories, choose a category, edit the category, confirm
    Given I have a NotePadActivity
    Given I have a note with "<noteAssignedTitle>" as title and "<body>" as body
    Given I have a category with "<oldName>" as name and "<iconText>" as icon
    When I long press the note with "<noteAssignedTitle>" as title
    And I select edit note
    And I select the category with name "<oldName>"
    And I confirm
    Given I have a category with "<oldName>" as name and a note with title "<noteAssignedTitle>"
    When I long press the category with "<oldName>" as name
    And I select edit category
    And I type "<newName>" as name
    And I confirm without scrolling
    Then I should see the category "<result>" on the list

    Examples:
      | oldName | newName | result | noteAssignedTitle | iconText | body |
      | category to assign 1 | new category name assigned 1 | new category name assigned 1 | Title to assign 1 | food | body 1 |
      | category to assign 2 | new category name assigned 2 | new category name assigned 2 | Title to assign 2 | study | body 2 |