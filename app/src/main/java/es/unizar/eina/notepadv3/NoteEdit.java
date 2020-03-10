package es.unizar.eina.notepadv3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import es.unizar.eina.bd.NotesDbAdapter;

public class NoteEdit extends AppCompatActivity {

    private EditText mTitleText;
    private EditText mBodyText;
    private EditText id;

    private Long mRowId;
    private NotesDbAdapter mDbHelper;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(this);
        setContentView(R.layout.note_edit);
        setTitle(R.string.title_activity_note_edit);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
        id = (EditText) findViewById(R.id.id_value);
        spinner = (Spinner) findViewById(R.id.spinner_categories);
        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_NOTE_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null) ? extras.getLong(NotesDbAdapter.KEY_NOTE_ROWID) : null;
        }

        id.setEnabled(false);

        populateFields();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void fillCategories() {
        // Get all of the notes from the database and create the item list
        Cursor categoriesCursor = mDbHelper.fetchAllCategories();
        startManagingCursor(categoriesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{NotesDbAdapter.KEY_CATEGORY_NAME};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter categories = new SimpleCursorAdapter(this, R.layout.rows, categoriesCursor, from, to);
        spinner.setAdapter(categories);
    }

    private void populateFields() {
        fillCategories();
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchNote(mRowId);
            startManagingCursor(note);
            mTitleText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_TITLE)));
            mBodyText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_BODY)));
            id.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_ROWID)));
            int pos = 0;
            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                Cursor aux = (Cursor) spinner.getItemAtPosition(i);
                String cat = aux.getString(aux.getColumnIndex("_id"));
                if(cat.equals(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_CATEGORY)))){
                   pos = i;
               }
            }
            spinner.setSelection(pos);
        }
        else{
            int pos = 0;
            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                Cursor aux = (Cursor) spinner.getItemAtPosition(i);
                String cat = aux.getString(aux.getColumnIndex("_id"));
                if(cat.equals("Ninguna")){
                    pos = i;
                }
            }
            spinner.setSelection(pos);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotesDbAdapter.KEY_NOTE_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume () {
        super.onResume();
        populateFields();
    }

    private void saveState () {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();
        Cursor aux = ((Cursor) spinner.getSelectedItem());
        String category = null;
        if(aux != null){
            category = aux.getString(aux.getColumnIndex("_id"));
        }
        if (mRowId == null) {
            long id = 0;
            if(category != null){
                id = mDbHelper.createNote(title, body, category);
            }
            else{
                id = mDbHelper.createNote(title, body,"Ninguna");
            }
            if (id > 0) {
                mRowId = id;
            }
        }
        else {
            if(category != null){
                mDbHelper.updateNote(mRowId, title, body, category);
            }
            else{
                mDbHelper.updateNote(mRowId, title, body, "Ninguna");
            }
        }
    }
}
