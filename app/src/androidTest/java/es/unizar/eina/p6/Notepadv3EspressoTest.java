package es.unizar.eina.p6;

import android.database.Cursor;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
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

public class Notepadv3EspressoTest {
    private static final String TEST_NAME="Espresso";
    private static final String TEST_TITLE= TEST_NAME + " Note Title";
    private static final String TEST_BODY= TEST_NAME + " Note Body";

    private static final int TEST_NUMBER_OF_NOTES = 4;

    @Rule
    public ActivityTestRule<Notes> mActivityRule = new ActivityTestRule<>(Notes.class);

    @Test
    public void testAddNotes() {
        for (int i = 0; i < TEST_NUMBER_OF_NOTES; i++) {
            // Hace clic en la opción de menú para insertar una nota (se asegura de que existe la opción)
            openActionBarOverflowOrOptionsMenu(mActivityRule.getActivity());
            onView(withText(R.string.menu_insert_note)).check(matches(notNullValue()));
            onView(withText(R.string.menu_insert_note)).perform(click());

            // Se asegura de que la actividad actual es NoteEdit
            onView(withId(R.id.title)).check(matches(notNullValue()));

            // En el título inserta "Espresso Note Title <i>"
            final String title = TEST_TITLE + " " + i;
            onView(withId(R.id.title)).perform(typeText(title), closeSoftKeyboard());

            // En el cuerpo inserta "Espresso Note Body <i>"
            final String body = TEST_BODY + " " + i;
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
    }

    @After
    public void borrarNotas() {
        for (int i = 0; i < TEST_NUMBER_OF_NOTES; i++) {
            // Busca y borra la nota con titulo "Espressso Note Title <i>"
            onView(withText(TEST_TITLE + " " + i)).perform(longClick());
            onView(withText(R.string.menu_delete_note)).perform(click());
        }
    }

    private void assertNoteEquals(String title, String body, Cursor noteCursor) {
        assertThat(noteCursor.getCount(), is(greaterThanOrEqualTo(1)));
        assertThat(noteCursor.getString(noteCursor.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE)), is(equalTo(title)));
        assertThat(noteCursor.getString(noteCursor.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY)), is(equalTo(body)));
    }
}
