package map;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MyHashMap<K, V> {

    private int capacity = 16;

    private Node<K,V>[] table;

    public MyHashMap() {
        table = (Node<K,V>[])new Node[capacity];
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        volatile V value;
        volatile Node<K, V> next;

        Node(int hash,K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Node) {
                Node<?,?> e = (Node<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }

    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static final int index(int hash, int length) {
        return hash & (length -1);
    }

    public void put(K key, V value) {
        int hash = hash(key);
        int index = index(hash, table.length);

        Node<K, V> newNode = new Node<>(hash,key,value,null);
//
//        if (table[index] == null) {
//            table[index] = newNode;
//        } else {
//            Node<K,V> currentNode = table[index];
//            Node<K,V> previousNode = null;
//
//            while (currentNode != null) {
//                if (currentNode.hash == newNode.hash &&
//                        (key == currentNode.key || (key != null && key.equals(currentNode.key)))) {
//                    currentNode.setValue(value);
//                    return;
//                }
//                previousNode = currentNode;
//                currentNode = currentNode.next;
//            }
//
//            if (previousNode != null) {
//                previousNode.next = newNode;
//            }
//
//        }

        AtomicReference<Node<K,V>> currentNodeAtomic = new AtomicReference<>(table[index]);

        if (currentNodeAtomic.get() == null) {
            currentNodeAtomic.compareAndSet(null, newNode);
        } else {

            Node<K, V> currentNode = currentNodeAtomic.get();
            synchronized (currentNode) {
                Node<K,V> previousNode = null;

                while (currentNode != null) {
                    if (currentNode.hash == newNode.hash &&
                            (key == currentNode.key || (key != null && key.equals(currentNode.key)))) {
                        currentNode.setValue(value);
                        return;
                    }
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }

                previousNode.next = newNode;
            }

        }
    }

    public V get(K key) {

        int hash = hash(key);
        int index = index(hash, table.length);

        Node<K, V> node = table[index];
        while(node != null) {
            if (node.hash == hash &&
                    (key == node.key || (key != null && key.equals(node.key)))) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    public void remove(K key) {

        int hash = hash(key);
        int index = index(hash, table.length);
        Node<K, V> node = table[index];
        Node<K, V> previousNode = null;

        while (node != null) {
            if (node.hash == hash &&
                    (key == node.key|| (key != null && key.equals(node.key)))) {

                if (previousNode == null) {
                    node = node.next;
                    table[index] = node;
                    return;
                } else {
                    previousNode.next = node.next;
                    return;
                }

            }

            previousNode = node;
            node = node.next;
        }

    }

}
