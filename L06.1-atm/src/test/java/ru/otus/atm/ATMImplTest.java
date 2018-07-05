package ru.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.cassette.Cassette;
import ru.otus.atm.charge.ChangeProcessor;
import ru.otus.atm.denomination.Denomination;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ATMImplTest {

    private ATM atm;
    private Denomination denomination1;
    private Denomination denomination2;
    private Map<Denomination, Banknote> banknoteMap;

    private static final int DEN1_CNT = 6;
    private static final int VAL1 = 3;

    private static final int DEN2_CNT = 3;
    private static final int VAL2 = 5;

    @BeforeEach
    void init() {
        denomination1 = mock(Denomination.class);
        when(denomination1.value()).thenReturn(DEN1_CNT);

        denomination2 = mock(Denomination.class);
        when(denomination2.value()).thenReturn(DEN2_CNT);

        banknoteMap = ofEntries(
                entry(denomination1, new Banknote(denomination1)),
                entry(denomination2, new Banknote(denomination2))
        );

        Cassette cassette1 = setUpCassette(denomination1, DEN1_CNT, DEN1_CNT*VAL1, true);
        Cassette cassette2 = setUpCassette(denomination2, DEN2_CNT, DEN2_CNT*VAL2, false);

        ChangeProcessor changeProcessor = setUpChangeProcessor();
        atm = new ATMFactory().defaultATM(List.of(cassette1, cassette2), changeProcessor);

    }

    private Cassette setUpCassette(Denomination denomination, int nBanknotes, int balance, boolean notFull) {
        Cassette cassette = mock(Cassette.class);

        when(cassette.notFull()).thenReturn(notFull);
        when(cassette.retrieveBanknote()).thenReturn(banknoteMap.get(denomination));
        when(cassette.getBalance()).thenReturn(balance);
        when(cassette.getNumberOfBankNotes()).thenReturn(nBanknotes);
        when(cassette.getDenomination()).thenReturn(denomination);

        return cassette;
    }

    private ChangeProcessor setUpChangeProcessor() {
        ChangeProcessor changeProcessor = mock(ChangeProcessor.class);

        Map<Denomination, Integer> availableDenomination =
                ofEntries(entry(denomination1, DEN1_CNT), entry(denomination2, DEN2_CNT));

        when(changeProcessor.calculateChange(30, availableDenomination))
                .thenReturn(ofEntries(
                        entry(denomination1, 5),
                        entry(denomination2, 3)
                ));

        when(changeProcessor.calculateChange(18, availableDenomination))
                .thenReturn(ofEntries(
                        entry(denomination1, 1),
                        entry(denomination2, 3)
                ));

        return changeProcessor;
    }

    @Test
    void acceptTest() {
        assertThrows(ATMException.class, () -> atm.accept(new Banknote(mock(Denomination.class))));
        assertThrows(ATMException.class, () -> atm.accept(new Banknote(denomination2)));
        atm.accept(new Banknote(denomination1));
    }

    @Test
    void handOutTest() {
        assertThrows(ATMException.class, () -> atm.handOut(1000));
    }

    @Test
    void balanceTest() {
        assertEquals(VAL1*DEN1_CNT + VAL2*DEN2_CNT, atm.balance());

        assertEquals(
                ofEntries(entry(denomination1, 5), entry(denomination2, 3)),
                groupBanknoteDenoms(atm.handOut(30))
        );

        assertEquals(
                ofEntries(entry(denomination1, 1), entry(denomination2, 3)),
                groupBanknoteDenoms(atm.handOut(18))
        );
    }

    private Map<Denomination, Integer> groupBanknoteDenoms(List<Banknote> banknotes) {
        return banknotes.stream()
                        .map(Banknote::getDenomination)
                        .collect(groupingBy(identity(), summingInt(i -> 1)));
    }
}