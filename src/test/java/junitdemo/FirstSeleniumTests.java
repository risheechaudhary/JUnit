package junitdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.Duration.ofMinutes;
import static junitdemo.DateTimeAssert.assertEquals;

public class FirstSeleniumTests {

    private static WebDriver webDriver;

    @BeforeAll
    public static void setUpClass(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        webDriver = new ChromeDriver();
    }

    @Test
    public void properCheckboxSelected() throws Exception {

        webDriver.navigate().to("https://lambdatest.github.io/sample-todo-app/");
        LocalDate birthDay = LocalDate.of(2000,12,12);
        DateTimeFormatter usDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateToType = usDateFormat.format(birthDay);

        WebElement todoInp = webDriver.findElement(By.id("sampletodotext"));
        todoInp.sendKeys(dateToType);

        var addButton = webDriver.findElement(By.id("addButton"));
        addButton.click();

        var todoCheckBoxes = webDriver.findElements(By.xpath("//li[@ng-repeat]/input"));
        todoCheckBoxes.get(2).click();

        var todoInfos = webDriver.findElements(By.xpath("//li[@ng-repeat]/span"));
        Assertions.assertEquals("2000-12-12",todoInfos.get(5).getText());

        String expectedUrl = "https://lambdatest.github.io/sample-todo-app/";
        Assertions.assertEquals(expectedUrl, webDriver.getCurrentUrl(), "URL does not match");

        String notExpectedUrl = "https://lambdatest.com/";
        Assertions.assertNotEquals(notExpectedUrl, webDriver.getCurrentUrl(), "URL matches");

        var expectedItems = new String[] {"First Item", "Second Item", "Third Item", "Fourth Item", "12-12-2000"};
        var actualToDoInfos = todoInfos.stream().map(WebElement::getText).toArray();
        Assertions.assertArrayEquals(expectedItems, actualToDoInfos);

        Exception exception = Assertions.assertThrows(ArithmeticException.class, ()-> new Calculator().divide(1,0));

        Assertions.assertEquals("/ by zero", exception.getMessage());
        Assertions.assertTimeout(ofMinutes(2), ()->{
            //perform tasks
        });

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedUrl, webDriver.getCurrentUrl(), "URL does not match"),
                () -> Assertions.assertNotEquals(notExpectedUrl, webDriver.getCurrentUrl(), "URL matches"),
                () -> Assertions.assertArrayEquals(expectedItems, actualToDoInfos)
        );

        double actualDoubleValue = 2.999;
        double expectedDoubleValue = 3.000;

        Assertions.assertEquals(expectedDoubleValue, actualDoubleValue, 0.001);

        var currentTime = LocalDateTime.now();
        var previousTime = LocalDateTime.now().minusMinutes(3);

        assertEquals(currentTime, previousTime, DateTimeDeltaType.MINUTES, 4);
    }

    @AfterAll
    public static void tearDownClass(){
        if (webDriver != null ){
            webDriver.quit();
        }
    }
}