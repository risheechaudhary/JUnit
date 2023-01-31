package junitdemo.parallel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class CompileJsToDoTestsChrome extends WebTest{

    private ToDoFacade toDoFacade;

    @Override
    protected void initializeDriver(String testName) {
        getDriver().start(Browser.CHROME);
        toDoFacade = new ToDoFacade(new MainPage(getDriver()), new ToDoAppPage(getDriver()));
    }

    @ParameterizedTest(name = "{index}. verify todo list created successfully when technology = {0}")
    @ValueSource(strings = {
            "Backbone.js",
            "AngularJS"
    })

    public void verifyToDoListCreatedSuccessfully_withParams(String technology){
        var itemsToAdd = List.of("Clean the car", "Clean the house", "Buy milk", "Buy Ketchup");
        var itemsToCheck = List.of("Clean the house", "Buy milk");

        toDoFacade.verifyToDoListCreatedSuccessfully(technology, itemsToAdd, itemsToCheck, 2);
    }
}