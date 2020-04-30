package es.unizar.eina.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;

@RunWith(AndroidJUnit4.class)
public class CreateCategoryTest {
    @Rule
    public ActivityTestRule<Notes> activityRule = new ActivityTestRule<>(Notes.class);

    private NotesDbAdapter mDbHelper;
    private String toDelete;

    @Before
    public void setUp(){
        AppCompatActivity notes = activityRule.getActivity();
        mDbHelper = NotesDbAdapter.getNotesDbAdapter(notes.getApplicationContext());
    }

    @After
    public void tearDown() {
        mDbHelper.deleteCategory(toDelete);
    }

    @Test
    public void testCreateCategoryValid(){
        toDelete = mDbHelper.createCategory("Universidad", R.drawable.ic_local_dining_black_24dp);

        Assert.assertNotEquals("Ninguna", toDelete);
    }

    @Test
    public void testCreateCategoryNotValidNameNull(){
        Assert.assertEquals("Ninguna", mDbHelper.createCategory(null, R.drawable.ic_local_dining_black_24dp));
    }

    @Test
    public void testCreateCategoryNotValidNameEmpty(){
        Assert.assertEquals("Ninguna", mDbHelper.createCategory(new String(), R.drawable.ic_local_dining_black_24dp));
    }

    @Test
    public void testCreateCategoryNotValidNameExists(){
        toDelete = mDbHelper.createCategory("Categoría repetida", R.drawable.ic_local_dining_black_24dp);

        Assert.assertEquals("Ninguna", mDbHelper.createCategory("Categoría repetida", R.drawable.ic_local_dining_black_24dp));
    }
}
