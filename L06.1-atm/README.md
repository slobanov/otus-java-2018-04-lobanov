# L06.1-atm

This is a simple ATM emulator. This AMT can accept banknotes of different denominations, estimate residual balance 
and hand out the requested amount with a minimum number of banknotes (or throwing an exception in case it can't be done).

Example of usage:

```java
package ru.otus.atm.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.atm.ATM;
import ru.otus.atm.ATMFactory;
import ru.otus.atm.Banknote;
import ru.otus.atm.cassette.Cassette;
import ru.otus.atm.cassette.CassetteFactory;
import ru.otus.atm.charge.ChangeProcessor;
import ru.otus.atm.charge.ChangeProcessorFactory;

import java.util.List;
import java.util.stream.Stream;

import static ru.otus.atm.denomination.RubleDenomination.*;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ChangeProcessor changeProcessor = new ChangeProcessorFactory().minCountChangeProcessor();

        CassetteFactory cassetteFactory = new CassetteFactory();
        List<Cassette> cassettes = List.of(
                cassetteFactory.newCassette(ONE_HUNDRED, 10),
                cassetteFactory.newCassette(TWO_HUNDRED, 10),
                cassetteFactory.newCassette(FIVE_HUNDRED, 10),
                cassetteFactory.newCassette(ONE_THOUSAND, 10),
                cassetteFactory.newCassette(FIVE_THOUSAND, 10)
        );

        ATM atm = new ATMFactory(changeProcessor).defaultATM(cassettes);

        Stream.of(
                ONE_HUNDRED,
                ONE_HUNDRED,
                TWO_HUNDRED,
                FIVE_HUNDRED,
                TWO_HUNDRED,
                TWO_HUNDRED,
                TWO_HUNDRED,
                FIVE_THOUSAND,
                ONE_THOUSAND,
                ONE_THOUSAND
        ).map(Banknote::new).forEach(atm::accept);

        LOGGER.info("initial balance: {}", atm.balance());
        //results in 8500

        LOGGER.info("handOut: ", atm.handOut(2600));
        /*results in  [ Banknote{denomination=ONE_HUNDRED},
                        Banknote{denomination=FIVE_HUNDRED},
                        Banknote{denomination=ONE_THOUSAND},
                        Banknote{denomination=ONE_THOUSAND}
                      ]
         */

        LOGGER.info("balance after charge: {}", atm.balance());
        // results in 5900
    }
}

```

How to build:
* run `$ mvn clean package` - this will run tests and outputs final artifact.

- - - -

Sergey Lobanov
[s.y.lobanov@yandex.ru](mailto:s.y.lobanov@yandex.ru?Subject=otus-java-2018-04-lobanov)
