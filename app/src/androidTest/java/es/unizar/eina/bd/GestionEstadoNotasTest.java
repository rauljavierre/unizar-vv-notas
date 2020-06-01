package es.unizar.eina.bd;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Calendar;
import java.util.GregorianCalendar;
import es.unizar.eina.notes.Notes;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;
import static es.unizar.eina.bd.NotesDbAdapter.KEY_NOTE_ROWID;

@RunWith(AndroidJUnit4.class)
public class GestionEstadoNotasTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;
    private long rowId;

    private final Calendar UNO_ENERO_1997 = new GregorianCalendar(1997,0,1);
    private final Calendar UNO_ENERO_1998 = new GregorianCalendar(1998,0,1);
    private final Calendar UNO_ENERO_2000 = new GregorianCalendar(2000,0,1);
    private final Calendar UNO_ENERO_2002 = new GregorianCalendar(2002,0,1);
    private final Calendar UNO_ENERO_2003 = new GregorianCalendar(2003,0,1);
    private final Calendar UNO_ENERO_2004 = new GregorianCalendar(2004,0,1);
    private final Calendar UNO_ENERO_2005 = new GregorianCalendar(2005,0,1);

    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());

        rowId = mDbHelper.createNote("Title", "Body",
                                            UNO_ENERO_2000.getTimeInMillis(),
                                            UNO_ENERO_2002.getTimeInMillis(),
                                            DATABASE_DEFAULT_CATEGORY);
    }


    @After
    public void tearDown() {
        mDbHelper.disableTestingTime();
        mDbHelper.deleteNote(rowId);
    }

    @Test
    public void testFlechaRojaPrevistaAVigente(){
        // Hacemos que pase a prevista
        viajarAlUnoDeEneroDelN(1999);

        // Comprobamos que, efectivamente, es una nota prevista
        Cursor c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Ahora cambiamos el tiempo para que pase a vigente
        viajarAlUnoDeEneroDelN(2001);

        // Comprobamos que, efectivamente, ahora es una nota vigente
        c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaRojaVigenteACaducada(){
        // Hacemos que pase a vigente
        viajarAlUnoDeEneroDelN(2001);

        // Comprobamos que, efectivamente, es una nota vigente
        Cursor c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Ahora cambiamos el tiempo para que pase a caducada
        viajarAlUnoDeEneroDelN(2003);

        // Comprobamos que, efectivamente, ahora es una nota caducada
        c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaNegraPrevistaAVigente(){
        // Hacemos que pase a prevista
        viajarAlUnoDeEneroDelN(1999);

        // Comprobamos que, efectivamente, es una nota prevista
        Cursor c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota para que pase a vigente
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_1998.getTimeInMillis(), UNO_ENERO_2002.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, ahora es una nota vigente
        c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaNegraPrevistaACaducada(){
        // Hacemos que pase a prevista
        viajarAlUnoDeEneroDelN(1999);

        // Comprobamos que, efectivamente, es una nota prevista
        Cursor c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota para que pase a caducada
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_1997.getTimeInMillis(), UNO_ENERO_1998.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, ahora es una nota caducada
        c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaNegraVigenteAPrevista(){
        // Hacemos que pase a vigente
        viajarAlUnoDeEneroDelN(2001);

        // Comprobamos que, efectivamente, es una nota vigente
        Cursor c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota para que pase a prevista
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_2002.getTimeInMillis(), UNO_ENERO_2003.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, ahora es una nota prevista
        c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaNegraVigenteACaducada(){
        // Hacemos que pase a vigente
        viajarAlUnoDeEneroDelN(2001);

        // Comprobamos que, efectivamente, es una nota vigente
        Cursor c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota para que pase a caducada
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_1997.getTimeInMillis(), UNO_ENERO_1998.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, ahora es una nota caducada
        c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaNegraCaducadaAPrevista(){
        // Hacemos que pase a caducada
        viajarAlUnoDeEneroDelN(2003);

        // Comprobamos que, efectivamente, es una nota caducada
        Cursor c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota para que pase a prevista
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_2004.getTimeInMillis(), UNO_ENERO_2005.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, ahora es una nota prevista
        c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaNegraCaducadaAVigente(){
        // Hacemos que pase a caducada
        viajarAlUnoDeEneroDelN(2003);

        // Comprobamos que, efectivamente, es una nota caducada
        Cursor c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota para que pase a vigente
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_2002.getTimeInMillis(), UNO_ENERO_2004.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, ahora es una nota vigente
        c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaGrisPrevistaAPrevista(){
        // Hacemos que pase a prevista
        viajarAlUnoDeEneroDelN(1999);

        // Comprobamos que, efectivamente, es una nota prevista
        Cursor c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota y comprobamos que sigue siendo prevista
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_2002.getTimeInMillis(), UNO_ENERO_2004.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, sigue siendo una nota prevista
        c = mDbHelper.fetchExpectedNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaGrisVigenteAVigente(){
        // Hacemos que pase a vigente
        viajarAlUnoDeEneroDelN(2001);

        // Comprobamos que, efectivamente, es una nota vigente
        Cursor c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota y comprobamos que sigue siendo vigente
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_2000.getTimeInMillis(), UNO_ENERO_2004.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, sigue siendo una nota vigente
        c = mDbHelper.fetchCurrentNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    @Test
    public void testFlechaGrisCaducadaACaducada(){
        // Hacemos que pase a caducada
        viajarAlUnoDeEneroDelN(2003);

        // Comprobamos que, efectivamente, es una nota caducada
        Cursor c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));

        // Cambiamos la nota y comprobamos que sigue siendo caducada
        mDbHelper.updateNote(rowId, "Title", "Body", UNO_ENERO_1997.getTimeInMillis(), UNO_ENERO_1998.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, sigue siendo una nota caducada
        c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));
    }


    private boolean estaEnElListado(Cursor c) {
        while(c.moveToNext()){
            if (c.getLong(c.getColumnIndex(KEY_NOTE_ROWID)) == rowId) {
                return true;
            }
        }
        c.close();
        return false;
    }

    private void viajarAlUnoDeEneroDelN(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,0,1);
        mDbHelper.setTestingTime(calendar.getTimeInMillis());
    }
}