package ru.otus.atm.cassette;

import ru.otus.atm.denomination.Denomination;

public class CassetteFactory {

    public Cassette newCassette(Denomination denomination, int capacity) {
        return new CassetteImpl(denomination, capacity);
    }
}
