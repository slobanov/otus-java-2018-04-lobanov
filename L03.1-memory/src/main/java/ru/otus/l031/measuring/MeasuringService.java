package ru.otus.l031.measuring;

import ru.otus.l031.report.SizeReporter;
import ru.otus.l031.supply.MeasurableObject;
import ru.otus.l031.supply.ObjectsSupplier;

public class MeasuringService {
    private final Meter meter;
    private final SizeReporter reporter;

    public MeasuringService(Meter meter, SizeReporter reporter) {
        this.meter = meter;
        this.reporter = reporter;
    }

    public void performMeasurement(ObjectsSupplier<?> objectsSuppler) {
        objectsSuppler.objects()
                .forEach(this::measureOne);
    }

    private void measureOne(MeasurableObject<?> object) {
        long size = meter.deepSizeOf(object.getObject());
        reporter.report(object.getName(), size);

    }
}
