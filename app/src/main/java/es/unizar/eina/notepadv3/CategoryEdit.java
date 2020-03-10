package es.unizar.eina.notepadv3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;

import es.unizar.eina.bd.NotesDbAdapter;

public class CategoryEdit extends AppCompatActivity {

    private EditText mNameText;

    private String mRowId;
    private NotesDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(this);
        setContentView(R.layout.category_edit);
        setTitle(R.string.title_activity_category_edit);

        mNameText = (EditText) findViewById(R.id.name);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = (savedInstanceState == null) ? null : (String) savedInstanceState.getSerializable(NotesDbAdapter.KEY_CATEGORY_NAME);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null) ? extras.getString(NotesDbAdapter.KEY_CATEGORY_NAME) : null;
        }

        populateFields();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchCategory(mRowId);
            startManagingCursor(note);
            mNameText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_CATEGORY_NAME)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotesDbAdapter.KEY_CATEGORY_NAME, mRowId);
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
        String name = mNameText.getText().toString();
        if(!name.equals("Ninguna")) {
            if (mRowId == null) {
                String id = mDbHelper.createCategory(name);
                if (!id.equals("Ninguna")) {
                    mRowId = id;
                }
            } else {
                mDbHelper.updateCategory(mRowId, name);
            }
        }
    }
}
