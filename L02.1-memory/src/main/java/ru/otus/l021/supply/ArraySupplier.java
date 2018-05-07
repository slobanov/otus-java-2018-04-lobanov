package ru.otus.l021.supply;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ArraySupplier<T> implements ObjectsSupplier<T[]> {

    private final IntFunction<T[]> constructor;
    private final Supplier<? extends T> elementSuppler;
    private final int size;
    private final String namePrefix;

    ArraySupplier(IntFunction<T[]> constructor, Supplier<? extends T> elementSuppler, int size, String namePrefix) {
        this.constructor = constructor;
        this.elementSuppler = elementSuppler;
        this.size = size;
        this.namePrefix = namePrefix;
    }

    @Override
    public Stream<MeasurableObject<? extends T[]>> objects() {
        return IntStream.range(0, size)
                .mapToObj(this::init);

    }

    private MeasurableObject<T[]> init(int size) {
        T[] arr = constructor.apply(size);
        IntStream.range(0, size)
                .forEach(i -> arr[i] = elementSuppler.get());

        String name = "{" + namePrefix + "} Array" + "[" + (size != 0 ? size :"empty") + "]";
        return new MeasurableObject<>(name, arr);
    }
}
