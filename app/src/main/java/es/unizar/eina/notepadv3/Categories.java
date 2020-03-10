package es.unizar.eina.notepadv3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import es.unizar.eina.bd.NotesDbAdapter;

public class Categories extends AppCompatActivity {

    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int INSERT_CATEGORY_ID = Menu.FIRST;
    private static final int DELETE_CATEGORY_ID = Menu.FIRST + 1;
    private static final int EDIT_CATEGORY_ID = Menu.FIRST + 2;
    private static final int SHOW_NOTES = Menu.FIRST + 3;

    private NotesDbAdapter mDbHelper;
    private ListView mList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        setTitle(R.string.title_activity_categories);

        mDbHelper = NotesDbAdapter.getNotesDbAdapter(this);
        mList = (ListView)findViewById(R.id.list);
        fillData();

        registerForContextMenu(mList);
    }

    private void fillData() {
        // Get all of the categories from the database and create the item list
        Cursor categoriesCursor = mDbHelper.fetchAllCategories();
        startManagingCursor(categoriesCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[] { NotesDbAdapter.KEY_NOTE_ROWID};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[] { R.id.text1};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.rows, categoriesCursor, from, to);
        mList.setAdapter(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_CATEGORY_ID, Menu.NONE, R.string.menu_insert_category);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_CATEGORY_ID:
                createCategory();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, DELETE_CATEGORY_ID, Menu.NONE, R.string.menu_delete_category);
        menu.add(Menu.NONE, EDIT_CATEGORY_ID, Menu.NONE, R.string.menu_edit_category);
        menu.add(Menu.NONE, SHOW_NOTES, Menu.NONE, R.string.menu_show_notes);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_CATEGORY_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Cursor c = (Cursor) mList.getAdapter().getItem((info.position));
                String category = c.getString(c.getColumnIndex("_id"));
                if(!category.equals("Ninguna")){
                    mDbHelper.deleteCategory(category);
                }
                fillData();
                return true;
            case EDIT_CATEGORY_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                c = (Cursor) mList.getAdapter().getItem((info.position));
                category = c.getString(c.getColumnIndex("_id"));
                if(!category.equals("Ninguna")){
                    editCategory(c.getString(c.getColumnIndex("_id")));
                }
                fillData();
                return true;
            case SHOW_NOTES:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                c = (Cursor) mList.getAdapter().getItem((info.position));

                Intent i = new Intent(this, Notes.class);
                Bundle b = new Bundle();
                b.putString("categoria",c.getString(c.getColumnIndex("_id")));
                i.putExtras(b);

                startActivityForResult(i, 8);

                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createCategory() {
        Intent i = new Intent(this, CategoryEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    protected void editCategory(String id) {
        Intent i = new Intent(this, CategoryEdit.class);
        i.putExtra(NotesDbAdapter.KEY_CATEGORY_NAME, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

}
