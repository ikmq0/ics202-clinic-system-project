package ds;

public class HashTable<K, V> {

    private Object[] keys;
    private Object[] values;
    private boolean[] deleted;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR = 0.5;

    public HashTable() {
        capacity = 16;
        keys = new Object[capacity];
        values = new Object[capacity];
        deleted = new boolean[capacity];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int i = 0;
        int index = hash(key);

        while (keys[index] != null && !deleted[index]) {
            if (keys[index].equals(key)) {
                values[index] = value; // update existing key
                return;
            }
            i++;
            index = (hash(key) + i * i) % capacity; // quadratic probe
        }

        keys[index] = key;
        values[index] = value;
        deleted[index] = false;
        size++;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        int i = 0;
        int index = hash(key);

        while (keys[index] != null || deleted[index]) {
            if (!deleted[index] && keys[index].equals(key)) {
                return (V) values[index];
            }
            i++;
            index = (hash(key) + i * i) % capacity;
            if (i >= capacity)
                break;
        }

        return null;
    }

    public boolean remove(K key) {
        int i = 0;
        int index = hash(key);

        while (keys[index] != null || deleted[index]) {
            if (!deleted[index] && keys[index].equals(key)) {
                keys[index] = null;
                values[index] = null;
                deleted[index] = true; // tombstone
                size--;
                return true;
            }
            i++;
            index = (hash(key) + i * i) % capacity;
            if (i >= capacity)
                break;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        boolean[] oldDeleted = deleted;

        capacity = oldCapacity * 2;
        keys = new Object[capacity];
        values = new Object[capacity];
        deleted = new boolean[capacity];
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            if (oldKeys[i] != null && !oldDeleted[i]) {
                put((K) oldKeys[i], (V) oldValues[i]);
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
}
