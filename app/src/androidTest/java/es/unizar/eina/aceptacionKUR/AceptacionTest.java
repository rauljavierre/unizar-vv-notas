package es.unizar.eina.aceptacionKUR;

import android.database.Cursor;
import androidx.test.rule.ActivityTestRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;


// Se incluyen los 3 requisitos fundamentales (los verdaderos tests de aceptación se realizan con Cucumber
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AceptacionTest {

    private static final String TEST_NAME = "Aceptación:";
    private static final String TEST_TITLE_CREATED = TEST_NAME + " nota creada por un bot";
    private static final String TEST_TITLE_MODIFIED = TEST_NAME + " nota modificada por un bot";
    private static final String TEST_BODY_CREATED = "0111000101010001010110";
    private static final String TEST_BODY_MODIFIED = "Bip bop";

    @Rule
    public ActivityTestRule<Notes> mActivityRule = new ActivityTestRule<>(Notes.class);

    @Test
    public void testRF1_CrearNota() {
        // Hace clic en la opción de menú para insertar una nota (se asegura de que existe la opción)
        openActionBarOverflowOrOptionsMenu(mActivityRule.getActivity());
        onView(withText(R.string.menu_insert_note)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_note)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit
        onView(withId(R.id.title)).check(matches(notNullValue()));

        final String title = TEST_TITLE_CREATED;
        onView(withId(R.id.title)).perform(typeText(title), closeSoftKeyboard());

        final String body = TEST_BODY_CREATED;
        onView(withId(R.id.body)).perform(typeText(body), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withText(R.string.confirm)).perform(scrollTo());
        onView(withText(R.string.confirm)).perform(click());

        // Aserción: comprobación de que la nota se visualiza en el listado
        onView(withText(title)).check(matches(isDisplayed()));

        // Aserción: comprobación de que la nota está en la base de datos
        NotesDbAdapter db = NotesDbAdapter.getNotesDbAdapter(mActivityRule.getActivity().getApplicationContext());
        Cursor noteCursor = db.fetchNote(db.lastIdNotes);
        assertNoteEquals(title, body, noteCursor);
    }

    @Test
    public void testRF2_ModificarNota() {
        onView(withText(TEST_TITLE_CREATED)).perform(longClick());
        onView(withText(R.string.menu_edit_note)).perform(click());

        final String title = TEST_TITLE_MODIFIED;
        onView(withId(R.id.title)).perform(replaceText(title), closeSoftKeyboard());

        final String body = TEST_BODY_MODIFIED;
        onView(withId(R.id.body)).perform(replaceText(body), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withText(R.string.confirm)).perform(scrollTo());
        onView(withText(R.string.confirm)).perform(click());

        // Aserción: comprobación de que la nota se visualiza en el listado
        onView(withText(TEST_TITLE_MODIFIED)).check(matches(isDisplayed()));

        // Aserción: comprobación de que la nota está en la base de datos
        NotesDbAdapter db = NotesDbAdapter.getNotesDbAdapter(mActivityRule.getActivity().getApplicationContext());
        Cursor noteCursor = db.fetchNote(db.lastIdNotes);
        assertNoteEquals(title, body, noteCursor);
    }

    @Test
    public void testRF3_BorrarNota() {
        onView(withText(TEST_TITLE_MODIFIED)).perform(longClick());
        onView(withText(R.string.menu_delete_note)).perform(click());
    }


    private void assertNoteEquals(String title, String body, Cursor noteCursor) {
        assertThat(noteCursor.getCount(), is(greaterThanOrEqualTo(1)));
        assertThat(noteCursor.getString(noteCursor.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE)), is(equalTo(title)));
        assertThat(noteCursor.getString(noteCursor.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY)), is(equalTo(body)));
    }
}
