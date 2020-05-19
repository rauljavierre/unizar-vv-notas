package es.unizar.eina.caminos;

import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import es.unizar.eina.bd.NotesDbAdapter;
import es.unizar.eina.notes.Notes;
import es.unizar.eina.notes.R;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.notNullValue;

public class CaminosTest {

    private static String ultima_nota;
    private static String ultima_categoria;

    @Rule
    public ActivityTestRule<Notes> mNotesRule = new ActivityTestRule<>(Notes.class);

    /*@Rule
    public ActivityTestRule<Categories> mCategoriesRule = new ActivityTestRule<>(Categories.class);
    */

    @Before
    public void setUp() {
        // Resetear la base de datos
        NotesDbAdapter.getNotesDbAdapter(mNotesRule.getActivity().getApplicationContext()).resetDatabase();
    }

    @Test
    public void camino1() throws InterruptedException {
        List<Integer> listaAristas = new ArrayList<>(Arrays.asList(5,5,6,5,7,5,8,5,9,5,10,5,11,5,12,15,5,17,19,5,12,21,22,15,6,6,7,6,8,6,9,6,10,6,11,6,12,21,1));
        recorrerCamino(listaAristas);
    }

    private void recorrerCamino(List<Integer> listaAristas) throws InterruptedException {
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
                default:
                    assert false : "Arista no encontrada";
                    break;
            }
            //sleep(2000);
        }
    }

    // Menú contextual: “Añadir nota” en la actividad principal.
    private void arista1() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_insert_note)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_note)).perform(click());
    }

    // Pulsación larga “Editar nota” en una nota en la actividad principal.
    private void arista2() {
        onView(withText(ultima_nota)).perform(longClick());
        onView(withText(R.string.menu_edit_note)).perform(click());
    }

    // Botón confirmar en actividad editar nota.
    private void arista3() {
        final String title = generateRandomName();
        ultima_nota = title;
        onView(withId(R.id.title)).perform(replaceText(title), closeSoftKeyboard());

        final String body = generateRandomName();
        onView(withId(R.id.body)).perform(replaceText(body), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withText(R.string.confirm)).perform(scrollTo());
        onView(withText(R.string.confirm)).perform(click());
    }

    // Botón back en actividad editar nota.
    private void arista4() {
        final String title = generateRandomName();
        ultima_nota = title;
        onView(withId(R.id.title)).perform(replaceText(title), closeSoftKeyboard());

        final String body = generateRandomName();
        onView(withId(R.id.body)).perform(replaceText(body), closeSoftKeyboard());

        pressBack();
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
        onView(withText(ultima_nota)).perform(longClick());
        onView(withText(R.string.send_email)).perform(click());
        pressBack();
    }

    // Pulsación larga “Eliminar nota” en una nota en la actividad principal.
    private void arista11() {
        onView(withText(ultima_nota)).perform(longClick());
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
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_insert_category)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_category)).perform(click());
    }

    // Pulsación larga “Editar categoría” en la actividad de lista de categorías.
    private void arista14() {
        onView(withText(ultima_categoria)).perform(longClick());
        onView(withText(R.string.menu_edit_category)).perform(click());
    }

    // Botón back en la actividad de lista de categorías.
    private void arista15() {
        pressBack();
    }

    // Botón back en la actividad de editar categoría viniendo de la actividad de lista de categorías.
    private void arista16() {
        final String title = generateRandomName();
        ultima_categoria = title;
        onView(withId(R.id.name)).perform(replaceText(title), closeSoftKeyboard());

        pressBack();
    }

    // Menú contextual: “Añadir categoría” en la actividad principal.
    private void arista17() {
        openActionBarOverflowOrOptionsMenu(mNotesRule.getActivity());
        onView(withText(R.string.menu_insert_category)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert_category)).perform(click());
    }

    // Botón confirmar en la actividad de editar categoría viniendo desde la actividad de lista de categorías.
    private void arista18() {
        final String title = generateRandomName();
        ultima_categoria = title;
        onView(withId(R.id.name)).perform(replaceText(title), closeSoftKeyboard());

        onView(withText(R.string.confirm)).perform(click());
    }

    // Botón confirmar en la actividad de editar categoría viniendo desde la actividad principal.
    private void arista19() {
        final String title = generateRandomName();
        ultima_categoria = title;
        onView(withId(R.id.name)).perform(replaceText(title), closeSoftKeyboard());

        onView(withText(R.string.confirm)).perform(click());
    }

    // Botón back en la actividad de editar categoría viniendo de la actividad principal.
    private void arista20() {
        final String title = generateRandomName();
        ultima_categoria = title;
        onView(withId(R.id.name)).perform(replaceText(title), closeSoftKeyboard());

        pressBack();
    }

    // Pulsación larga “Mostrar notas de la categoría” en una categoría de la actividad de lista de categorías.
    private void arista21() {
        onView(withText(ultima_categoria)).perform(longClick());
        onView(withText(R.string.menu_show_notes)).perform(click());
    }

    // Botón back en la actividad principal viniendo de la actividad de lista de categorías.
    private void arista22() {
        pressBack();
    }

    private String generateRandomName() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int count = 10;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*chars.length());
            builder.append(chars.charAt(character));
        }
        return builder.toString();
    }
}
