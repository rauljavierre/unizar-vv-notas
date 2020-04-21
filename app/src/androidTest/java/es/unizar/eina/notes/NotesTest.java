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

import es.unizar.eina.bd.NotesDbAdapter;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NotesTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);
    AppCompatActivity Notes;

    long rowIdToDelete;


    @Before
    public void setUp(){
        Notes = activityRule.getActivity();
    }

    @After
    public void tearDown() {
        NotesDbAdapter mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
        mDbHelper.deleteNote(rowIdToDelete);
    }

    @Test
    public void testHumo() {
        NotesDbAdapter mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
        int pre = mDbHelper.fetchAllNotes().getCount();
        rowIdToDelete = mDbHelper.createNote("Title","Body", 0, 86400000, "Ninguna");
        int post = mDbHelper.fetchAllNotes().getCount();
        assertEquals(pre + 1, post);
    }

    @Test
    public void testMasExhaustivo(){
        NotesDbAdapter mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
        long rowId = mDbHelper.createNote("Title","Body", 0, 86400000, "Ninguna");
        Cursor c = mDbHelper.fetchNote(rowId);
        String title = c.getString(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE));
        String body = c.getString(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY));
        long activationDate = c.getLong(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_ACTIVATION_DATE));
        long expirationDate = c.getLong(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_EXPIRATION_DATE));
        String category = c.getString(c.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_CATEGORY));
        if(title.equals("Title") && body.equals("Body") && activationDate == 0 && expirationDate == 86400 && category.equals("Ninguna")){
            assertTrue(true);
        }
        else{
            fail();
        }
    }
}