package junitdemo.parallel;

import org.openqa.selenium.By;

public class MainPage {

    private final DriverAdapter driver;

    public MainPage(DriverAdapter driverAdapter) {
        this.driver = driverAdapter;
    }

    public void open(){
        driver.goToUrl("https://todomvc.com/");
    }

    public void openTechnologyApp(String technologyName){
        var technologyLink = driver.findElement(By.linkText(technologyName));
        technologyLink.click();
    }
}