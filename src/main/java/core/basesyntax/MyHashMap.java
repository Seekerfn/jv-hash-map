package core.basesyntax;

import java.util.LinkedList;
import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int size = 0;
    private LinkedList<Entry>[] table;

    public MyHashMap() {
        table = new LinkedList[INITIAL_CAPACITY];
    }

    public void resize() {
        LinkedList<Entry>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;

        for (LinkedList<Entry> bucket : oldTable) {
            if (bucket != null) {
                for (Entry entry : bucket) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public void put(K key, V value) {

        int index = getIndex(key);
        float threshold = table.length * LOAD_FACTOR;
        if (size >= threshold) {
            resize();
            index = getIndex(key);
        }

        int myNewIndex = index;
        if (table[myNewIndex] == null) {
            LinkedList<Entry> list = new LinkedList<>();
            list.add(new Entry(key, value));
            table[myNewIndex] = list;
            size++;
        } else {
            LinkedList<Entry> listic = table[myNewIndex];
            for (Entry entry : listic) {
                if (Objects.equals(entry.getKey(), key)) {
                    entry.setValue(value);
                    return;
                }
            }
            listic.add(new Entry(key, value));
            size++;

        }

    }

    @Override
    public V getValue(K key) {
        int index = getIndex(key);
        LinkedList<Entry> list = table[index];
        if (list == null) {
            return null;
        }
        for (Entry entry : list) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    private int getIndex(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % table.length;
    }

    private class Entry {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

    }

}
