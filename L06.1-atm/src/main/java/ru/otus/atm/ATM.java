package ru.otus.atm;

import java.util.List;

public interface ATM {

    void accept(Banknote banknote);
    List<Banknote> handOut(int amount);
    int balance();

}
