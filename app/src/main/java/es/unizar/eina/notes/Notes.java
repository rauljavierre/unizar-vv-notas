package es.unizar.eina.notes;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.categories.Categories;
import es.unizar.eina.categories.CategoryEdit;
import es.unizar.eina.send.SendAbstractionImpl;
import es.unizar.eina.test.Test;

public class Notes extends AppCompatActivity {

    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int INSERT_NOTE_ID = Menu.FIRST;
    private static final int DELETE_NOTE_ID = Menu.FIRST + 1;
    private static final int EDIT_NOTE_ID = Menu.FIRST + 2;
    private static final int INSERT_CATEGORY_ID = Menu.FIRST + 3;
    private static final int SEND_EMAIL_ID = Menu.FIRST + 4;
    private static final int SEND_SMS_ID = Menu.FIRST + 5;
    private static final int ORDENAR_POR_NOTAS = Menu.FIRST + 6;
    private static final int ORDENAR_POR_CATEGORIAS = Menu.FIRST + 7;
    private static final int VER_CATEGORIAS = Menu.FIRST + 8;
    private static final int VER_NOTAS = Menu.FIRST + 9;
    private static final int VER_NOTAS_PREVISTAS = Menu.FIRST + 10;
    private static final int VER_NOTAS_VIGENTES = Menu.FIRST + 11;
    private static final int VER_NOTAS_CADUCADAS = Menu.FIRST + 12;


    private NotesDbAdapter mDbHelper;
    private ListView mList;

    private SendAbstractionImpl sendAbstraction;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        mDbHelper = NotesDbAdapter.getNotesDbAdapter(this);
        mDbHelper.open();
        mList = (ListView)findViewById(R.id.list);

        Bundle b = getIntent().getExtras();
        if(b == null){
            fillData();
        }
        else if (b != null && b.getString("categoria") == null) {
            fillData();
        }
        else if(b != null){
            String categoria = b.getString("categoria");
            fillNotesOfACategory(categoria);
        }
        setTitle(R.string.title_activity_notes);
        registerForContextMenu(mList);
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor notesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_TITLE};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, notesCursor, from, to);
        mList.setAdapter(notes);
    }

    private void fillNotesOfACategory(String category) {
        // Get all of the categories from the database and create the item list
        Cursor categoriesCursor = mDbHelper.fetchAllNotesOfACategory(category);
        startManagingCursor(categoriesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_TITLE};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, categoriesCursor, from, to);
        mList.setAdapter(notes);
    }

    private void fillExpectedNotes(){
        Cursor notesCursor = mDbHelper.fetchExpectedNotes();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_TITLE};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, notesCursor, from, to);
        mList.setAdapter(notes);
    }

    private void fillCurrentNotes(){
        Cursor notesCursor = mDbHelper.fetchCurrentNotes();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_TITLE};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, notesCursor, from, to);
        mList.setAdapter(notes);
    }

    private void fillExpiredNotes(){
        Cursor notesCursor = mDbHelper.fetchExpiredNotes();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_TITLE};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, notesCursor, from, to);
        mList.setAdapter(notes);
    }

    private void fillDataOrderedByCategory() {
        // Get all of the notes from the database and create the item list
        Cursor notesCursor = mDbHelper.fetchAllNotesOrderedByCategory();
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_TITLE};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, notesCursor, from, to);
        mList.setAdapter(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_NOTE_ID, Menu.NONE, R.string.menu_insert_note);
        menu.add(Menu.NONE, INSERT_CATEGORY_ID, Menu.NONE, R.string.menu_insert_category);
        menu.add(Menu.NONE, ORDENAR_POR_NOTAS, Menu.NONE, R.string.menu_ordenar_por_notas);
        menu.add(Menu.NONE, ORDENAR_POR_CATEGORIAS, Menu.NONE, R.string.menu_ordenar_por_categorias);
        menu.add(Menu.NONE, VER_CATEGORIAS, Menu.NONE, R.string.menu_mostrar_categorias);
        menu.add(Menu.NONE, VER_NOTAS, Menu.NONE, R.string.menu_mostrar_notas);
        menu.add(Menu.NONE, VER_NOTAS_PREVISTAS, Menu.NONE, R.string.menu_ver_previstas);
        menu.add(Menu.NONE, VER_NOTAS_VIGENTES, Menu.NONE, R.string.menu_ver_vigentes);
        menu.add(Menu.NONE, VER_NOTAS_CADUCADAS, Menu.NONE, R.string.menu_ver_caducadas);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_NOTE_ID:
                createNote();
                return true;
            case INSERT_CATEGORY_ID:
                createCategory();
                return true;
            case ORDENAR_POR_NOTAS:
                fillData();
                return true;
            case ORDENAR_POR_CATEGORIAS:
                fillDataOrderedByCategory();
                return true;
            case VER_CATEGORIAS:
                Intent i = new Intent(this, Categories.class);
                startActivityForResult(i, ACTIVITY_CREATE);
                return true;
            case VER_NOTAS:
                fillData();
                return true;
            case VER_NOTAS_PREVISTAS:
                fillExpectedNotes();
                return true;
            case VER_NOTAS_VIGENTES:
                fillCurrentNotes();
                return true;
            case VER_NOTAS_CADUCADAS:
                fillExpiredNotes();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, DELETE_NOTE_ID, Menu.NONE, R.string.menu_delete_note);
        menu.add(Menu.NONE, EDIT_NOTE_ID, Menu.NONE, R.string.menu_edit_note);
        menu.add(Menu.NONE, SEND_EMAIL_ID, Menu.NONE, R.string.send_email);
        menu.add(Menu.NONE, SEND_SMS_ID, Menu.NONE, R.string.send_SMS);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_NOTE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteNote(info.id);
                fillData();
                return true;
            case EDIT_NOTE_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                editNote(info.id);
                return true;
            case SEND_EMAIL_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Cursor note = mDbHelper.fetchNote(info.id);
                startManagingCursor(note);
                String titulo = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE));
                String cuerpo = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY));
                sendAbstraction = new SendAbstractionImpl(this,"email");
                sendAbstraction.send(titulo,cuerpo);
                return true;
            case SEND_SMS_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                note = mDbHelper.fetchNote(info.id);
                startManagingCursor(note);
                titulo = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE));
                cuerpo = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY));
                sendAbstraction = new SendAbstractionImpl(this,"SMS");
                sendAbstraction.send(titulo,cuerpo);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this, NoteEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    private void createCategory() {
        Intent i = new Intent(this, CategoryEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    protected void editNote(long id) {
        Intent i = new Intent(this, NoteEdit.class);
        i.putExtra(NotesDbAdapter.KEY_NOTE_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}
