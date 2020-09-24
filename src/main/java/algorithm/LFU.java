package algorithm;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFU<K, V>{
    //容量
    private int capacity;
    //元素个数
    private int size;
    //最小频率
    private int minFreq;
    //Key 和Value的映射
    private Map<K, Node<K, V>> map;
    //频率和对应数据的链表
    private Map<Integer, LinkedHashSet<Node<K,V>>> freqMap;

    //数据节点
    class Node<K, V> {
        K key;
        V value;
        int frequency = 1;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    public LFU(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.minFreq = 1;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public V get(K key) {

        Node<K, V> node = map.get(key);

        if (node == null) {
            return null;
        }

        //增加访问频率
        plusFreq(node);
        return node.value;
    }

    public void put(K key, V value) {
        if(capacity < 0) return;

        Node<K,V> node = map.get(key);
        if(node != null) {
            node.value = value;
            plusFreq(node);
        } else {
            //淘汰数据
            eliminate();
            //新增数据并放到数据频率为1的数据链表中
            Node<K, V> newNode = new Node<>(key, value);
            map.put(key,newNode);
            LinkedHashSet<Node<K,V>> set = freqMap.computeIfAbsent(1, v -> new LinkedHashSet<>());
            set.add(newNode);

            //更新最低频率1
            minFreq = 1;
            size++;
        }
    }

    private void eliminate() {
        if(size < capacity) {
            return;
        }
        //容量满了，删除最低频率的链表的头结点
        LinkedHashSet<Node<K, V>> nodes = freqMap.get(minFreq);
        Node<K,V> node = nodes.iterator().next();
        nodes.remove(node);
        //从映射中删除
        map.remove(node.key);
        //容量更新
        size--;
    }


    private void plusFreq(Node<K, V> node) {

        int frequency = node.frequency;
        //从旧的频率对应的队列中删除
        LinkedHashSet<Node<K,V>> oldSet = freqMap.get(frequency);
        oldSet.remove(node);
        //是否需要更新最低频率
        if (minFreq == frequency && oldSet.isEmpty()) {
            minFreq++;
        }

        //将节点移到高一的频率队列中
        frequency++;
        //增加节点的频率
        node.frequency++;
        LinkedHashSet<Node<K,V>> set = freqMap.computeIfAbsent(frequency, k -> new LinkedHashSet<>());
        set.add(node);
    }

}
