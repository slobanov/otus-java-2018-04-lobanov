package ru.otus.l021.supply;

public class MeasurableObject<T> {

    private final String name;
    private final T object;

    MeasurableObject(String name, T object) {
        this.name = name;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public T getObject() {
        return object;
    }
}
