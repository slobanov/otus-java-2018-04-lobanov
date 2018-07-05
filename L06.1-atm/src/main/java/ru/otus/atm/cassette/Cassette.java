package ru.otus.atm.cassette;

import ru.otus.atm.Banknote;
import ru.otus.atm.denomination.Denomination;

public interface Cassette {

    int getNumberOfBankNotes();

    Denomination getDenomination();

    Banknote retrieveBanknote();

    int getBalance();

    boolean notFull();

    void offerBankNote(Banknote banknote);
}
