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
import java.util.ArrayList;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;

@RunWith(AndroidJUnit4.class)
public class CreateNoteTest {
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
    public void testCreateNoteValid(){
        // Prueba nota con titulo != null ^ titulo.length > 0 ^ body != null ^ category != null ^ activationDate >= 0 && expirationDate >= 0
        Long rowId = mDbHelper.createNote("Title", "Body", 0, 0, DATABASE_DEFAULT_CATEGORY);
        Assert.assertNotSame(-1, rowId);
        toDelete.add(rowId);
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
