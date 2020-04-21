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
        rowIdToDelete = mDbHelper.createNote("test","test", 0, 0, "Ninguna");
        int post = mDbHelper.fetchAllNotes().getCount();
        assertEquals(pre + 1, post);
    }
}