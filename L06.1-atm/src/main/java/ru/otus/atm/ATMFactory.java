package ru.otus.atm;

import ru.otus.atm.cassette.Cassette;
import ru.otus.atm.charge.ChangeProcessor;

import java.util.List;

public final class ATMFactory {

    private final ChangeProcessor changeProcessor;

    public ATMFactory(ChangeProcessor changeProcessor) {
        this.changeProcessor = changeProcessor;
    }

    public ATM defaultATM(List<Cassette> cassettes) {
        return new ATMImpl(cassettes, changeProcessor);
    }
}
