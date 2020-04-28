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
import es.unizar.eina.notes.Notes;
import static es.unizar.eina.bd.NotesDbAdapter.DATABASE_DEFAULT_CATEGORY;
import static es.unizar.eina.bd.NotesDbAdapter.KEY_NOTE_ROWID;

@RunWith(AndroidJUnit4.class)
public class NotesDbAdapterTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;
    private long rowId;

    private Calendar unoEnero1997;
    private Calendar unoEnero1998;
    private Calendar unoEnero1999;
    private Calendar unoEnero2000;
    private Calendar unoEnero2001;
    private Calendar unoEnero2002;
    private Calendar unoEnero2003;
    private Calendar unoEnero2004;
    private Calendar unoEnero2005;

    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());

        inicializarVariablesCalendario();

        rowId = mDbHelper.createNote("Title", "Body",
                                            unoEnero2000.getTimeInMillis(),
                                            unoEnero2002.getTimeInMillis(),
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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero1998.getTimeInMillis(), unoEnero2002.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero1997.getTimeInMillis(), unoEnero1998.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero2002.getTimeInMillis(), unoEnero2003.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero1997.getTimeInMillis(), unoEnero1998.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero2004.getTimeInMillis(), unoEnero2005.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero2002.getTimeInMillis(), unoEnero2004.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero2002.getTimeInMillis(), unoEnero2004.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero2000.getTimeInMillis(), unoEnero2004.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

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
        mDbHelper.updateNote(rowId, "Title", "Body", unoEnero1997.getTimeInMillis(), unoEnero1998.getTimeInMillis(), DATABASE_DEFAULT_CATEGORY);

        // Comprobamos que, efectivamente, sigue siendo una nota caducada
        c = mDbHelper.fetchExpiredNotes();
        Assert.assertTrue(estaEnElListado(c));
    }

    private void inicializarVariablesCalendario() {
        unoEnero1997 = Calendar.getInstance();
        unoEnero1997.set(1997,0,1);

        unoEnero1998 = Calendar.getInstance();
        unoEnero1998.set(1998,0,1);

        unoEnero1999 = Calendar.getInstance();
        unoEnero1999.set(1999,0,1);

        unoEnero2000 = Calendar.getInstance();
        unoEnero2000.set(2000,0,1);

        unoEnero2001 = Calendar.getInstance();
        unoEnero2001.set(2001,0,1);

        unoEnero2002 = Calendar.getInstance();
        unoEnero2002.set(2002,0,1);

        unoEnero2003 = Calendar.getInstance();
        unoEnero2003.set(2003,0,1);

        unoEnero2004 = Calendar.getInstance();
        unoEnero2004.set(2004,0,1);

        unoEnero2005 = Calendar.getInstance();
        unoEnero2005.set(2005,0,1);
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