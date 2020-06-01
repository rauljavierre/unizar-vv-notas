Feature: Send note through SMS
  Send a note through the SMS application

  Scenario Outline: Create a note, send the note
    Given I have a NotePadActivity
    Given I have a note with "<title>" as title and "<body>" as body
    When I long press the note with "<title>" as title
    And I select send note through SMS
    Then I should see the SMS application

    Examples:
    | title | body |
    | Title send SMS | body |