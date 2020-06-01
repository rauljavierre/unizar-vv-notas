package es.unizar.eina.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;

@RunWith(Parameterized.class)
public class TitulosInvalidosCategoriasTest {

    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;

    private String title;
    private int icon;


    @Before
    public void setUp(){
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(activityRule.getActivity().getApplicationContext());
    }

    @After
    public void tearDown() {
        NotesDbAdapter.getNotesDbAdapter(activityRule.getActivity().getApplicationContext());
    }

    public TitulosInvalidosCategoriasTest(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data(){
        List<Object[]> parametros = new ArrayList<>();
        parametros.add(new Object[] {null, R.drawable.ic_local_dining_black_24dp});
        parametros.add(new Object[] {new String(), R.drawable.ic_local_dining_black_24dp});

        return parametros;
    }

    @Test
    public void TitulosInvalidosCategoriasTest(){
        Assert.assertEquals("Ninguna", mDbHelper.createCategory(title, icon));
    }
}
