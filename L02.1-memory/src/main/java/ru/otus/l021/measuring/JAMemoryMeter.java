package ru.otus.l021.measuring;

import org.github.jamm.MemoryMeter;

class JAMemoryMeter implements Meter {
    private static final MemoryMeter METER = new MemoryMeter();
    @Override
    public long deepSizeOf(Object object) {
        return METER.measureDeep(object);
    }
}
