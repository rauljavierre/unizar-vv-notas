package es.unizar.eina.notepadv3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import es.unizar.eina.bd.NotesDbAdapter;

public class NoteEdit extends AppCompatActivity {

    private EditText mTitleText;
    private EditText mBodyText;
    private EditText id;
    private CalendarView expirationCalendar;
    private CalendarView activationCalendar;
    private String dateActivation;
    private String dateExpiration;

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
        expirationCalendar = (CalendarView) findViewById(R.id.expirationCalendar);
        activationCalendar = (CalendarView) findViewById(R.id.activationCalendar);
        Button confirmButton = (Button) findViewById(R.id.confirm);

        dateActivation = millisecondsToDateFormat(activationCalendar.getDate());
        dateExpiration = millisecondsToDateFormat(getDefaultExpirationDate());

        mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_NOTE_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null) ? extras.getLong(NotesDbAdapter.KEY_NOTE_ROWID) : null;
        }

        id.setEnabled(false);

        populateFields();

        activationCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dateActivation = i2 +"/" +i1 +"/" +i;
                expirationCalendar.setMinDate(dateToMillisecondsFormat(dateActivation));
            }
        });

        expirationCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dateExpiration = i2 +"/" +i1 +"/" +i;
                activationCalendar.setMaxDate(dateToMillisecondsFormat(dateExpiration));
            }
        });

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

            activationCalendar.setDate(note.getLong(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_ACTIVATION_DATE)));
            expirationCalendar.setDate(note.getLong(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_EXPIRATION_DATE)));
            expirationCalendar.setMinDate(activationCalendar.getDate());
            activationCalendar.setMaxDate(expirationCalendar.getDate());

            Log.d("d", "La fecha es: "+note.getLong((note.getColumnIndexOrThrow(NotesDbAdapter.KEY_NOTE_EXPIRATION_DATE))));
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
            expirationCalendar.setDate(getDefaultExpirationDate());
            expirationCalendar.setMinDate(activationCalendar.getDate());
            activationCalendar.setMaxDate(expirationCalendar.getDate());
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
        //activationCalendar.setDate(dateToMillisecondsFormat(dateActivation));
        //expirationCalendar.setDate(dateToMillisecondsFormat(dateExpiration));
        long activationDate = activationCalendar.getDate();
        long expirationDate = expirationCalendar.getDate();
        if(aux != null){
            category = aux.getString(aux.getColumnIndex("_id"));
        }
        if (mRowId == null) {
            long id = 0;
            if(category != null){
                id = mDbHelper.createNote(title, body, activationDate, expirationDate, category);
            }
            else{
                id = mDbHelper.createNote(title, body, activationDate, expirationDate,"Ninguna");
            }
            if (id > 0) {
                mRowId = id;
            }
        }
        else {
            if(category != null){
                Log.d("d", "La fecha es: "+expirationDate);
                mDbHelper.updateNote(mRowId, title, body, activationDate, expirationDate, category);
            }
            else{
                mDbHelper.updateNote(mRowId, title, body, activationDate, expirationDate, "Ninguna");
            }
        }
    }

    // Returns current date plus 30 days in milliseconds
    public long getDefaultExpirationDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0

        return calendar.getTimeInMillis();
    }

    public long dateToMillisecondsFormat(String date){
        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        Log.d("d", "La conversion es: "+millisecondsToDateFormat(milliTime));
        return milliTime;
    }

    public String millisecondsToDateFormat(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        return mDay+"/"+mMonth+"/"+mYear;
    }

}
