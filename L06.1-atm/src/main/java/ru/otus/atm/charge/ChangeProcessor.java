package ru.otus.atm.charge;

import ru.otus.atm.denomination.Denomination;

import java.util.Map;

public interface ChangeProcessor {

    Map<Denomination, Integer> calculateChange(int amount, Map<Denomination, Integer> availableBanknotes);
}
