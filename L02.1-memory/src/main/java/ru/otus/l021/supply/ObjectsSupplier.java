package ru.otus.l021.supply;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ObjectsSupplier<T> {
    Stream<MeasurableObject<? extends T>> objects();

    static ObjectsSupplier<String> emptyString() {
        return new SingleObjectSuppler<>("Empty String", "");
    }

    static ObjectsSupplier<?> object() {
        return new SingleObjectSuppler<>("Object", new Object());
    }

    static ObjectsSupplier<Collection<? super Integer>> randomIntCollection(
            Supplier<Collection<? super Integer>> collectionSupplier,
            int size
    ) {
        return genericCollection(
                collectionSupplier,
                () -> ThreadLocalRandom.current().nextInt(),
                size,
                "random ints"
        );
    }

    static <E> ObjectsSupplier<Collection<? super E>> sameValCollection(
            Supplier<Collection<? super E>> collectionSupplier,
            E value,
            int size
    ) {
        return genericCollection(
                collectionSupplier,
                () -> value,
                size,
                "same value " + value
        );

    }

    static <E> ObjectsSupplier<Collection<? super E>> genericCollection(
            Supplier<Collection<? super E>> collectionSupplier,
            Supplier<? extends E> elementSuppler,
            int size,
            String namePrefix
    ) {
        return new CollectionSupplier<>(
                collectionSupplier,
                elementSuppler,
                size,
                namePrefix
        );
    }

    static <E> ObjectsSupplier<E[]> genericArray(
            IntFunction<E[]> arraySupplier,
            Supplier<? extends E> elementSupplier,
            int size,
            String namePrefix
    ) {
        return new ArraySupplier<>(
                arraySupplier,
                elementSupplier,
                size,
                namePrefix
        );
    }

}
