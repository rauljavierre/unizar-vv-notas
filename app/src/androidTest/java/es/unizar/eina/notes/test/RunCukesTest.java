package es.unizar.eina.notes.test;

import cucumber.api.CucumberOptions;

/**
 * The CucumberOptions annotation is mandatory for exactly one of the classes in the test project.
 * Only the first annotated class that is found will be used, others are ignored. If no class is
 * annotated, an exception is thrown. This annotation does not have to placed in runner class
 */
@CucumberOptions(
        features = {"features"}
)
public class RunCukesTest {
}
