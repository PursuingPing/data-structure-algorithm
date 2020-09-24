package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash {

    //待添加的节点
    private static String[] servers = {
            "192.168.0.0:111",
            "192.168.0.1:111",
            "192.168.0.2:111",
            "192.168.0.3:111",
            "192.168.0.4:111",
    };

    //真是的节点列表
    private static List<String> realNode = new LinkedList<>();


    //虚拟节点，key为虚拟节点的hash值，value为名称
    private static SortedMap<Integer, String> virtualNodesMap = new TreeMap<>();

    //一个真实节点对应多少个虚拟节点
    private static final int VIRTUAL_NODE = 5;

    static {
        for(String s : servers) {
            realNode.add(s);
            for (int i = 0; i < VIRTUAL_NODE; i++) {
                String virtualNodeName = s.concat("&&VN").concat(String.valueOf(i));
                int hash = getHash(virtualNodeName);
                System.out.println("virtual node :" + virtualNodeName + "has been added and its hash is :" + hash);
                virtualNodesMap.put(hash, virtualNodeName);
            }
        }

    }

    //可以自己实现hash算法 CRC16之类的
    private static int getHash(String virtualNodeName) {
        return Math.abs(virtualNodeName.hashCode());
    }

    //根据传入节点地址，获取路由到的节点

    private static String getServer(String node) {

        //计算hash值
        int hash  = getHash(node);
        //得到大于该hash值的所有节点
        SortedMap<Integer, String> tailMap = virtualNodesMap.tailMap(hash);
        //第一个key就是离node最近的节点
        Integer first = tailMap.firstKey();
        //返回真实节点的地址
        String virtualNodeName = tailMap.get(first);
        return virtualNodeName.substring(0, virtualNodeName.indexOf("&&"));

    }

    public static void main(String[] args) {
        String[] nodes = {
                "127.0.0.1:111",
                "221.226.0.1:2222",
                "10.21.0.1:3333",
        };
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("[" + nodes[i] + "]的hash值为" + getHash(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");
        }

    }
}
