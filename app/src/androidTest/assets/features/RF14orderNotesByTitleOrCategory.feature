Feature: Order notes by title or category
  Organize the notes by its title or the category they are in

  # Order by title
  Scenario: 1
    Scenario Outline: Choose order by title
      Given I have a NotePadActivity
      # Create the two notes to order by title
      Given I have a note with "<firstTitle>" as title and "<body>" as body
      Given I have a note with "<secondTitle>" as title and "<body>" as body
      When I select order notes by title
      Then I should see the note with "<firstTitle>" as title first

    Examples:
      | firstTitle | secondTitle | body |
      | A          | B           | example body |

  # Order by categories
  Scenario: 2
    Scenario Outline: Choose order by categories
      Given I have a NotePadActivity
      Given I have a note with "<titleA>" as title and "<body>" as body
      Given I have a note with "<titleB>" as title and "<body>" as body
      Given I have a note with "<titleC>" as title and "<body>" as body
      # Create the category for one the notes and assign it
      Given I have a category with "<name>" as name and "food" as icon
      And I long press the note with "<titleB>" as title
      And I select edit note
      And I select the category with name "<name>"
      And I confirm
      When I select order notes by category
      Then I should see the note with "<titleB>" as title last

      Examples:
        | titleA | titleB | titleC | body | name |
        | A      | B      | C      | body | category |