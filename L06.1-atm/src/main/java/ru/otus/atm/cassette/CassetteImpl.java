package ru.otus.atm.cassette;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.atm.ATMException;
import ru.otus.atm.Banknote;
import ru.otus.atm.denomination.Denomination;

import java.util.IdentityHashMap;
import java.util.Map;

final class CassetteImpl implements Cassette {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassetteImpl.class);

    private final Denomination denomination;
    private final int capacity;
    private final Map<Banknote, Boolean> content = new IdentityHashMap<>();

    CassetteImpl(Denomination denomination, int capacity) {
        this.denomination = denomination;
        this.capacity = capacity;
    }

    @Override
    public int getNumberOfBankNotes() {
        return content.size();
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public Banknote retrieveBanknote() {
        LOGGER.info("Retrieving banknote");

        if (content.isEmpty()) {
            throw new ATMException("Cassette is empty");
        }
        Banknote banknote = content.keySet().iterator().next();
        content.remove(banknote);

        LOGGER.info("Retrieved banknote: {}", banknote);
        return banknote;
    }

    @Override
    public int getBalance() {
        return denomination.value() * content.size();
    }

    @Override
    public boolean notFull() {
        return content.size() < capacity;
    }

    @Override
    public void offerBankNote(Banknote banknote) {
        LOGGER.info("Accepting banknote: {}", banknote);
        if (notFull()) {
            if (content.containsKey(banknote)) {
                throw new ATMException("Can't accept same banknote twice");
            } else {
                content.put(banknote, true);
            }
        } else {
            throw new ATMException("Cassette is full");
        }
    }
}
