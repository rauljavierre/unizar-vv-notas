package es.unizar.eina.notes.test;


import cucumber.api.CucumberOptions;



@CucumberOptions(features = {"features/RF16sendNoteSMS.feature","features/RF17sendNoteEmail.feature"})
public class RunAcceptationTests {

}