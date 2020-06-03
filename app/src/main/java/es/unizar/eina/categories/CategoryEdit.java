package es.unizar.eina.categories;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.R;

public class CategoryEdit extends AppCompatActivity {

    private EditText mNameText;
    private Spinner imageList;
    private CategoryIconAdapter categoryIconAdapter;
    private ArrayList<CategoryWithImage> listaImagenes;

    private String mRowId;
    private NotesDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(this);
        setContentView(R.layout.category_edit);
        setTitle(R.string.title_activity_category_edit);

        cargarSpinner();
        mNameText = (EditText) findViewById(R.id.name);
        imageList = (Spinner) findViewById(R.id.spinnerImages);
        categoryIconAdapter = new CategoryIconAdapter(this, listaImagenes);
        imageList.setAdapter(categoryIconAdapter);


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

    private void cargarSpinner(){
        listaImagenes = new ArrayList<>();
        listaImagenes.add(new CategoryWithImage("Comida", R.drawable.ic_local_dining_black_24dp));
        listaImagenes.add(new CategoryWithImage("Estudio", R.drawable.ic_library_books_black_24dp));
        listaImagenes.add(new CategoryWithImage("Música", R.drawable.ic_headset_black_24dp));
    }

    private void populateFields() {
        cargarSpinner();
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchCategory(mRowId);
            startManagingCursor(note);
            mNameText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_CATEGORY_NAME)));
            listaImagenes = new ArrayList<>();
            System.out.println(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_CATEGORY_ICON)));
            int icono_seleccionado = Integer.parseInt(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_CATEGORY_ICON)));
            listaImagenes.add(new CategoryWithImage("Seleccionado",icono_seleccionado));
            if (icono_seleccionado == R.drawable.ic_headset_black_24dp) {
                listaImagenes.add(new CategoryWithImage("Música", R.drawable.ic_headset_black_24dp));
            }
            if (icono_seleccionado == R.drawable.ic_local_dining_black_24dp) {
                listaImagenes.add(new CategoryWithImage("Comida", R.drawable.ic_local_dining_black_24dp));
            }
            if (icono_seleccionado == R.drawable.ic_library_books_black_24dp) {
                listaImagenes.add(new CategoryWithImage("Estudio", R.drawable.ic_library_books_black_24dp));
            }
            categoryIconAdapter = new CategoryIconAdapter(this, listaImagenes);
            imageList.setAdapter(categoryIconAdapter);
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
        CategoryWithImage cat = (CategoryWithImage) imageList.getSelectedItem();
        int icon = cat.getIcon();
        if(!name.equals("Ninguna")) {
            if (mRowId == null) {
                String id = mDbHelper.createCategory(name, icon);
                if (!id.equals("Ninguna")) {
                    Toast.makeText(getApplicationContext(),"Categoría guardada", Toast.LENGTH_LONG).show();
                    mRowId = id;
                }
                else{
                    Toast.makeText(getApplicationContext(),"La categoría no ha sido guardada", Toast.LENGTH_LONG).show();
                }
            }
            else {
                mDbHelper.updateCategory(mRowId, name, icon);
                Toast.makeText(getApplicationContext(),"Categoría guardada", Toast.LENGTH_LONG).show();
            }
        }
    }
}
