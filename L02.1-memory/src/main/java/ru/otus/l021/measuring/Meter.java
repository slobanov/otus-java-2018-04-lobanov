package ru.otus.l021.measuring;

public interface Meter {
    long deepSizeOf(Object object);

    static Meter defaultMeter() {
        return new JAMemoryMeter();
    }
}
