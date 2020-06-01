Feature: Show notes by dates
  Show only the notes related to the conditions of its dates

  # Show planned notes
  Scenario: 1
    Scenario Outline: Create a planned note, choose show planned notes
      Given I have a NotePadActivity
      Given I have a planned note with "<title>" as title and "<body>" as body
      When I select show planned notes
      Then I should see the note with "<title>" as title first

      Examples:
        | title | body |
        | title planned note | body |

  # Show current notes
  Scenario: 2
  Scenario Outline: Create a current note, choose show current notes
    Given I have a NotePadActivity
    Given I have a current note with "<title>" as title and "<body>" as body
    When I select show current notes
    Then I should see the note with "<title>" as title first

    Examples:
      | title | body |
      | title current note | body |

  # Show expired notes
  Scenario: 3
  Scenario Outline: Create a expired note, choose show expired notes
    Given I have a NotePadActivity
    Given I have a expired note with "<title>" as title and "<body>" as body
    When I select show expired notes
    Then I should see the note with "<title>" as title first

    Examples:
      | title | body |
      | title expired note | body |