package es.unizar.eina.notes;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;


@RunWith(AndroidJUnit4.class)
public class DeleteNoteTest {
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
    public void testDeleteNoteValid(){
        // Creamos una nota para posteriormente eliminarla
        long rowId = mDbHelper.createNote("Title", "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
        assertTrue(mDbHelper.deleteNote(rowId));
    }

    @Test
    public void testDeleteNoteRowIdGreaterThanNumberOfNotes(){
        // Creamos una nota para posteriormente eliminarla
        long rowId = mDbHelper.createNote("Title", "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
        assertFalse(mDbHelper.deleteNote(rowId + 1));
        toDelete.add(rowId);
    }

    @Test
    public void testDeleteNoteRowIdEqualsZero(){
        assertFalse(mDbHelper.deleteNote(0));
    }

}
