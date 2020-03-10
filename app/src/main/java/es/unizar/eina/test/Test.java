package es.unizar.eina.test;

import android.util.Log;

import es.unizar.eina.bd.NotesDbAdapter;

public class Test {
    private NotesDbAdapter mDbHelper;

    public Test(NotesDbAdapter mDbHelper){
        this.mDbHelper = mDbHelper;
    }

    public void testUnitarios(){
        testCreateNote();
        testCreateCategory();
        testDeleteNote();
        testDeleteCategory();
        testUpdateNote();
        testUpdateCategory();
    }

    public void testVolumen(){
        testVolumenN(50);
        testVolumenN(999);
        testVolumenN(1000);
        testVolumenN(1001);
        testVolumenN(10000);
    }

    public void testSobrecarga(){
        String text = "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext";
        int numCaracteres = 5000;
        try{
            Long rowID = mDbHelper.createNote("sobrecarga",text,"Ninguna");
            while(rowID != -1){
                android.util.Log.d("testSobrecarga","OK con caracteres = " + numCaracteres);
                text = text + "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext";
                numCaracteres += 5000;
                mDbHelper.deleteNote(rowID);
                rowID = mDbHelper.createNote(text,text,"Ninguna");
            }
        }
        catch (Throwable t){
            android.util.Log.d("testSobrecarga","Finalizado");
        }
    }

    private void testCreateNote(){
        Log.d("\n","");
        testCreateNoteValid();
        testCreateNoteNotValid();
    }

    private void testCreateCategory(){
        Log.d("\n","");
        testCreateCategoryValid();
        testCreateCategoryNotValid();
    }

    private void testDeleteNote(){
        Log.d("\n","");
        testDeleteNoteValid();
        testDeleteNoteNotValid();
    }

    private void testDeleteCategory(){
        Log.d("\n","");
        testDeleteCategoryValid();
        testDeleteCategoryNotValid();
    }

    private void testUpdateNote(){
        Log.d("\n","");
        testUpdateNoteValid();
        testUpdateNoteNotValid();
    }

    private void testUpdateCategory(){
        Log.d("\n","");
        testUpdateCategoryValid();
        testUpdateCategoryNotValid();
    }

    private void testCreateNoteValid(){
        // Prueba nota con titulo != null ^ titulo.length > 0 ^ body != null ^ category != null
        try {
            Long rowId = mDbHelper.createNote("Nota funcional", "Va a funcionar", "Ninguna");
            android.util.Log.d("createNoteValid", "OK (titulo != null ^ titulo.length > 0 ^ body != null ^ category != null)");
            mDbHelper.deleteNote(rowId);
        }
        catch (Throwable t){
            android.util.Log.d("createNoteValid","NO OK (titulo != null ^ titulo.length > 0 ^ body != null ^ category != null)");
        }
    }

    private void testCreateNoteNotValid(){
        // Prueba con titulo = null
        try{
            mDbHelper.createNote(null,"No va a funcionar","Ninguna");
            android.util.Log.d("createNoteNotValid","NO OK (titulo = null)");
        }
        catch (Throwable t){
            android.util.Log.d("createNoteNotValid","OK (titulo = null)");
        }

        // Prueba con titulo.length() = 0
        if(mDbHelper.createNote(new String(),"Esto tampoco va a funcionar","Ninguna") != -1){
            android.util.Log.d("createNoteNotValid","NO OK (titulo.length() = 0)");
        }
        else{
            android.util.Log.d("createNoteNotValid","OK (titulo.length() = 0)");
        }

        // Prueba con body = null
        try{
            mDbHelper.createNote("Esto no va a funcionar",null,"Ninguna");
            android.util.Log.d("createNoteNotValid","NO OK (body = null)");
        }
        catch (Throwable t){
            android.util.Log.d("createNoteNotValid","OK (body = null)");
        }

        // Prueba con category = null
        try{
            mDbHelper.createNote("Esto no va a funcionar","cuerpo",null);
            android.util.Log.d("createNoteNotValid","NO OK (category = null)");
        }
        catch (Throwable t){
            android.util.Log.d("createNoteNotValid","OK (category = null)");
        }
    }

    private void testCreateCategoryValid(){
        // Prueba categoría con name != null ^ name.length > 0
        try{
            mDbHelper.createCategory("Universidad");
            android.util.Log.d("createCategoryValid","OK (name != null ^ name.length > 0)");
            mDbHelper.deleteCategory("Universidad");
        }
        catch (Throwable t){
            android.util.Log.d("createCategoryValid","NO OK (name != null ^ name.length > 0)");
        }
    }

    private void testCreateCategoryNotValid(){
        // Prueba con name = null
        try{
            mDbHelper.createCategory(null);
            android.util.Log.d("createCategoryNotValid","NO OK (name = null)");
        }
        catch (Throwable t){
            android.util.Log.d("createCategoryNotValid","OK (name = null)");
        }

        // Prueba con name.length() = 0
        if(mDbHelper.createCategory(new String()) != "Ninguna"){
            android.util.Log.d("createCategoryNotValid","NO OK (name.length() = 0)");
        }
        else{
            android.util.Log.d("createCategoryNotValid","OK (name.length() = 0)");
        }
    }

