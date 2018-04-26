package ru.otus.l031.supply;

import java.util.stream.Stream;

class SingleObjectSuppler<T> implements ObjectsSupplier<T> {

    private final String name;
    private final T object;

    SingleObjectSuppler(String name, T object) {
        this.name = name;
        this.object = object;
    }

    @Override
    public Stream<MeasurableObject<? extends T>> objects() {
        return Stream.of(new MeasurableObject<>(name, object));
    }
}
