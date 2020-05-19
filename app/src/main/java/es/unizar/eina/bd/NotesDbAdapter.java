package es.unizar.eina.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;


/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 *
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class NotesDbAdapter {


    public static final String KEY_NOTE_TITLE = "title";
    public static final String KEY_NOTE_BODY = "body";
    public static final String KEY_NOTE_CATEGORY = "category";
    public static final String KEY_NOTE_ROWID = "_id";
    public static final String KEY_NOTE_ACTIVATION_DATE = "activation";
    public static final String KEY_NOTE_EXPIRATION_DATE = "expiration";

    public static final String KEY_CATEGORY_NAME = "_id";
    public static final String KEY_CATEGORY_ICON = "icon";

    private static final String TAG = "NotesDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static NotesDbAdapter BD;

    // mClock permitirá resolver la dependencia relativa a la fecha actual
    // En producción, su valor es el de un objeto que representa la fecha actual
    // En testing, se podrá cambiar con el método setTestingTime
    private Clock mClock = Clock.systemDefaultZone();

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE_TABLE_CATEGORIES = "create table categories (_id text primary key, icon integer);";
    private static final String DATABASE_CREATE_TABLE_NOTES = "create table notes (_id integer primary key autoincrement, " + "title text not null, body text not null, activation long not null, expiration long not null, category text references categories(_id));";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE_CATEGORIES = "categories";
    private static final String DATABASE_TABLE_NOTES = "notes";
    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_DEFAULT_CATEGORY = "\"Ninguna\"";

    private final Context mCtx;

    public long lastIdNotes;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL(DATABASE_CREATE_TABLE_CATEGORIES);
            db.execSQL(DATABASE_CREATE_TABLE_NOTES);
            db.execSQL("INSERT INTO categories (_id) VALUES(" + DATABASE_DEFAULT_CATEGORY + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL(DATABASE_CREATE_TABLE_CATEGORIES);
            db.execSQL(DATABASE_CREATE_TABLE_NOTES);
            db.execSQL("INSERT INTO categories (_id) VALUES(" + DATABASE_DEFAULT_CATEGORY + ");");
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    private NotesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public static NotesDbAdapter getNotesDbAdapter(Context ctx){
        if(BD == null){
            BD = new NotesDbAdapter(ctx);
        }
        return BD;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public NotesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title, body and category provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @param title the title of the note... title != null ^ title.length > 0
     * @param body the body of the note... body != null
     * @param category the category of the note... category != null
     * @param activationDate the activation date of the note... activationDate >= 0 & activationDate <= expirationDate
     * @param expirationDate the expiration date of the note... expirationDate >= 0 & expirationDate >= activationDate
     * @return rowId or exception if failed
     */
    public long createNote(String title, String body, long activationDate, long expirationDate, String category) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOTE_TITLE, title);
        initialValues.put(KEY_NOTE_BODY, body);
        initialValues.put(KEY_NOTE_CATEGORY, category);
        initialValues.put(KEY_NOTE_ACTIVATION_DATE, activationDate);
        initialValues.put(KEY_NOTE_EXPIRATION_DATE, expirationDate);

        if(title == null || title.length() == 0 || body == null || activationDate < 0 || expirationDate < 0 || category == null || activationDate > expirationDate){
            throw new IllegalArgumentException();
        }

        lastIdNotes = mDb.insertOrThrow(DATABASE_TABLE_NOTES, null, initialValues);
        return lastIdNotes;
    }

    /**
     * Create a new category using the name provided. If the category is not
     * succesfully created, returns "Ninguna", otherwise, returns the name of the category
     *
     * @param name the name of the category... name != null ^ name.length > 0
     * @return "Ninguna" if failed, name otherwise
     */
    public String createCategory(String name, int icon) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY_NAME, name);
        initialValues.put(KEY_CATEGORY_ICON, icon);

        try{
            if(name.length() == 0){
                return "Ninguna";
            }
            mDb.insertOrThrow(DATABASE_TABLE_CATEGORIES, null, initialValues);
            return name;
        }
        catch(Throwable t) {
            return "Ninguna";
        }
    }

    /**
     * Delete the note with the given rowId
     *
     * @param rowId id of note to delete... 0 < rowId <= #notas
     * @return true if deleted, false otherwise
     */
    public boolean deleteNote(long rowId) {
        return mDb.delete(DATABASE_TABLE_NOTES, KEY_NOTE_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Delete the category with the given name
     *
     * @param name id of the category to delete... Category with name "name" exists ^ name != null ^ name.length > 0
     * @return true is the category has been deleted, false otherwise
     */
    public boolean deleteCategory(String name) {
        setOldCategoryToNewCategory(name,"Ninguna");
        return mDb.delete(DATABASE_TABLE_CATEGORIES, KEY_CATEGORY_NAME + "='" + name + "'", null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database ordered by name
     *
     * @return Cursor over all notes ordered by name
     */
    public Cursor fetchAllNotes() {
        return mDb.query(DATABASE_TABLE_NOTES, new String[] {KEY_NOTE_ROWID, KEY_NOTE_TITLE, KEY_NOTE_BODY, KEY_NOTE_ACTIVATION_DATE, KEY_NOTE_EXPIRATION_DATE, KEY_NOTE_CATEGORY}, null, null, null, null, KEY_NOTE_TITLE);
    }

    /**
     * Return a Cursor over the list of all notes in the database ordered by category
     *
     * @return Cursor over all notes ordered by category
     */
    public Cursor fetchAllNotesOrderedByCategory() {
        return mDb.query(DATABASE_TABLE_NOTES, new String[] {KEY_NOTE_ROWID, KEY_NOTE_TITLE, KEY_NOTE_BODY, KEY_NOTE_ACTIVATION_DATE, KEY_NOTE_EXPIRATION_DATE, KEY_NOTE_CATEGORY}, null, null, null, null, KEY_NOTE_CATEGORY);
    }

    /**
     * Return a Cursor over the list of all notes in the database with state = expected
     *
     * @return Cursor over all notes with state = expected
     */
    public Cursor fetchExpectedNotes() {
        return mDb.rawQuery("SELECT * FROM NOTES WHERE ? < ACTIVATION", new String[]{Long.toString(getActualTime())});
    }

    /**
     * Return a Cursor over the list of all notes in the database with state = current
     *
     * @return Cursor over all notes with state = current
     */
    public Cursor fetchCurrentNotes() {
        return mDb.rawQuery("SELECT * FROM NOTES WHERE ? > ACTIVATION AND ? < EXPIRATION", new String[]{Long.toString(getActualTime()), Long.toString(getActualTime())});
    }

    /**
     * Return a Cursor over the list of all notes in the database with state = expired
     *
     * @return Cursor over all notes with state = expired
     */
    public Cursor fetchExpiredNotes() {
        return mDb.rawQuery("SELECT * FROM NOTES WHERE ? > ACTIVATION AND ? > EXPIRATION", new String[]{Long.toString(getActualTime()), Long.toString(getActualTime())});
    }

    /**
     * Return a Cursor over the list of all notes of a category
     *
     * @return Cursor over all notes of a category
     */
    public Cursor fetchAllNotesOfACategory(String category){
        String args[] = {category};
        return mDb.rawQuery("SELECT * FROM NOTES WHERE CATEGORY = ?", args);
    }

    /**
     * Return a Cursor over the list of all categories in the database
     *
     * @return Cursor over all categories
     */
    public Cursor fetchAllCategories() {
        return mDb.query(DATABASE_TABLE_CATEGORIES, new String[] {KEY_CATEGORY_NAME}, null, null, null, null, KEY_CATEGORY_NAME);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor = mDb.query(true, DATABASE_TABLE_NOTES, new String[] {KEY_NOTE_ROWID, KEY_NOTE_TITLE, KEY_NOTE_BODY, KEY_NOTE_ACTIVATION_DATE, KEY_NOTE_EXPIRATION_DATE, KEY_NOTE_CATEGORY}, KEY_NOTE_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Return a Cursor positioned at the category that matches the given name
     *
     * @param name id of category to retrieve
     * @return Cursor positioned to matching category, if found
     * @throws SQLException if category could not be found/retrieved
     */
    public Cursor fetchCategory(String name) throws SQLException {
        String args[] = {name};
        Cursor mCursor = mDb.rawQuery("SELECT * FROM CATEGORIES WHERE _ID = ?", args);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            return mCursor;
        }
        else{
            return null;
        }

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title, body and category
     * values passed in
     *
     * @param rowId id of note to update... 0 < rowId <= #notas
     * @param title value to set note title to... title != null ^ title.length > 0
     * @param body value to set note body to... body != null
     * @param category value to set note category to... category != null
     * @param activationDate value to set activation date to... activationDate >= 0 ^ activationDate <= expirationDate
     * @param expirationDate value to set expiration date to... expirationDate >= 0 ^ expirationDate >= activationDate
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateNote(long rowId, String title, String body, long activationDate, long expirationDate, String category) {
        if(title == null || title.length() == 0 || body == null || activationDate < 0 || expirationDate < 0 || category == null || activationDate > expirationDate){
            return false;
        }

        ContentValues args = new ContentValues();
        args.put(KEY_NOTE_TITLE, title);
        args.put(KEY_NOTE_BODY, body);
        args.put(KEY_NOTE_CATEGORY, category);
        args.put(KEY_NOTE_ACTIVATION_DATE, activationDate);
        args.put(KEY_NOTE_EXPIRATION_DATE, expirationDate);

        try{
            return mDb.update(DATABASE_TABLE_NOTES, args, KEY_NOTE_ROWID + "=" + rowId, null) > 0;
        }
        catch (Throwable t){
            return false;
        }
    }

    /**
     * Update the category using the details provided. The category to be updated is
     * specified using the old name, and it is altered to use the name value passed in
     *
     * @param oldName id of category to update... oldName != null ^ oldName.length > 0
     * @param name value to set to name category to... name != null ^ name.length > 0
     * @return true if the category was successfully updated, false otherwise
     */
    public boolean updateCategory(String oldName, String name, int icon) {
        if(oldName == null || oldName.length() == 0){
            return false;
        }
        if(name == null || name.length() == 0){
            return false;
        }
        if(fetchCategory(oldName) == null) {
            return false;
        }

        try{
            // Implementación distinta al updateNote, utilizando execSQL
            mDb.execSQL("UPDATE Categories SET icon='" + icon + "' WHERE _id='" + oldName + "'");
            mDb.execSQL("UPDATE Categories SET _id='" + name + "' WHERE _id='" + oldName + "'");
            setOldCategoryToNewCategory(oldName,name);
            return true;
        }
        catch (Throwable t){
            return false;
        }
    }

    /**
     * Set category newCategory to all notes with the category oldCategory.
     *
     * @param oldCategory id of category to update... oldCategory != null ^ oldCategory.length > 0
     */
    private void setOldCategoryToNewCategory(String oldCategory, String newCategory){
        mDb.execSQL("UPDATE Notes SET category='" + newCategory + "' WHERE category='" + oldCategory + "'");
    }

    private long getActualTime() {
        final long currentTimeMillis = Instant.now(mClock).toEpochMilli();
        return currentTimeMillis;
    }

    public void setTestingTime(long pseudotime) {
        mClock = Clock.fixed(Instant.ofEpochMilli(pseudotime), ZoneId.systemDefault());
    }

    public void disableTestingTime() {
        mClock = Clock.systemDefaultZone();
    }

    public void resetDatabase() {
        mDbHelper.onUpgrade(mDb, 1, 1);
    }
}