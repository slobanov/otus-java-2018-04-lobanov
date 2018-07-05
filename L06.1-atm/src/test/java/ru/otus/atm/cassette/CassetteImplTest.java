package ru.otus.atm.cassette;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATMException;
import ru.otus.atm.Banknote;
import ru.otus.atm.denomination.Denomination;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CassetteImplTest {

    private Cassette cassette;
    private final Denomination denomination = mock(Denomination.class);

    @BeforeEach
    void init() {
        cassette = new CassetteFactory().newCassette(denomination, 10);
    }
    
    @Test
    void getNumberOfBankNotes() {
        assertEquals(0, cassette.getNumberOfBankNotes());

        int n = 5;
        for (int i = 0; i < n; i++) {
            cassette.offerBankNote(new Banknote(denomination));
        }

        assertEquals(n, cassette.getNumberOfBankNotes());

        cassette.retrieveBanknote();
        assertEquals(n-1, cassette.getNumberOfBankNotes());
    }

    @Test
    void getDenomination() {
        assertEquals(denomination, cassette.getDenomination());
    }

    @Test
    void retrieveBanknote() {
        assertThrows(ATMException.class, () -> cassette.retrieveBanknote());

        Banknote banknote = new Banknote(denomination);
        cassette.offerBankNote(banknote);
        assertSame(banknote, cassette.retrieveBanknote());

        assertThrows(ATMException.class, () -> cassette.retrieveBanknote());
    }

    @Test
    void getBalance() {
        assertEquals(0, cassette.getBalance());
    }

    @Test
    void notFull() {
        assertTrue(cassette.notFull());
        cassette.offerBankNote(new Banknote(denomination));
        assertTrue(cassette.notFull());
        for(int i = 0; i < 9; i++) {
            cassette.offerBankNote(new Banknote(denomination));
        }
        assertFalse(cassette.notFull());
        cassette.retrieveBanknote();
        assertTrue(cassette.notFull());
    }

    @Test
    void offerBankNote() {
        Banknote banknote = new Banknote(denomination);
        cassette.offerBankNote(banknote);
        assertThrows(ATMException.class, () -> cassette.offerBankNote(banknote));

        for (int i = 0; i < 9; i++) {
            cassette.offerBankNote(new Banknote(denomination));
        }

        assertThrows(ATMException.class, () -> cassette.offerBankNote(new Banknote(denomination)));
    }
}