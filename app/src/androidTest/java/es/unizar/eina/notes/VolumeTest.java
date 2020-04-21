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
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class VolumeTest {

    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);
    private AppCompatActivity Notes;
    private NotesDbAdapter mDbHelper;
    private List<Long> toDelete = new ArrayList<>();

    @Before
    public void setUp(){
        Notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(Notes.getApplicationContext());
    }

    @After
    public void tearDown() {
        for( long i : toDelete ) {
            mDbHelper.deleteNote(i);
        }
    }

    @Test
    public void testVolumen(){
        int[] volumenes = {50, 999, 1000, 1001, 10000};
        try{
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < volumenes[i]; j++){
                    toDelete.add(mDbHelper.createNote("Title","Body", 0, 0,"Ninguna"));
                }
            }
        }
        catch (Throwable t){
            fail();
        }
    }
}
