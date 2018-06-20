package ru.otus.l041.leak;

public enum Leakers {
    ;

    public static MemoryLeaker listLeaker(int addedSize, long timeout) {
        return new ListMemoryLeaker(addedSize, timeout);
    }
}
