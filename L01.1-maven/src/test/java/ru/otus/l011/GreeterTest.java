package ru.otus.l011;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GreeterTest {

    @ParameterizedTest
    @ValueSource(strings = {"World", "OTUS"})
    void normalGreeting(String name) {
        Greeter greeter = new Greeter(name);
        assertEquals("Hello, " + name + "!", greeter.greeting());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t", "  \t", "\t  \t"})
    void illegalGreeting(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Greeter(name));
    }

    @Test
    void nullGreeting() {
        assertThrows(IllegalArgumentException.class, () -> new Greeter(null));
    }

}