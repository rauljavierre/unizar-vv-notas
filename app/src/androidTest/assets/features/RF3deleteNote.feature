Feature: Delete a note
  Execute the deletion of a note

  Scenario Outline: Choose a note, delete the note
    Given I have a NotePadActivity
    Given I have a note with "<title>" as title and "<body>" as body
    When I long press the note with "<title>" as title
    And I select delete note
    Then I shouldn't see "<title>" on the list

    Examples:
      | title | body |
      | Example new title 1 | body 1 |
      | Example new title 2 | body 2 |