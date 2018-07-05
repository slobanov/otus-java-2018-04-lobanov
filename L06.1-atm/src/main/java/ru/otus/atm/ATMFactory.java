package ru.otus.atm;

import ru.otus.atm.cassette.Cassette;
import ru.otus.atm.charge.ChangeProcessor;

import java.util.List;

public final class ATMFactory {

    public ATM defaultATM(List<Cassette> cassettes, ChangeProcessor chargeCalculator) {
        return new ATMImpl(cassettes, chargeCalculator);
    }
}
