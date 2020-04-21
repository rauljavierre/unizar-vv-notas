package es.unizar.eina.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.unizar.eina.bd.NotesDbAdapter;

import static org.junit.Assert.assertTrue;

public class OverloadTest {
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

    // Se fuerza que falle para que muestre el número de caracteres que soporta un body de una nota
    @Test
    public void testSobrecargaBody(){
        String body = "f";
        int numCaracteres = 1;
        try{
            Long rowID = mDbHelper.createNote("Title", body, 0, 0,"Ninguna");
            while(rowID != -1){
                toDelete.add(rowID);

                body += body;
                numCaracteres += numCaracteres;
                rowID = mDbHelper.createNote("Title", body, 0, 0,"Ninguna");
            }
        }
        catch (Throwable t){
            assertTrue("Tamaño máximo: " + numCaracteres, false);
            throw t;
        }
    }
}
