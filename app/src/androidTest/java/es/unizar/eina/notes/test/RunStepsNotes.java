package es.unizar.eina.notes.test;

import android.app.Activity;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import androidx.test.rule.ActivityTestRule;

import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import androidx.test.uiautomator.UiDevice;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

public class RunStepsNotes {
    ActivityTestRule rule = new ActivityTestRule<>(Notes.class);
    NotesDbAdapter db;

    private final Calendar UNO_ENERO_1997 = new GregorianCalendar(1997,0,1);
    private final Calendar UNO_ENERO_1998 = new GregorianCalendar(1998,0,1);
    private final Calendar UNO_ENERO_2000 = new GregorianCalendar(2000,0,1);
    private final Calendar UNO_ENERO_2002 = new GregorianCalendar(2002,0,1);
    private final Calendar UNO_ENERO_2003 = new GregorianCalendar(2003,0,1);
    private final Calendar UNO_ENERO_2004 = new GregorianCalendar(2004,0,1);
    private final Calendar UNO_ENERO_2005 = new GregorianCalendar(2005,0,1);


    private void viajarAlUnoDeEneroDelN(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,0,1);
        db.setTestingTime(calendar.getTimeInMillis());
    }

    private Activity getCurrentActivity(){
        final Activity[] currentActivity = {null};
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity activity: resumedActivities){
                    currentActivity[0] = activity;
                    break;
                }
            }
        });
        return currentActivity[0];
    }

    @Before
    public void launchActivity() throws Exception {
        rule.launchActivity(null);
        db = NotesDbAdapter.getNotesDbAdapter(rule.getActivity().getApplicationContext());
        viajarAlUnoDeEneroDelN(2000);
    }

    @After
    public void finishActivity() throws Exception {
        db.resetDatabase();
        rule.getActivity().finish();
    }

    @Given("I have a NotePadActivity")
    public void I_have_a_NotePadActivity() {
        assertNotNull(rule.getActivity());
    }

    // NOTAS

    @When("I create a note")
    public void I_create_a_note() {
        // Hace clic en la opción de menú para insertar una nota
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_insert_note)).check(
                matches(notNullValue()));
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
        onView(withText(R.string.confirm)).perform(scrollTo());
        onView(withId(R.id.confirm)).perform(click());
    }

    @Then("I should see {string} on the list")
    public void I_should_see_title_on_the_list(final String result) {
// Aserción: comprobación de que la nota se visualiza en el listado
        onView(withText(result)).check(matches(isDisplayed()));
    }

    @Given("I have a note with {string} as title")
    public void iHaveANoteWithAsTitle(final String title) {
        onView(withText(title)).check(matches(isDisplayed()));
    }

    @When("I long press the note with {string} as title")
    public void iLongPressTheNoteWithAsTitle(final String title) {
        onView(withText(title)).perform(longClick());
    }

    @And("I select edit note")
    public void iSelectEditNote() {
        onView(withText(R.string.menu_edit_note)).perform(click());
    }

    @And("I select delete note")
    public void iSelectDeleteNote() {
        onView(withText(R.string.menu_delete_note)).perform(click());
    }

    @Then("I shouldn't see {string} on the list")
    public void iShouldnTSeeOnTheList(String result) {
        onView(withText(result)).check(doesNotExist());
    }

    // CATEGORIAS

    @When("I select create category")
    public void iSelectCreateCategory() {
        // Hace clic en la opción de menú para insertar una categoría
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_insert_category)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_insert_category)).perform(click());
    }

    @And("I type {string} as name")
    public void iTypeAsName(final String name) {
        onView(withId(R.id.name)).perform(replaceText(name), closeSoftKeyboard());
    }

    @And("I select the icon {string}")
    public void iSelectTheIcon(final String iconName) {
        String textoSpinner = null;
        switch (iconName) {
            case "study":
                textoSpinner = "Estudio";
                break;
            case "food":
                textoSpinner = "Comida";
                break;
            case "music":
                textoSpinner = "Música";
                break;
            default:
                fail();
        }
        onView(withId(R.id.spinnerImages)).perform(click());
        onView(allOf(withId(R.id.categoryText), withText(textoSpinner))).perform(click());
        onView(withId(R.id.categoryText)).check(matches(withText(textoSpinner)));
    }

    @Then("I should see the category {string} on the list")
    public void iShouldSeeTheCategoryOnTheList(final String result) {
        onView(withText(result)).check(matches(isDisplayed()));
    }

    @And("I confirm without scrolling")
    public void iConfirmWithoutScrolling() {
        onView(withId(R.id.confirm)).perform(click());
    }

    @When("I select list all categories")
    public void iSelectListAllCategories() {
        // Hace clic en la opción de menú para insertar una categoría
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_mostrar_categorias)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_mostrar_categorias)).perform(click());
    }

    @And("I long press the category with {string} as name")
    public void iLongPressTheCategoryWithAsName(final String name) {
        onView(withText(name)).perform(longClick());
    }

    @And("I select edit category")
    public void iSelectEditCategory() {
        onView(withText(R.string.menu_edit_category)).perform(click());
    }

    @And("I select delete category")
    public void iSelectDeleteCategory() {
        onView(withText(R.string.menu_delete_category)).perform(click());
    }

    // ASIGNAR NOTAS A CATEGORÍAS

    @Given("I have a note with {string} as title and {string} as body")
    public void iHaveANoteWithAsTitleAndAsBody(final String title, final String body) {
        I_create_a_note();
        I_type_title_as_title(title);
        I_type_subject_as_body(body);
        I_confirm();
    }

    @Given("I have a category with {string} as name and {string} as icon")
    public void iHaveACategoryWithAsNameAndAsIcon(final String name, final String icon) {
        iSelectCreateCategory();
        iTypeAsName(name);
        iSelectTheIcon(icon);
        iConfirmWithoutScrolling();
    }

    @And("I select the category with name {string}")
    public void iSelectTheCategoryWithName(String categoryName) {
        onView(withId(R.id.spinner_categories)).perform(click());
        onView(withText(categoryName)).perform(click());
        onView(withText(categoryName)).check(matches(isDisplayed()));
    }

    @And("I select list all notes from the category")
    public void iSelectListAllNotesFromTheCategory() {
        onView(withText(R.string.menu_show_notes)).perform(click());
    }

    @Given("I have a category with {string} as name and a note with title {string}")
    public void iHaveACategoryWithAsNameAndANoteWithTitle(final String name, final String title) {
        iSelectListAllCategories();
        iLongPressTheCategoryWithAsName(name);
        iSelectListAllNotesFromTheCategory();
        I_should_see_title_on_the_list(title);
        pressBack();
    }

    // ORDENAR NOTAS

    @When("I select order notes by title")
    public void iSelectOrderNotesByTitle() {
        // Hace clic en la opción de menú para ordenar las notas
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_ordenar_por_notas)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_ordenar_por_notas)).perform(click());
    }

    @Then("I should see the note with {string} as title first")
    public void iShouldSeeTheNoteWithAsTitleFirst(final String firstTitle) {
        onData(anything()).inAdapterView(withId(R.id.list)).atPosition(0).check(matches(withText(firstTitle)));
    }

    @When("I select order notes by category")
    public void iSelectOrderNotesByCategory() {
        // Hace clic en la opción de menú para ordenar las notas
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_ordenar_por_categorias)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_ordenar_por_categorias)).perform(click());
    }

    @Then("I should see the note with {string} as title last")
    public void iShouldSeeTheNoteWithAsTitleThird(final String lastTitle) {
        onData(anything()).inAdapterView(withId(R.id.list)).atPosition(2).check(matches(withText(lastTitle)));
    }

    // MOSTRAR POR FECHAS

    @When("I select show planned notes")
    public void iSelectShowPlannedNotes() {
        // Hace clic en la opción de menú para mostrar notas previstas
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_ver_previstas)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_ver_previstas)).perform(click());
    }

    @Given("I have a planned note with {string} as title and {string} as body")
    public void iHaveAPlannedNoteWithAsTitleAndAsBody(final String title, final String body) {
        db.createNote(title, body,
                UNO_ENERO_2002.getTimeInMillis(),
                UNO_ENERO_2003.getTimeInMillis(),
                DATABASE_DEFAULT_CATEGORY);
    }

    @Given("I have a current note with {string} as title and {string} as body")
    public void iHaveACurrentNoteWithAsTitleAndAsBody(final String title, final String body) {
        db.createNote(title, body,
                UNO_ENERO_2000.getTimeInMillis(),
                UNO_ENERO_2003.getTimeInMillis(),
                DATABASE_DEFAULT_CATEGORY);
    }

    @When("I select show current notes")
    public void iSelectShowCurrentNotes() {
        // Hace clic en la opción de menú para mostrar notas vigentes
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_ver_vigentes)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_ver_vigentes)).perform(click());
    }

    @Given("I have a expired note with {string} as title and {string} as body")
    public void iHaveAExpiredNoteWithAsTitleAndAsBody(final String title, final String body) {
        db.createNote(title, body,
                UNO_ENERO_1997.getTimeInMillis(),
                UNO_ENERO_1998.getTimeInMillis(),
                DATABASE_DEFAULT_CATEGORY);
    }

    @When("I select show expired notes")
    public void iSelectShowExpiredNotes() {
        // Hace clic en la opción de menú para mostrar notas caducadas
        openActionBarOverflowOrOptionsMenu(rule.getActivity());
        onView(withText(R.string.menu_ver_caducadas)).check(
                matches(notNullValue()));
        onView(withText(R.string.menu_ver_caducadas)).perform(click());
    }

    // ENVIAR NOTAS

    @And("I select send note through SMS")
    public void iSelectSendNoteThroughSMS() {
        onView(withText(R.string.send_SMS)).perform(click());
    }

    @Then("I should see the SMS application")
    public void iShouldSeeTheSMSApplication() throws InterruptedException {
        Activity current = getCurrentActivity();
        assertNotSame(current, rule.getActivity());
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressBack();
    }

    @And("I select send note through email")
    public void iSelectSendNoteThroughEmail() {
        onView(withText(R.string.send_email)).perform(click());
    }

    @Then("I should see the email application")
    public void iShouldSeeTheEmailApplication() throws InterruptedException {
        Activity current = getCurrentActivity();
        assertNotSame(current, rule.getActivity());
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressBack();
    }
}
