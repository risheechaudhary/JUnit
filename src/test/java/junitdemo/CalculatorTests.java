package junitdemo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.condition.OS.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("nightlyRun")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(value = MethodOrderer.Random.class)
public class CalculatorTests {

    private final Calculator calculator = new Calculator();

    @BeforeAll
    public static void setUpClass(){
        System.out.println("This is @BeforeAll annotation");
    }

    @BeforeEach
    public void setUp(){
        System.out.println("This is @BeforeEach annotation");
    }

    @NightlyRunTest
    @Order(2)
    public void testOnAdd(){

        System.out.println("test1---------------");
        var actualResult = calculator.add(1,1);
        Assertions.assertEquals(2,actualResult);
    }

   // @RepeatedTest(value = 10, name = "{displayName}_{currentRepetition}/{totalRepetitions}")
    //@DisplayName("TEST addition different number")
//    @Timeout(value = 100,unit = TimeUnit.SECONDS)
//    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
//    @EnabledIfEnvironmentVariable(named = "ENV", matches = ".*DEV.*")
//    @Disabled("jira bug id = 2141")
//    @DisabledOnOs(WINDOWS)
//    @EnabledOnOs({LINUX,MAC})
   @Test
   @Order(1)
   public void testOnDifferentNumbers(){

        System.out.println("test2----------------");
        var actualResult = calculator.add(2,1);
        Assertions.assertEquals(3,actualResult);
    }

    @AfterAll
    public static void tearDownClass(){
        System.out.println("This is @AfterAll annotation");
    }

    @AfterEach
    public void tearDown(){
        System.out.println("This is @AfterEach annotation ");
    }
}