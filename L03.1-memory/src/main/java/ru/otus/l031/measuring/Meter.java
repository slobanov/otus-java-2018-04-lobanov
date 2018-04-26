package ru.otus.l031.measuring;

public interface Meter {
    long deepSizeOf(Object object);

    static Meter defaultMeter() {
        return new JAMemoryMeter();
    }
}
