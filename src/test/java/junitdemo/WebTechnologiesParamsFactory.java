package junitdemo;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

//@MethodSource("junitdemo.WebTechnologiesParamsFactory.provideWebTechnologiesMultipleParams")
public class WebTechnologiesParamsFactory {
    public static Stream<Arguments> provideWebTechnologiesMultipleParams(){
        return Stream.of(
                Arguments.of("Angular.js", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 3),
                Arguments.of("React", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 2),
                Arguments.of("Vue.js", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 2),
                Arguments.of("Angular 2.0", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk"), List.of("Buy Ketchup", "Buy House"), 2)
        );
    }
}
