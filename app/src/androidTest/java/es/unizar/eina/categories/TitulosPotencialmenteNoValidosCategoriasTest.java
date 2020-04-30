package es.unizar.eina.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;

@RunWith(Parameterized.class)
public class TitulosPotencialmenteNoValidosCategoriasTest {

    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;

    private String title;
    private int icon;
    private static String[] titulos =
            {
                    "a\u0000a",     "a\u0001a", "a\u0002a", "a\u0003a", "a\u0004a", "a\u0005a",
                    "a\u0006a",     "a\u0007a", "a\u0008a", "a\u0009a", "a\na",     "a\u000Ba",
                    "a\u000Ca",     "a\ra",     "a\u000Ea", "a\u000Fa", "a\0010a", "a\u0011a",
                    "a\u0012a",     "a\u0013a", "a\u0014a", "a\u0015a", "a\u0016a", "a\u0017a",
                    "a\u0018a",     "a\u0019a", "a\u001Aa", "a\u001Ba", "a\u001Ca", "a\u001Da",
                    "a\u001Ea",     "a\u001Fa", "a\u0020a", "a\u007Fa", "a\u0080a", "a\u0081a",
                    "a\u0082a",     "a\u0083a", "a\u0084a", "a\u0085a", "a\u0086a", "a\u0087a",
                    "a\u0088a",     "a\u0089a", "a\u008Aa", "a\u008Ba", "a\u008Ca", "a\u008Da",
                    "a\u008Ea",     "a\u008Fa", "a\u0090a", "a\u0091a", "a\u0092a", "a\u0093a",
                    "a\u0094a",     "a\u0095a", "a\u0096a", "a\u0097a", "a\u0098a", "a\u0099a",
                    "a\u009Aa",     "a\u009Ba", "a\u009Ca", "a\u009Da", "a\u009Ea", "a\u009Fa",
                    "a\u00A0a",     "a\u00ADa",

                    "\u0000",       "\u0001",   "\u0002",   "\u0003",   "\u0004",   "\u0005",
                    "\u0006",       "\u0007",   "\u0008",   "\u0009",   "\n",       "\u000B",
                    "\u000C",       "\r",       "\u000E",   "\u000F",   "\0010",    "\u0011",
                    "\u0012",       "\u0013",   "\u0014",   "\u0015",   "\u0016",   "\u0017",
                    "\u0018",       "\u0019",   "\u001A",   "\u001B",   "\u001C",   "\u001D",
                    "\u001E",       "\u001F",   "\u0020",   "\u007F",   "\u0080",   "\u0081",
                    "\u0082",       "\u0083",   "\u0084",   "\u0085",   "\u0086",   "\u0087",
                    "\u0088",       "\u0089",   "\u008A",   "\u008B",   "\u008C",   "\u008D",
                    "\u008E",       "\u008F",   "\u0090",   "\u0091",   "\u0092",   "\u0093",
                    "\u0094",       "\u0095",   "\u0096",   "\u0097",   "\u0098",   "\u0099",
                    "\u009A",       "\u009B",   "\u009C",   "\u009D",   "\u009E",   "\u009F",
                    "\u00A0",       "\u00AD"
            };

    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());
    }

    public TitulosPotencialmenteNoValidosCategoriasTest(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data(){
        List<Object[]> parametros = new ArrayList<>();
        for(int i = 0; i < titulos.length; i++){
            parametros.add(new Object[] {titulos[i], R.drawable.ic_local_dining_black_24dp});
        }

        return parametros;
    }

    @Test
    public void TitulosPotencialmenteNoValidosCategoriasTest(){
        Assert.assertNotEquals("Ninguna", mDbHelper.createCategory(title, icon));
    }
}
