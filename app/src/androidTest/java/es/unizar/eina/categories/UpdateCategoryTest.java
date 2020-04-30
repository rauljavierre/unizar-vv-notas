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
public class UpdateCategoryTest {
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
    public void testUpdateCategoryValid(){
        toDelete = mDbHelper.createCategory("Categoría vieja", R.drawable.ic_local_dining_black_24dp);

        Assert.assertTrue(mDbHelper.updateCategory(toDelete, "Categoría nueva", R.drawable.ic_local_dining_black_24dp));

        toDelete = "Categoría nueva";
    }

    @Test
    public void testDeleteCategoryNotValidOldNameNotExists(){
        Assert.assertFalse(mDbHelper.updateCategory("No existo", "Categoría nueva", R.drawable.ic_local_dining_black_24dp));
    }

    @Test
    public void testDeleteCategoryNotValidOldNameNull(){
        Assert.assertFalse(mDbHelper.updateCategory(null, "Categoría nueva", R.drawable.ic_local_dining_black_24dp));
    }

    @Test
    public void testDeleteCategoryNotValidOldNameEmpty(){
        Assert.assertFalse(mDbHelper.updateCategory(new String(), "Categoría nueva", R.drawable.ic_local_dining_black_24dp));
    }

    @Test
    public void testDeleteCategoryNotValidNewNameExists(){
        String c1 = mDbHelper.createCategory("Categoría vieja", R.drawable.ic_local_dining_black_24dp);
        String c2 = mDbHelper.createCategory("Categoría repetida", R.drawable.ic_local_dining_black_24dp);

        Assert.assertFalse(mDbHelper.updateCategory("Categoría vieja", "Categoría repetida", R.drawable.ic_local_dining_black_24dp));

        mDbHelper.deleteCategory(c1);
        mDbHelper.deleteCategory(c2);
    }

    @Test
    public void testDeleteCategoryNotValidNewNameNull(){
        toDelete = mDbHelper.createCategory("Categoría vieja", R.drawable.ic_local_dining_black_24dp);

        Assert.assertFalse(mDbHelper.updateCategory(toDelete, null, R.drawable.ic_local_dining_black_24dp));
    }

    @Test
    public void testDeleteCategoryNotValidNewNameEmpty(){
        Assert.assertFalse(mDbHelper.updateCategory(toDelete, new String(), R.drawable.ic_local_dining_black_24dp));
    }
}
