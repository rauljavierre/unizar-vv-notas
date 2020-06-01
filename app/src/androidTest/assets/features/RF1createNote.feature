Feature: Create a note
    Execute the creation of a note

    Scenario Outline: Choose create note, type title, type body, confirm
        Given I have a NotePadActivity
        When I create a note
        And I type "<title>" as title
        And I type "<body>" as body
        And I confirm
        Then I should see "<result>" on the list

    Examples:
        | title | body | result |
        | Example title 1 | Example body 1 | Example title 1 |
        | Example title 2 | Example body 2 | Example title 2 |