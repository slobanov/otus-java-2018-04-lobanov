package ru.otus.l011;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Main {

    public static void main(String[] args) {
        Config conf = ConfigFactory.load();
        String name = conf.getString("greeter.name");

        Greeter greeter = new Greeter(name);

        System.out.println(greeter.greeting());
    }

}
