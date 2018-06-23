package ru.otus.l021;

import ru.otus.l021.measuring.MeasuringService;
import ru.otus.l021.measuring.Meter;
import ru.otus.l021.report.SizeReporter;
import ru.otus.l021.supply.ObjectsSupplier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static ru.otus.l021.report.SizeReporter.consoleReporter;
import static ru.otus.l021.supply.ObjectsSupplier.*;

public class Main {

    public static void main(String[] args) {
        Meter meter = Meter.defaultMeter();
        SizeReporter sizeReporter = consoleReporter();

        MeasuringService measuringService = new MeasuringService(meter, sizeReporter);
        objectsSupplerStream().forEach(measuringService::performMeasurement);
    }

    private static Stream<ObjectsSupplier<?>> objectsSupplerStream() {
        int size = 10;
        return of(
                emptyString(),
                object(),
                randomIntCollection(ArrayList::new, size),
                randomIntCollection(LinkedList::new, size),
                sameValCollection(ArrayList::new, new Object(), size),
                sameValCollection(ArrayList::new, null, size),
                sameValCollection(LinkedList::new, 42, size),
                genericCollection(HashSet::new, Object::new, size, "new objects"),
                genericArray(Object[]::new, Object::new, size, "new objects"),
                genericArray(Object[]::new, () -> null, size, "null elements" )
        );
    }
}
