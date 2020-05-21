package es.unizar.eina.notes.test;

import androidx.test.rule.ActivityTestRule;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;

public class RunStepsDefinition {

    ActivityTestRule rule = new ActivityTestRule<>(Notes.class);

    @Before
    public void launchActivity() throws Exception {
        rule.launchActivity(null);
    }

    @After
    public void finishActivity() throws Exception {
        rule.getActivity().finish();
    }

    @Given("I have a NotePadActivity")
    public void I_have_a_NotePadActivity() {
        assertNotNull(rule.getActivity());
    }

    @When("I create a note")
    public void I_create_a_note() {
        // Hace clic en la opción de menú para insertar una nota
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_insert_note)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_note)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit
        onView(withId(R.id.title)).check(matches(notNullValue()));
    }

    @When("I type {string} as title")
    public void I_type_title_as_title(final String title) {
        onView(withId(R.id.title)).perform(replaceText(title), closeSoftKeyboard());
    }

    @When("I type {string} as body")
    public void I_type_subject_as_body(final String body) {
        onView(withId(R.id.body)).perform(replaceText(body), closeSoftKeyboard());
    }

    @When("I confirm")
    public void I_confirm() {
        onView(withId(R.id.confirm)).perform(click());
    }

    @Then("I should see {string} on the list")
    public void I_should_see_title_on_the_list(final String result) {
        // Aserción: comprobación de que la nota se visualiza en el listado
        onView(withText(result)).check(matches(isDisplayed()));
    }
}