package algorithm;

import java.util.HashMap;
import java.util.Map;

public class LRU<K, V> {


    int capacity;

    private Map<K,Node<K,V>> cache;

    Node head;

    Node tail;

    class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    public LRU(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();

    }

    public V get(K key) {

        Node<K, V> node = cache.get(key);
        if (node != null) {
            moveToHead(node);
            return node.value;
        }

        return null;
    }

    public void put(K key, V value) {

        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            node.value = value;
            moveToHead(node);

        } else {
            Node<K, V> node = new Node<>(key, value);
            if (cache.size() == capacity) {
                cache.remove(tail.key);
                removeNode(tail);
            }

            cache.put(key, node);
            addFirst(node);

        }

    }

    private void removeNode(Node node) {

        if (node == null) return;

        Node<K,V> prevNode = node.prev;
        Node<K,V> nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
    }

    private void addFirst(Node<K, V> node) {
        if (node == null) return;
        node.next = head;
        node.prev = null;

        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addFirst(node);
    }


}
