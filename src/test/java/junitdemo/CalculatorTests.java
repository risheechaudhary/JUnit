package junitdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTests {

    private final Calculator calculator = new Calculator();

    @Test
    public void testOnAdd(){

        var actualResult = calculator.add(1,1);
        Assertions.assertEquals(2,actualResult);
    }

    @Test
    public void testOnDifferentNumbers(){

        var actualResult = calculator.add(2,1);
        Assertions.assertEquals(3,actualResult);
    }
}