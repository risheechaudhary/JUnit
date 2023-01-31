package junitdemo.parallel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class ToDoTestCloud {

    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;
    private String status = "failed";

    public ToDoTestCloud(WebDriver driver, WebDriverWait webDriverWait, Actions actions) {
        this.driver = driver;
        this.webDriverWait = webDriverWait;
        this.actions = actions;
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) throws MalformedURLException {

        var capabilites = new DesiredCapabilities();
        capabilites.setCapability("browserName","Chrome");
        capabilites.setCapability("ChromeVersion", "95.0");

        HashMap<String,Object> ltOptions = new HashMap<String, Object>();

        String username = System.getenv("LT_USERNAME");
        String accesskey = System.getenv("LT_ACCESS_KEY");

        ltOptions.put("username",username);
        ltOptions.put("accessKey",accesskey);
        ltOptions.put("build", "first to be build");
        ltOptions.put("DisplayName", testInfo.getDisplayName());

        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("browserName", "Chrome");
        ltOptions.put("browserVersion", 95.0);
        ltOptions.put("network", true);
        ltOptions.put("console", "true");
        ltOptions.put("visual", true);
        ltOptions.put("timezone", "UTC+02:00");
        capabilites.setCapability("LT_Options",ltOptions);

        try {
            String url = String.format("https://%s:%s@hub.lambdatest.com/wd/hub", username, accesskey);
            driver = new RemoteWebDriver(new URL(url), capabilites);
            webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
            actions = new Actions(driver);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @ParameterizedTest(name = "{index}. verify todo list created successfully when technology = {0}")
    @ValueSource(strings = {
            "Backbone.js",
            "AngularJS",
            "Ember.js",
            "KnockoutJS",
            "Dojo",
            "Knockback.js",
            "CanJS",
            "Polymer",
            "React",
            "Mithril",
            "Vue.js",
            "Marionette.js"
    })

    @NullAndEmptySource
    public void verifyToDoListCreatedSuccessfully_withParams(String technology){
        driver.navigate().to("https://todomvc.com/");
        openTechnologyApp(technology);
        addNewToDoItem("Clean the house");
        addNewToDoItem("Clean the car");
        addNewToDoItem("Buy Ketchup");
        getItemCheckbox("Buy Ketchup").click();

        assertLeftItems(2);
    }
    private void assertLeftItems(int expectedCount) {

        var resultSpan = waitAndFindElement(By.xpath("//footer/*/span | //footer/span"));
        if (expectedCount == 1){
            var expectedText = String.format("%d items left",expectedCount);
            validateInnerTextIs(resultSpan, expectedText);
        } else {
            var expectedText = String.format("%d items left",expectedCount);
            validateInnerTextIs(resultSpan, expectedText);
        }
    }

    private void validateInnerTextIs(WebElement resultElement, String expectedText) {
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    private WebElement getItemCheckbox(String todoItem) {
        var xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input",todoItem);
        return waitAndFindElement(By.xpath(xpathLocator));
    }

    private void addNewToDoItem(String todoItem) {
        var todoInput = waitAndFindElement(By.xpath("//input[@placeholder='What needs to be done?']"));
        todoInput.sendKeys(todoItem);
        actions.click(todoInput).sendKeys(Keys.ENTER).perform();
    }

    public void openTechnologyApp(String technologyName){
        var technologyLink = waitAndFindElement(By.linkText(technologyName));
        technologyLink.click();
    }

    private WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private static Stream<String> provideWebTechnologies(){

        return Stream.of("Backbone.js",
                "AngularJS",
                "Ember.js",
                "KnockoutJS",
                "Dojo",
                "Knockback.js",
                "CanJS",
                "Polymer",
                "React",
                "Mithril",
                "Vue.js",
                "Marionette.js");
    }

    private Stream<Arguments> provideWebTechnologiesMultipleParams(){
        return Stream.of(
                Arguments.of("Angular.js", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 3),
                Arguments.of("React", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 2),
                Arguments.of("Vue.js", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 2),
                Arguments.of("Angular 2.0", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 2)
        );
    }

    @AfterEach
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}