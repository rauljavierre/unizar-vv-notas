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
public class TitulosValidosCategoriasTest {

    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;

    private String title;
    private int icon;


    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());
    }

    @After
    public void tearDown() {
        NotesDbAdapter.getNotesDbAdapter(activityRule.getActivity().getApplicationContext());
    }


    public TitulosValidosCategoriasTest(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data(){
        List<Object[]> parametros = new ArrayList<>();
        parametros.add(new Object[] {"+1234!@Va A fUnCiOnAr", R.drawable.ic_local_dining_black_24dp});

        return parametros;
    }

    @Test
    public void TitulosValidosCategoriasTest(){
        Assert.assertNotEquals("Ninguna", mDbHelper.createCategory(title, icon));
    }
}
