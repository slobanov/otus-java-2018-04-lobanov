package ru.otus.l011;

import static java.lang.String.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

class Greeter {

    private final String name;

    Greeter(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException(
                    format("Name must not be blank. [%s] is given.", name)
            );
        }
        this.name = name;
    }

    String greeting() {
        return "Hello, " + name + "!";
    }
}