    private void testDeleteNoteValid(){
        // Creamos una nota para posteriormente eliminarla
        long rowId = mDbHelper.createNote("Nota a eliminar", "Esta nota va a ser eliminada","Ninguna");

        if(mDbHelper.deleteNote(rowId)){
            android.util.Log.d("deleteNoteValid","OK (0 < rowId <= #notas)");
        }
        else{
            android.util.Log.d("deleteNoteValid","NO OK (0 < rowId <= #notas)");
        }
    }

    private void testDeleteNoteNotValid(){
        // Intentamos eliminar una nota con rowId < 0
        if(mDbHelper.deleteNote(-1)){
            android.util.Log.d("deleteNoteNotValid","NO OK (rowId < 0)");
        }
        else{
            android.util.Log.d("deleteNoteNotValid","OK (rowId < 0)");
        }

        // Intentamos eliminar una nota con rowId = 0
        if(mDbHelper.deleteNote(0)){
            android.util.Log.d("deleteNoteNotValid","NO OK (rowId = 0)");
        }
        else{
            android.util.Log.d("deleteNoteNotValid","OK (rowId = 0)");
        }

        // Creamos una nota para que nos devuelva un rowId e intentar eliminar una nota con un rowId mayor
        long rowId = mDbHelper.createNote("Nota complice", "Esta nota NO va a ser eliminada","Ninguna");

        if(mDbHelper.deleteNote(rowId + 1)){
            android.util.Log.d("deleteNoteNotValid","NO OK (rowId > #notas)");
        }
        else{
            android.util.Log.d("deleteNoteNotValid","OK (rowId > #notas)");
        }

        // Eliminamos la nota cómplice
        mDbHelper.deleteNote(rowId);
    }

    private void testDeleteCategoryValid(){
        // Creamos una categoría para posteriormente eliminarla
        mDbHelper.createCategory("A eliminar");

        if(mDbHelper.deleteCategory("A eliminar")){
            android.util.Log.d("deleteCategoryValid","OK (Existe ^ name != null ^ name.length > 0)");
        }
        else{
            android.util.Log.d("deleteCategoryValid","NO OK (Existe ^ name != null ^ name.length > 0)");
        }
    }

    private void testDeleteCategoryNotValid(){
        // Intentamos eliminar una categoría inexistente
        if(mDbHelper.deleteCategory("No existo")){
            android.util.Log.d("deleteCategoryNotValid","NO OK (No existe)");
        }
        else{
            android.util.Log.d("deleteCategoryNotValid","OK (No existe)");
        }

        // Intentamos eliminar una categoría con name null
        if(mDbHelper.deleteCategory(null)){
            android.util.Log.d("deleteCategoryNotValid","NO OK (name = null)");
        }
        else{
            android.util.Log.d("deleteCategoryNotValid","OK (name = null)");
        }

        // Intentamos eliminar una categoría con name.length = 0
        if(mDbHelper.deleteCategory(new String())){
            android.util.Log.d("deleteCategoryNotValid","NO OK (name.length = 0)");
        }
        else{
            android.util.Log.d("deleteCategoryNotValid","OK (name.length = 0)");
        }
    }

    private void testUpdateNoteValid(){
        // Creamos una nota para posteriormente actualizarla
        long rowId = mDbHelper.createNote("Nota a actualizar", "Esta nota va a ser actualizada","Ninguna");

        if(mDbHelper.updateNote(rowId,"Nota actualizada","Esta nota ha sido actualizada","Ninguna")){
            android.util.Log.d("updateNoteValid","OK (rowId > 0 ^ rowId <= #notas)");
            mDbHelper.deleteNote(rowId);
        }
        else{
            android.util.Log.d("updateNoteValid","NO OK (rowId > 0 ^ rowId <= #notas)");
        }
    }

