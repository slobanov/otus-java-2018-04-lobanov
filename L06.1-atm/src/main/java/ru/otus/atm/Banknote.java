package ru.otus.atm;

import ru.otus.atm.denomination.Denomination;

public final class Banknote {

    private final Denomination denomination;

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public String toString() {
        return "Banknote{" +
                "denomination=" + denomination +
                '}';
    }
}
