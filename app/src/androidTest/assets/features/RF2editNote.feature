Feature: Edit a note
  Execute the modification of a note

  Scenario Outline: Choose a note, edit the note information, confirm
    Given I have a NotePadActivity
    Given I have a note with "<oldTitle>" as title and "<body>" as body
    When I long press the note with "<oldTitle>" as title
    And I select edit note
    And I type "<newTitle>" as title
    And I confirm
    Then I should see "<result>" on the list
    Examples:
      | oldTitle | newTitle | result | body |
      | Example title 1 | Example new title 1 | Example new title 1 | body 1 |
      | Example title 2 | Example new title 2 | Example new title 2 | body 2 |