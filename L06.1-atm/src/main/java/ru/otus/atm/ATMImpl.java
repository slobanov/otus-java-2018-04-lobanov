package ru.otus.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.atm.cassette.Cassette;
import ru.otus.atm.charge.ChangeProcessor;
import ru.otus.atm.denomination.Denomination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.IntStream.range;

public class ATMImpl implements ATM {

    private static final Logger LOGGER = LoggerFactory.getLogger(ATMImpl.class);

    private final List<Cassette> cassettes;
    private final ChangeProcessor changeCalculator;

    ATMImpl(List<Cassette> cassettes, ChangeProcessor changeCalculator) {
        this.cassettes = new ArrayList<>(cassettes);
        this.changeCalculator = changeCalculator;
    }

    @Override
    public void accept(Banknote banknote) {
        LOGGER.info("Offering banknote: {}", banknote);

        cassettes.stream()
                .filter(c -> c.getDenomination().equals(banknote.getDenomination()))
                .filter(Cassette::notFull)
                .findFirst()
                .ifPresentOrElse(c -> c.offerBankNote(banknote), () -> {
                    throw new ATMException("There is no suitable or not full cassette");
                });
    }

    @Override
    public List<Banknote> handOut(int amount) {
        LOGGER.info("Charging {}", amount);
        if (amount > balance()) {
            throw new ATMException("Insufficient funds");
        }

        Map<Denomination, Integer> availableBanknotes =
                cassettes.stream().collect(
                        groupingBy(Cassette::getDenomination, summingInt(Cassette::getNumberOfBankNotes))
                );

        Map<Denomination, Integer> change = new HashMap<>(
                changeCalculator.calculateChange(amount, availableBanknotes)
        );

        LOGGER.debug("changeCalculator response: {}", change);

        List<Banknote> banknotes = new ArrayList<>();
        for (Cassette cassette: cassettes) {

            if (change.isEmpty()) {
                break;
            }

            Denomination denomination = cassette.getDenomination();
            if (change.containsKey(denomination)) {
                int needCnt = change.get(denomination);
                int availableCnt = cassette.getNumberOfBankNotes();

                if (availableCnt >= needCnt) {
                    change.remove(denomination);
                } else {
                    change.put(denomination, needCnt-availableCnt);
                }

                range(0, min(availableCnt, needCnt)).forEach(
                        i -> banknotes.add(cassette.retrieveBanknote())
                );
            }
        }

        LOGGER.debug("Change for {}: {}", amount, banknotes);

        return banknotes;
    }

    @Override
    public int balance() {
        int balance = cassettes.stream().mapToInt(Cassette::getBalance).sum();
        LOGGER.info("Getting balance: {}", balance);
        return balance;
    }

}
