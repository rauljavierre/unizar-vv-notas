Feature: Assign note to a category
  Execute  the assignation of a note to a category

  Scenario Outline:
    Given I have a NotePadActivity
    Given I have a note with "<title>" as title and "<body>" as body
    Given I have a category with "<name>" as name and "<iconText>" as icon
    And I long press the note with "<title>" as title
    And I select edit note
    And I select the category with name "<name>"
    And I confirm
    And I select list all categories
    And I long press the category with "<name>" as name
    And I select list all notes from the category
    Then I should see "<result>" on the list

    Examples:
      | title | body | name | iconText | result |
      | Title to assign 1 | Example body 1 | category to assign 1 | study | Title to assign 1 |
      | Title to assign 2 | Example body 2 | category to assign 2 | food | Title to assign 2 |