package es.unizar.eina.notes;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;

public class OverloadTest {
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

    @Ignore("It's not a regression test")
    @Test(expected = Throwable.class)
    public void testOverloadBody(){
        String body = "F";
        int numCaracteres = 1;
        try{
            Long rowID = mDbHelper.createNote("Title", body, 0, 0, DATABASE_DEFAULT_CATEGORY);
            while(rowID != -1){
                toDelete.add(rowID);

                body += body;
                numCaracteres += numCaracteres;
                rowID = mDbHelper.createNote("Title", body, 0, 0, DATABASE_DEFAULT_CATEGORY);
            }
        }
        catch (Throwable t){
            Log.d("Maximum number of characters testOverloadBody", String.valueOf(numCaracteres));
            throw t;
        }
    }
}
