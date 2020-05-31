package es.unizar.eina.caminos;

import android.app.Activity;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import androidx.test.uiautomator.UiDevice;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.CursorMatchers.withRowString;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.notNullValue;

public class CaminosTest {

    private final String TITLE_NOTE_PRECONDITION = "NOTE PRECONDITION";
    private final String BODY_NOTE_PRECONDITION = "Foo Bar";
    private final String NAME_CATEGORY_PRECONDITION = "CATEGORY PRECONDITION";


    @Rule
    public ActivityTestRule<Notes> mNotesRule = new ActivityTestRule<>(Notes.class);

    @Before
    public void setUp() {
        NotesDbAdapter db =  NotesDbAdapter.getNotesDbAdapter(mNotesRule.getActivity().getApplicationContext());

        // Resetear la base de datos
        db.resetDatabase();

        fillToSatisfyPreconditions(db);
    }

    @After
    public void tearDown() {
        NotesDbAdapter db =  NotesDbAdapter.getNotesDbAdapter(mNotesRule.getActivity().getApplicationContext());

        // Resetear la base de datos
        db.resetDatabase();
    }

    @Test
    public void camino1() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(1,3,12,13,16,13,18,15,17,19,1,3,
                1,3,2,3,5,1,4,1,3,6,1,3,7,1,3,8,1,3,9,1,3,5,10,1,3,11,1));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino2() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(17,19,12,21,9,9,5,10,9,5,11,9,5,
                12,21,10,6,17,20,5,6,12,21,22,21,2,3,17,19,12,13,16,14,16,15,1));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino3() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(1,3,12,21,22,13,18,15,17,20,1,4,
                2,4,5,2,4,6,2,4,7,2,4,8,5,2,4,9,5,2,4,10,2,4,11,2,4,12,14,18,15,17,19,2,4,12,15,2,4,
                12,21,22,14,16,15,17,20,2));
        recorrerCamino(listaAristas);
    }
    @Test
    public void camino4() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(5,5,6,5,7,5,8,5,9,5,10,5,11,5,
                12,15,5,17,19,5,12,21,22,15,6,6,7,5,6,8,5,6,9,5,6,10,6,11,6,12,21,1));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino5() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(7,7,8,7,9,7,5,10,7,5,11,7,5,12,
                15,12,21,22,21,8,8,9,8,5,10,8,5,11,8,5,12,21,1,3,11));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino6() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(7,5,17,19,9,7,5,12,21,22,21,7,8,
                5,17,19,10,10,11,10,12,21,22,21,17,20,22,21,12));
        recorrerCamino(listaAristas);
    }
    @Test
    public void camino7() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(17,19,11,1,3,17,19,6,11,1,3,
                7,5,10,8,5,12,15,9,5,10,5,10,9,5,12,15,11,1,3,9,5,17,19,7,5,8,5,12,
                21,12,15,17,19,8,5));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino8() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(17,19,17,19,12,21,22,13,18,21,17,
                20,17,20,12,13,16,21,5,17,20,12,14,18,21,6));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino9() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(17,20,6,17,20,7,5,17,20,8,5,17,
                20,9,5,17,20,10,17,20,11,17,20,12));
        recorrerCamino(listaAristas);
    }

    @Test
    public void camino10() {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(12,24,13,18,24,13,18,14,18,24,13,
                19,21,23,12));
        recorrerCamino(listaAristas);
    }

    private void recorrerCamino(List<Integer> listaAristas)  {
        for(Integer arista : listaAristas) {
            switch (arista) {
                case 1:
                    arista1();
                    break;
                case 2:
                    arista2();
                    break;
                case 3:
                    arista3();
                    break;
                case 4:
                    arista4();
                    break;
                case 5:
                    arista5();
                    break;
                case 6:
                    arista6();
                    break;
                case 7:
                    arista7();
                    break;
                case 8:
                    arista8();
                    break;
                case 9:
                    arista9();
                    break;
                case 10:
                    arista10();
                    break;
                case 11:
                    arista11();
                    break;
                case 12:
                    arista12();
                    break;
                case 13:
                    arista13();
                    break;
                case 14:
                    arista14();
                    break;
                case 15:
                    arista15();
                    break;
                case 16:
                    arista16();
                    break;
                case 17:
                    arista17();
                    break;
                case 18:
                    arista18();
                    break;
                case 19:
                    arista19();
                    break;
                case 20:
                    arista20();
                    break;
                case 21:
                    arista21();
                    break;
                case 22:
                    arista22();
                    break;
                case 23:
                    arista23();
                    break;
                case 24:
                    arista24();
                    break;
                default:
                    assert false : "Arista no encontrada";
                    break;
            }
        }
    }

    // Menú contextual: “Añadir nota” en la actividad principal.
    private void arista1() {
        openActionBarOverflowOrOptionsMenu(getCurrentActivity());
        onView(withText(R.string.menu_insert_note)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_note)).perform(click());
    }

    // Pulsación larga “Editar nota” en una nota en la actividad principal.
    private void arista2() {
        onView(first(withText(TITLE_NOTE_PRECONDITION))).perform(longClick());
        onView(withText(R.string.menu_edit_note)).perform(click());
    }

    // Botón confirmar en actividad editar nota.
    private void arista3() {
        onView(withId(R.id.title)).perform(replaceText(TITLE_NOTE_PRECONDITION), closeSoftKeyboard());
        onView(withId(R.id.body)).perform(replaceText(BODY_NOTE_PRECONDITION), closeSoftKeyboard());

        onView(withId(R.id.spinner_categories)).perform(click());
        onData(withRowString("_id", NAME_CATEGORY_PRECONDITION)).perform(click());

        // Confirma y vuelve a la actividad anterior
        onView(withText(R.string.confirm)).perform(scrollTo());
        onView(withText(R.string.confirm)).perform(click());
    }

    // Botón back en actividad editar nota.
    private void arista4() {
        onView(withId(R.id.title)).perform(replaceText(TITLE_NOTE_PRECONDITION), closeSoftKeyboard());
        onView(withId(R.id.body)).perform(replaceText(BODY_NOTE_PRECONDITION), closeSoftKeyboard());

        onView(withId(R.id.spinner_categories)).perform(click());
        onData(withRowString("_id", NAME_CATEGORY_PRECONDITION)).perform(click());

        onView(withId(R.id.body)).perform(pressBack());
    }

    // Menú contextual: “Ordenar por título” en la actividad principal.
    private void arista5() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_ordenar_por_notas)).check(matches(notNullValue()));
        onView(withText(R.string.menu_ordenar_por_notas)).perform(click());
    }

    // Menú contextual: “Ordenar por categoría” en la actividad principal.
    private void arista6() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_ordenar_por_categorias)).check(matches(notNullValue()));
        onView(withText(R.string.menu_ordenar_por_categorias)).perform(click());
    }

    // Menú contextual: “Mostrar notas vigentes” en la actividad principal.
    private void arista7() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_ver_vigentes)).check(matches(notNullValue()));
        onView(withText(R.string.menu_ver_vigentes)).perform(click());
    }

    // Menú contextual: “Mostrar notas previstas” en la actividad principal.
    private void arista8() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_ver_previstas)).check(matches(notNullValue()));
        onView(withText(R.string.menu_ver_previstas)).perform(click());
    }

    // Menú contextual: “Mostrar notas caducadas” en la actividad principal.
    private void arista9() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_ver_caducadas)).check(matches(notNullValue()));
        onView(withText(R.string.menu_ver_caducadas)).perform(click());
    }

    // Pulsación larga “Enviar nota” en una nota en la actividad principal.
    private void arista10() {
        onView(first(withText(TITLE_NOTE_PRECONDITION))).perform(longClick());
        onView(withText(R.string.send_email)).perform(click());

        // Aprendido en UIAutomator: acceso a otros elementos externos a la aplicación
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressBack();
    }

    // Pulsación larga “Eliminar nota” en una nota en la actividad principal.
    private void arista11() {
        onView(first(withText(TITLE_NOTE_PRECONDITION))).perform(longClick());
        onView(withText(R.string.menu_delete_note)).perform(click());
    }

    // Menú contextual: “Mostrar categorías” en la actividad principal.
    private void arista12() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_mostrar_categorias)).check(matches(notNullValue()));
        onView(withText(R.string.menu_mostrar_categorias)).perform(click());
    }

    // Menú contextual: “Añadir categoría” en la actividad de lista de categorías.
    private void arista13() {
        openActionBarOverflowOrOptionsMenu(getCurrentActivity());
        onView(withText(R.string.menu_insert_category)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_category)).perform(click());
    }

    // Pulsación larga “Editar categoría” en la actividad de lista de categorías.
    private void arista14() {
        onView(first(withText(NAME_CATEGORY_PRECONDITION))).perform(longClick());
        onView(withText(R.string.menu_edit_category)).perform(click());
    }

    // Botón back en la actividad de lista de categorías.
    private void arista15() {
        onView(withText("Ninguna")).perform(pressBack()); // Nadie sabe por qué haciendo un pressBack() únicamente no funciona
    }

    // Botón back en la actividad de editar categoría viniendo de la actividad de lista de categorías.
    private void arista16() {
        onView(withId(R.id.name)).perform(replaceText(NAME_CATEGORY_PRECONDITION), closeSoftKeyboard());

        onView(withText(R.string.confirm)).perform(pressBack()); // Nadie sabe por qué haciendo un pressBack() únicamente no funciona
    }

    // Menú contextual: “Añadir categoría” en la actividad principal.
    private void arista17() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_insert_category)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_category)).perform(click());
    }

    // Botón confirmar en la actividad de editar categoría viniendo desde la actividad de lista de categorías.
    private void arista18() {
        onView(withId(R.id.name)).perform(replaceText(NAME_CATEGORY_PRECONDITION), closeSoftKeyboard());

        onView(withText(R.string.confirm)).perform(click());
    }

    // Botón confirmar en la actividad de editar categoría viniendo desde la actividad principal.
    private void arista19() {
        onView(withId(R.id.name)).perform(replaceText(NAME_CATEGORY_PRECONDITION), closeSoftKeyboard());

        onView(withText(R.string.confirm)).perform(click());
    }

    // Botón back en la actividad de editar categoría viniendo de la actividad principal.
    private void arista20() {
        onView(withId(R.id.name)).perform(replaceText(NAME_CATEGORY_PRECONDITION), closeSoftKeyboard());

        onView(withId(R.id.name)).perform(pressBack()); // Nadie sabe por qué haciendo un pressBack() únicamente no funciona
    }

    // Pulsación larga “Mostrar notas de la categoría” en una categoría de la actividad de lista de categorías.
    private void arista21() {
        onView(withText(NAME_CATEGORY_PRECONDITION)).perform(longClick());
        onView(withText(R.string.menu_show_notes)).perform(click());
    }

    // Botón back en la actividad principal viniendo de la actividad de lista de categorías.
    private void arista22() {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressBack();
    }

    // Menú contextual "Todas las notas" en la actividad principal
    private void arista23() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_mostrar_notas)).check(matches(notNullValue()));
        onView(withText(R.string.menu_mostrar_notas)).perform(click());
    }

    // Pulsación larga "Eliminar categoría" en la lista de categorías
    private void arista24() {
        onView(first(withText(NAME_CATEGORY_PRECONDITION))).perform(longClick());
        onView(withText(R.string.menu_delete_category)).perform(click());
    }

    private Activity getCurrentActivity(){
        final Activity[] currentActivity = {null};
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity activity: resumedActivities){
                    currentActivity[0] = activity;
                    break;
                }
            }
        });
        return currentActivity[0];
    }


    private void fillToSatisfyPreconditions(NotesDbAdapter db) {
        // Creamos una categoría
        db.createCategory(NAME_CATEGORY_PRECONDITION, R.drawable.ic_local_dining_black_24dp);

        // Creamos tres notas: caducadas, vigentes y previstas
        for(int i = 0; i < 3; i++) {
            db.createNote(TITLE_NOTE_PRECONDITION, BODY_NOTE_PRECONDITION, 0, 0,
                    NAME_CATEGORY_PRECONDITION);

            db.createNote(TITLE_NOTE_PRECONDITION, BODY_NOTE_PRECONDITION,
                    System.currentTimeMillis() - 2*86400000,
                    System.currentTimeMillis() + 2*86400000,
                    NAME_CATEGORY_PRECONDITION);

            db.createNote(TITLE_NOTE_PRECONDITION, BODY_NOTE_PRECONDITION,
                    System.currentTimeMillis() + 2*86400000,
                    System.currentTimeMillis() + 4*86400000,
                    NAME_CATEGORY_PRECONDITION);
        }
    }

    private <T> Matcher<T> first(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {
            @Override
            public void describeTo(Description description) { }

            boolean isFirst = true;

            @Override
            public boolean matches(final Object item) {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false;
                    return true;
                }

                return false;
            }
        };
    }
}
