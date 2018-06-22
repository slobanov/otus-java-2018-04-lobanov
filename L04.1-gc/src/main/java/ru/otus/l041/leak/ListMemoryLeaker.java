package ru.otus.l041.leak;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

class ListMemoryLeaker implements MemoryLeaker {

    private final int addedSize;
    private final long timeout;

    ListMemoryLeaker(int addedSize, long timeout) {
        this.addedSize = addedSize;
        this.timeout = timeout;
    }

    @Override
    public void leak() {
        List<Integer> l = new ArrayList<>();

        while (true) {
            l = Stream.concat(
                    l.stream().skip(addedSize/2),
                    current().ints().limit(addedSize).boxed()
            ).collect(toList());

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
