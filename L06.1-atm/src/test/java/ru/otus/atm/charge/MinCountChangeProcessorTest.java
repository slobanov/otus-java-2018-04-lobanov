package ru.otus.atm.charge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.atm.ATMException;
import ru.otus.atm.denomination.Denomination;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.atm.denomination.RubleDenomination.*;

class MinCountChangeProcessorTest {

    private static Map<Denomination, Integer> availableBanknotes;
    private ChangeProcessor changeProcessor;

    @BeforeAll
    static void init() {
        availableBanknotes = new HashMap<>();
        availableBanknotes.put(ONE_HUNDRED, 10);
        availableBanknotes.put(TWO_HUNDRED, 6);
        availableBanknotes.put(FIVE_HUNDRED, 4);
        availableBanknotes.put(ONE_THOUSAND, 2);
    }

    @BeforeEach
    void initChangeProcessor() {
        changeProcessor = new MinCountChangeProcessor();
    }

    @Test
    void calculateChargeTooMuch() {
        assertThrows(ATMException.class,
                () -> changeProcessor.calculateChange(100_000, availableBanknotes)
        );
    }

    @Test
    void calculateChargeCantChange() {
        assertThrows(ATMException.class,
                () -> changeProcessor.calculateChange(2003, availableBanknotes)
        );
    }

    @ParameterizedTest
    @MethodSource("amountProvider")
    void calculateCharge(int amount, Map<Denomination, Integer> result) {
        assertEquals(result, changeProcessor.calculateChange(amount, availableBanknotes),
                String.valueOf(amount));
    }

    private static Stream<Arguments> amountProvider() {
        return Stream.of(
                Arguments.of(4500, ofEntries(
                        entry(ONE_THOUSAND, 2),
                        entry(FIVE_HUNDRED, 4),
                        entry(TWO_HUNDRED, 2),
                        entry(ONE_HUNDRED, 1)
                )),
                Arguments.of(3100, ofEntries(
                        entry(ONE_THOUSAND, 2),
                        entry(FIVE_HUNDRED, 2),
                        entry(ONE_HUNDRED, 1)
                )),
                Arguments.of(5600, ofEntries(
                        entry(ONE_THOUSAND, 2),
                        entry(FIVE_HUNDRED, 4),
                        entry(TWO_HUNDRED, 6),
                        entry(ONE_HUNDRED, 4)
                ))
        );
    }

}