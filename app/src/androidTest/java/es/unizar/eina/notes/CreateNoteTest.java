package es.unizar.eina.notes;

import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.bd.NotesDbAdapter;

import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CreateNoteTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;
    private long idCreatedNote = -1;

    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());
    }

    @After
    public void tearDown() {
        mDbHelper.deleteNote(idCreatedNote);
    }

    @Test
    public void testCreateNoteValid(){
        // Prueba nota con titulo != null ^ titulo.length > 0 ^ body != null ^ category != null ^ activationDate >= 0 && expirationDate >= 0
        idCreatedNote = mDbHelper.createNote("Title", "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
        Assert.assertNotEquals(-1, idCreatedNote);
        assertNote(idCreatedNote, "Title", "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
    }

    private void assertNote(long rowID, String expectedTitle, String expectedBody,
                            long expectedActivationDate, long expectedExpirationDate,
                            String expectedCategory ) {
        Cursor note = mDbHelper.fetchNote(rowID);
        String actualTitle = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE));
        String actualBody = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY));
        String actualCategory = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_CATEGORY));
        long actualActivationDate = note.getLong(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_ACTIVATION_DATE));
        long actualExpirationDate = note.getLong(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_EXPIRATION_DATE));
        note.close();
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedCategory, actualCategory);
        assertEquals(expectedActivationDate, actualActivationDate);
        assertEquals(expectedExpirationDate, actualExpirationDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidTitleNull(){
        mDbHelper.createNote(null, "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidTitleEmpty(){
        mDbHelper.createNote("", "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidBodyNull(){
        mDbHelper.createNote("Title", null, 0, 0, DATABASE_DEFAULT_CATEGORY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidActivationDateLessThanZero(){
        mDbHelper.createNote("Title", "Body", -1, 0, DATABASE_DEFAULT_CATEGORY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidExpirationDateLessThanZero(){
        mDbHelper.createNote("Title", "Body", 0, -1, DATABASE_DEFAULT_CATEGORY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidCategoryNull(){
        mDbHelper.createNote("Title", "Body", 0, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoteNotValidActivationDateGreaterThanExpirationDate(){
        mDbHelper.createNote("Title", "Body", 1, 0, DATABASE_DEFAULT_CATEGORY);
    }
}
