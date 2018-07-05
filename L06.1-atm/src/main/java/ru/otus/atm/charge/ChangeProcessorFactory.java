package ru.otus.atm.charge;

public final class ChangeProcessorFactory {

    public ChangeProcessor minCountChangeProcessor() {
        return new MinCountChangeProcessor();
    }
}
