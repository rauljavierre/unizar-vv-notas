package es.unizar.eina.notes;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NotesTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);
    private NotesDbAdapter mDbHelper;
    private List<Long> toDelete = new ArrayList<>();

    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());
    }

    @After
    public void tearDown() {
        for( long i : toDelete ) {
            mDbHelper.deleteNote(i);
        }
    }

    @Test
    public void testDeHumo() {
        int pre = mDbHelper.fetchAllNotes().getCount();
        toDelete.add(mDbHelper.createNote("Title","Body", 0, 86400000, DATABASE_DEFAULT_CATEGORY));
        int post = mDbHelper.fetchAllNotes().getCount();
        assertEquals(pre + 1, post);
    }

    @Test
    public void testMasExhaustivo(){
        long rowId = mDbHelper.createNote("Title","Body", 0, 86400000, DATABASE_DEFAULT_CATEGORY);
        toDelete.add(rowId);

        Cursor c = mDbHelper.fetchNote(rowId);
        String title = c.getString(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE));
        String body = c.getString(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY));
        long activationDate = c.getLong(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_ACTIVATION_DATE));
        long expirationDate = c.getLong(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_EXPIRATION_DATE));
        String category = c.getString(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_CATEGORY));

        assertEquals("Title", title);
        assertEquals("Body", body);
        assertEquals(0, activationDate);
        assertEquals(86400000, expirationDate);
        assertEquals(DATABASE_DEFAULT_CATEGORY, category);
    }
}