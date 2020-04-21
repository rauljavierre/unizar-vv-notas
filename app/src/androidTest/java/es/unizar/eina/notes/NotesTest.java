package es.unizar.eina.notes;

import android.database.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import es.unizar.eina.bd.NotesDbAdapter;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NotesTest {
    @Rule
    public ActivityTestRule<Notes> activityTestRule
            = new ActivityTestRule<>(Notes.class);

    // Atributos
    private AppCompatActivity Notes;
    private long rowId;

    @Before
    public void setUp() {
        Notes = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        NotesDbAdapter mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
        mDbHelper.deleteNote(rowId);
    }

    @Test
    public void testCrearNota() {
        NotesDbAdapter mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
        Cursor cursor = mDbHelper.fetchAllNotes();
        int numNotasAntes = cursor.getCount();
        long fechaACtual = System.currentTimeMillis();
        rowId = mDbHelper.createNote("nota-Test","cuerpo-Test",fechaACtual,fechaACtual+9999,"categoria-Test");
        cursor = mDbHelper.fetchAllNotes();
        int numNotasDespues = cursor.getCount();
        assertEquals(numNotasAntes+1,numNotasDespues);
    }
}