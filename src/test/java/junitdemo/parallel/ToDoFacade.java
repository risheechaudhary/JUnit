package junitdemo.parallel;

import java.util.List;

public class ToDoFacade {
    private final MainPage mainPage;
    private final ToDoAppPage toDoAppPage;

    public ToDoFacade(MainPage mainPage, ToDoAppPage toDoAppPage) {
        this.mainPage = mainPage;
        this.toDoAppPage = toDoAppPage;
    }

    public void verifyToDoListCreatedSuccessfully(String technology, List<String> itemsToAdd, List<String> itemsToCheck, int expectedLeftItems){
        mainPage.open();
        mainPage.openTechnologyApp(technology);

        itemsToAdd.stream().forEach(item -> toDoAppPage.addNewToDoItem(item));
        itemsToCheck.stream().forEach(item -> toDoAppPage.getItemCheckbox(item).click());

        toDoAppPage.assertLeftItems(expectedLeftItems);
    }
}