package ru.otus.l031.supply;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

class CollectionSupplier<T> implements ObjectsSupplier<Collection<? super T>> {

    private final Supplier<? extends Collection<? super T>> constructor;
    private final Supplier<? extends T> elementSuppler;
    private final int size;
    private final String namePrefix;

    CollectionSupplier(
            Supplier<? extends Collection<? super T>> constructor,
            Supplier<? extends T> elementSuppler,
            int size,
            String namePrefix
    ) {
        this.constructor = constructor;
        this.elementSuppler = elementSuppler;
        this.size = size;
        this.namePrefix = namePrefix;
    }

    @Override
    public Stream<MeasurableObject<? extends Collection<? super T>>> objects() {
        Collection<? super T> collection = constructor.get();
        String baseName = "{" + namePrefix + "} " + collection.getClass().getSimpleName();

        return Stream.<MeasurableObject<? extends Collection<? super T>>>iterate(
                new MeasurableObject<>(baseName + " [empty]", collection),
                c -> {
                    c.getObject().add(elementSuppler.get());
                    return new MeasurableObject<>(
                            baseName + " [size=" +c.getObject().size() + "]",
                            c.getObject()
                    );
                }
        ).limit(size);
    }
}
