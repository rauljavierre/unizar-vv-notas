package es.unizar.eina.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;

@RunWith(Parameterized.class)
public class TitulosInvalidosNotasTest {

    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;

    private String title;
    private String body;
    private long activation;
    private long expiration;
    private String category;


    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());
    }

    public TitulosInvalidosNotasTest(String title, String body, long activation, long expiration, String category){
        this.title = title;
        this.body = body;
        this.activation = activation;
        this.expiration = expiration;
        this.category = category;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data(){
        List<Object[]> parametros = new ArrayList<>();
        parametros.add(new Object[] {null, "body", 0, 0, NotesDbAdapter.DATABASE_DEFAULT_CATEGORY});
        parametros.add(new Object[] {new String(), "body", 0, 0, NotesDbAdapter.DATABASE_DEFAULT_CATEGORY});

        return parametros;
    }

    @Test(expected = IllegalArgumentException.class)
    public void TitulosNotasTest(){
        mDbHelper.createNote(title, body, activation, expiration, category);
    }
}