    private void testUpdateNoteNotValid(){
        // Intentamos actualizar una nota con rowId < 0
        if(mDbHelper.updateNote(-1,"No existe", "No se va a poder hacer","Ninguna")){
            android.util.Log.d("updateNoteNotValid","NO OK (rowId < 0)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (rowId < 0)");
        }

        // Intentamos actualizar una nota con rowId = 0
        if(mDbHelper.updateNote(0,"No existe", "No se va a poder hacer","Ninguna")){
            android.util.Log.d("updateNoteNotValid","NO OK (rowId = 0)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (rowId = 0)");
        }

        // Creamos una nota para que nos devuelva un rowId e intentar actualizar una nota con un rowId mayor
        long rowId = mDbHelper.createNote("Nota complice", "Esta nota NO va a ser actualizada","Ninguna");

        if(mDbHelper.updateNote(rowId + 1,"No existe", "No se va a poder hacer","Ninguna")){
            android.util.Log.d("updateNoteNotValid","NO OK (rowId > #notas)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (rowId > #notas)");
        }

        // Intentamos actualizar la nota cómplice con un título nulo
        if(mDbHelper.updateNote(rowId,null, "No se va a poder hacer","Ninguna")){
            android.util.Log.d("updateNoteNotValid","NO OK (titulo = null)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (titulo = null)");
        }

        // Intentamos actualizar la nota cómplice con titulo.length = 0
        if(mDbHelper.updateNote(rowId, new String(), "No se va a poder hacer","Ninguna")){
            android.util.Log.d("updateNoteNotValid","NO OK (titulo.length = 0)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (titulo.length = 0)");
        }

        // Intentamos actualizar la nota cómplice con body = null
        if(mDbHelper.updateNote(rowId, "fail", null,"Ninguna")){
            android.util.Log.d("updateNoteNotValid","NO OK (body = null)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (body = null)");
        }

        // Intentamos actualizar la nota cómplice con category = null
        if(mDbHelper.updateNote(rowId, "fail", "fail_body",null)){
            android.util.Log.d("updateNoteNotValid","NO OK (category = null)");
        }
        else{
            android.util.Log.d("updateNoteNotValid","OK (category = null)");
        }

        // Eliminamos nota cómplice
        mDbHelper.deleteNote(rowId);
    }

    private void testUpdateCategoryValid(){
        // Creamos una categoría para posteriormente actualizarla
        mDbHelper.createCategory("Categoría vieja");

        if(mDbHelper.updateCategory("Categoría vieja", "Categoría nueva")){
            android.util.Log.d("updateCategoryValid","OK (oldName existe ^ name no existe ^ oldName != null ^ oldName.length > 0 ^ name != null ^ name.length > 0)");
        }
        else{
            android.util.Log.d("updateCategoryValid","NO OK (oldName existe ^ name no existe ^ oldName != null ^ oldName.length > 0 ^ name != null ^ name.length > 0)");
        }

        // Eliminamos la categoría
        mDbHelper.deleteCategory("Categoría nueva");
    }

    private void testUpdateCategoryNotValid(){
        // Intentamos actualizar una categoría que no existe
        if(mDbHelper.updateCategory("Categoría no existente", "fail")){
            android.util.Log.d("updateCategoryNotValid","NO OK (oldName no existe)");
        }
        else{
            android.util.Log.d("updateCategoryNotValid","OK (oldName no existe)");
        }

        // Intentamos actualizar una categoría nula
        if(mDbHelper.updateCategory(null, "fail")){
            android.util.Log.d("updateCategoryNotValid","NO OK (oldName = null)");
        }
        else{
            android.util.Log.d("updateCategoryNotValid","OK (oldName = null)");
        }

        // Intentamos actualizar una oldName.length = 0
        if(mDbHelper.updateCategory(new String(), "fail")){
            android.util.Log.d("updateCategoryNotValid","NO OK (oldName.length = 0)");
        }
        else{
            android.util.Log.d("updateCategoryNotValid","OK (oldName.length = 0)");
        }

        // Intentamos actualizar una categoría existente y le ponemos el nombre de otra categoría también existente
        mDbHelper.createCategory("A actualizar");
        mDbHelper.createCategory("No va a dejar que se actualice");

        if(mDbHelper.updateCategory("A actualizar", "No va a dejar que se actualice")){
            android.util.Log.d("updateCategoryNotValid","NO OK (name existe)");
        }
        else{
            android.util.Log.d("updateCategoryNotValid","OK (name existe)");
        }

        // Intentamos actualizar una categoría existente y le ponemos el nuevo nombre nulo
        if(mDbHelper.updateCategory("A actualizar", null)){
            android.util.Log.d("updateCategoryNotValid","NO OK (name = null)");
        }
        else{
            android.util.Log.d("updateCategoryNotValid","OK (name = null)");
        }

        // Intentamos actualizar una categoría existente y name.length = 0
        if(mDbHelper.updateCategory("A actualizar", new String())){
            android.util.Log.d("updateCategoryNotValid","NO OK (name.length = 0)");
        }
        else{
            android.util.Log.d("updateCategoryNotValid","OK (name.length = 0)");
        }

        // Eliminamos las categorías creadas
        mDbHelper.deleteCategory("A actualizar");
        mDbHelper.deleteCategory("No va a dejar que se actualice");
    }

    public void testVolumenN(int N){
        try{
            Long ultimaAnyadida = insertarNotas(N);
            retirarNotas(ultimaAnyadida, N);
            android.util.Log.d("testVolumen con " + Integer.toString(N),"Consigue insertarlas");
        }
        catch (Throwable t){
            android.util.Log.d("testVolumen con " + Integer.toString(N),"No consigue insertarlas");
        }
    }

    private long insertarNotas(int cantidad){
        Long rowId = null;
        for(int i = 0; i < cantidad; i++){
            rowId = mDbHelper.createNote("Nota test numero " + Integer.toString(i),"body","Ninguna");
        }
        return rowId;
    }

    private void retirarNotas(Long ultimaAnyadida, int cantidad){
        while(cantidad > 0){
            mDbHelper.deleteNote(ultimaAnyadida - cantidad + 1);
            cantidad--;
        }
    }
}
