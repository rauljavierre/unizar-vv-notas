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
public class DeleteCategoryTest {
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
    public void testDeleteCategoryValid(){
        toDelete = mDbHelper.createCategory("A eliminar", R.drawable.ic_local_dining_black_24dp);

        Assert.assertTrue(mDbHelper.deleteCategory(toDelete));
    }

    @Test
    public void testDeleteCategoryNotValidNameNotExists(){
        Assert.assertFalse(mDbHelper.deleteCategory("No existo"));
    }

    @Test
    public void testDeleteCategoryNotValidNameNull(){
        Assert.assertFalse(mDbHelper.deleteCategory(null));
    }

    @Test
    public void testDeleteCategoryNotValidNameEmpty(){
        Assert.assertFalse(mDbHelper.deleteCategory(new String()));
    }
}
