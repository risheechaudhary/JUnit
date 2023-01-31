package junitdemo.parallel;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ToDoAppPage {
    private final DriverAdapter driverAdapter;

    public ToDoAppPage(DriverAdapter driverAdapter) {
        this.driverAdapter = driverAdapter;
    }

    public void open(){
        driverAdapter.goToUrl("https://todomvc.com/");
    }

    public void assertLeftItems(int expectedCount) {

        var resultSpan = driverAdapter.findElement(By.xpath("//footer/*/span | //footer/span"));
        if (expectedCount == 1){
            var expectedText = String.format("%d items left",expectedCount);
            driverAdapter.validateInnerTextIs(resultSpan, expectedText);
        } else {
            var expectedText = String.format("%d items left",expectedCount);
            driverAdapter.validateInnerTextIs(resultSpan, expectedText);
        }
    }

    public WebElement getItemCheckbox(String todoItem) {
        var xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input",todoItem);
        return driverAdapter.findElement(By.xpath(xpathLocator));
    }

    public void addNewToDoItem(String todoItem) {
        var todoInput = driverAdapter.findElement(By.xpath("//input[@placeholder='What needs to be done?']"));
        todoInput.sendKeys(todoItem);
        driverAdapter.getActions().click(todoInput).sendKeys(Keys.ENTER).perform();
    }

    public void openTechnologyApp(String technologyName){
        var technologyLink = driverAdapter.findElement(By.linkText(technologyName));
        technologyLink.click();
    }
}
