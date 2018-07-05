package ru.otus.atm.charge;

import ru.otus.atm.ATMException;
import ru.otus.atm.denomination.Denomination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.generate;

final class MinCountChangeProcessor implements ChangeProcessor {

    public Map<Denomination, Integer> calculateChange(int amount, Map<Denomination, Integer> availableBanknotes) {

        List<Denomination> denominations = new ArrayList<>(availableBanknotes.keySet());
        denominations.sort(comparing(Denomination::value));

        int denCnt = denominations.size();
        int[][][] minCount = new int[amount+1][denCnt+1][denCnt];

        for (int dIndex = 1; dIndex <= denCnt; dIndex++) {
            Denomination denomination = denominations.get(dIndex-1);
            int value = denominations.get(dIndex-1).value();

            for (int currAmt = 0; currAmt <= amount; currAmt++) {
                minCount[currAmt][dIndex] = minCount[currAmt][dIndex-1].clone();
            }

            for (int i = 1; i <= availableBanknotes.get(denomination); i++) {
                for (int currAmt = 0; currAmt <= amount - i*value; currAmt++) {
                    if (
                         ( sum(minCount[currAmt][dIndex - 1]) != 0
                           && (  sum(minCount[currAmt + i*value][dIndex - 1]) == 0
                              || sum(minCount[currAmt][dIndex-1]) + i < sum(minCount[currAmt + i*value][dIndex-1])
                           )
                         ) || (currAmt == 0)
                    ) {
                        minCount[currAmt + i*value][dIndex] = minCount[currAmt][dIndex - 1].clone();
                        minCount[currAmt + i*value][dIndex][dIndex-1] += i;
                    }
                }

            }
        }

        if (amount != 0 && sum(minCount[amount][denCnt]) == 0) {
            throw new ATMException(
                    format("Failed to calculate change for %s using %s", amount, availableBanknotes)
            );
        }

        return range(0, denCnt)
                .boxed()
                .flatMap(d -> generate(
                        () -> denominations.get(d)).limit(minCount[amount][denCnt][d])
                ).collect(groupingBy(identity(), summingInt(i -> 1)));
    }

    private static int sum(int[] arr) {
        return stream(arr).sum();
    }

}
