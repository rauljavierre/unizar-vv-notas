Feature: Send note through email
  Send a note through the email application

  Scenario Outline: Create a note, send the note
    Given I have a NotePadActivity
    Given I have a note with "<title>" as title and "<body>" as body
    When I long press the note with "<title>" as title
    And I select send note through email
    Then I should see the email application

    Examples:
      | title | body |
      | Title send email | body |