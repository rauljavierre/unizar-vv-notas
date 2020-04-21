package es.unizar.eina.notes;

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
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class UpdateNoteTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);
    private AppCompatActivity Notes;
    private NotesDbAdapter mDbHelper;
    private long rowId;

    @Before
    public void setUp(){
        Notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
        rowId = mDbHelper.createNote("Title", "Body", 0, 0, "Ninguna");
    }

    @After
    public void tearDown() {
        mDbHelper.deleteNote(rowId);
    }

    @Test
    public void testUpdateNoteValid(){
        Assert.assertTrue(mDbHelper.updateNote(rowId, "Title2", "Body2", 1, 1, "Ninguna"));
    }

    @Test
    public void testUpdateNoteNotValidTitleNull(){
        assertFalse(mDbHelper.updateNote(rowId,null, "Body", 0, 0, "Ninguna"));
    }

    @Test
    public void testUpdateNoteNotValidTitleEmpty(){
        assertFalse(mDbHelper.updateNote(rowId,"", "Body", 0, 0, "Ninguna"));
    }

    @Test
    public void testUpdateNoteNotValidBodyNull(){
        assertFalse(mDbHelper.updateNote(rowId,"Title", null, 0, 0, "Ninguna"));
    }

    @Test
    public void testUpdateNoteNotValidActivationDateLessThanZero(){
        assertFalse(mDbHelper.updateNote(rowId,"Title", "Body", -1, 0, "Ninguna"));
    }

    @Test
    public void testUpdateNoteNotValidExpirationDateLessThanZero(){
        assertFalse(mDbHelper.updateNote(rowId,"Title", "Body", 0, -1, "Ninguna"));
    }

    @Test
    public void testUpdateNoteNotValidCategoryNull(){
        assertFalse(mDbHelper.updateNote(rowId,"Title", "Body", 0, 0, null));
    }

    @Test
    public void testUpdateNoteNotValidActivationDateGreaterThanExpirationDate(){
        assertFalse(mDbHelper.updateNote(rowId,"Title", "Body", 1, 0, "Ninguna"));
    }
}
