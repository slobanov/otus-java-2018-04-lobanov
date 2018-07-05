package ru.otus.atm.denomination;

public enum RubleDenomination implements Denomination{
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int value;

    RubleDenomination(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
