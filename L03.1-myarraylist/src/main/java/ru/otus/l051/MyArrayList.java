package ru.otus.l051;

import org.slf4j.Logger;
import org.slf4j.ext.XLoggerFactory;

import java.util.*;

public class MyArrayList<T> extends AbstractList<T> implements List<T> {

    private static final Logger LOGGER = XLoggerFactory.getXLogger(MyArrayList.class);

    private T[] array;
    private int size;

    @SafeVarargs
    public MyArrayList(T... elems) {
        this.array = elems.clone();
        this.size = array.length;
    }

    private void ensureCapacity(int reqSize) {
        if (reqSize > array.length) {
            LOGGER.debug("Extending array to hold '{}' elements", reqSize);
            array = Arrays.copyOf(array, newCapacity(reqSize));
        }
    }

    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index '" + index + "' is out of range [0; " + size + ")");
        }
    }

    private int newCapacity(int minCapacity) {
        int newCapacity = Math.max(minCapacity, 2*array.length+1);
        LOGGER.debug(
                "oldCapacity = '{}'; minCapacity = '{}'; newCapacity = '{}'",
                array.length,
                minCapacity,
                newCapacity
        );
        return newCapacity;
    }

    @Override
    public T get(int index) {
        checkRange(index);
        return array[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T t) {
        LOGGER.info("Adding '{}'", t);
        ensureCapacity(size+1);
        array[size] = t;
        LOGGER.debug("New array = '{}'", Arrays.toString(array));
        ++size;
        return true;
    }

    @Override
    public T set(int index, T element) {
        LOGGER.debug("Setting '{}' on '{}' position", element, index);
        checkRange(index);
        array[index] = element;
        return element;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

}
