public class MyHashMap<K, V> {
    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private int threshold;

    public MyHashMap() {
        this.table = new Node[DEFAULT_INITIAL_CAPACITY];
        this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * LOAD_FACTOR);
    }

    private static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int index(int hash, int length) {
        return (length - 1) & hash;
    }

    /**
     * Adds a key-value pair. If the key already exists, it replaces the value.
     */
    public V put(K key, V value) {
        int hash = hash(key);
        int i = index(hash, table.length);
        Node<K, V> head = table[i];

        for (Node<K, V> e = head; e != null; e = e.next) {
            if (e.hash == hash &&
                    (e.key == key || (key != null && key.equals(e.key)))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        table[i] = new Node<>(hash, key, value, head);
        size++;

        if (size > threshold) {
            resize();
        }

        return null;
    }

    /**
     * Returns the value by key. If the key is not found, null.
     */
    public V get(K key) {
        int hash = hash(key);
        int i = index(hash, table.length);
        Node<K, V> e = table[i];

        for (Node<K, V> p = e; p != null; p = p.next) {
            if (p.hash == hash &&
                    (p.key == key || (key != null && key.equals(p.key)))) {
                return p.value;
            }
        }

        return null;
    }

    /**
     * Deletes an item by key. Returns the old value or null if not found.
     */
    public V remove(K key) {
        int hash = hash(key);
        int i = index(hash, table.length);
        Node<K, V> head = table[i];
        Node<K, V> prev = null;

        for (Node<K, V> e = head; e != null; e = e.next) {
            if (e.hash == hash &&
                    (e.key == key || (key != null && key.equals(e.key)))) {

                if (prev == null) {
                    table[i] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
        }

        return null;
    }

    /**
     * Increases the size of the table by 2 times.
     */
    private void resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = oldTab.length;
        int newCap = oldCap << 1;  // Удваиваем
        Node<K, V>[] newTab = new Node[newCap];

        for (int i = 0; i < oldCap; i++) {
            Node<K, V> e = oldTab[i];
            while (e != null) {
                Node<K, V> next = e.next;
                int newIndex = index(e.hash, newCap);
                e.next = newTab[newIndex];
                newTab[newIndex] = e;
                e = next;
            }
        }

        table = newTab;
        threshold = (int) (newCap * LOAD_FACTOR);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Node<K, V> bucket : table) {
            Node<K, V> e = bucket;
            while (e != null) {
                if (!first) sb.append(", ");
                sb.append(e.key).append("=").append(e.value);
                first = false;
                e = e.next;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}