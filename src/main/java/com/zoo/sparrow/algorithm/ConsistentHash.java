package com.zoo.sparrow.algorithm;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by David.Liu on 17/5/22.
 */
public class ConsistentHash {
    /**
     * 虚拟节点个数 用于复制真是节点进行负载均衡
     */
    private final int virtualNodeNum;
    //环形SortMap 用于存放节点并排序
    private SortedMap<Long, Node> circleMap = new TreeMap<Long, Node>();

    /**
     * 构造，使用Java默认的Hash算法
     *
     * @param virtualNodeNum 虚拟化节点数量 复制的节点个数，增加每个节点的复制节点有利于负载均衡
     * @param nodes          节点对象
     */
    public ConsistentHash(int virtualNodeNum, Collection<Node> nodes) {
        this.virtualNodeNum = virtualNodeNum;
        for (Node node : nodes) {
            addNode(node);
        }
    }

    /**
     * 构造
     *
     * @param virtualNodeNum 虚拟化节点数量 复制的节点个数，增加每个节点的复制节点有利于负载均衡
     * @param nodes          节点对象
     */
    public ConsistentHash(int virtualNodeNum, Node node) {
        this.virtualNodeNum = virtualNodeNum;
        addNode(node);
    }

    /**
     * 构造
     *
     * @param virtualNodeNum 虚拟化节点数量
     */
    public ConsistentHash(int virtualNodeNum) {
        this.virtualNodeNum = virtualNodeNum;
    }

    /**
     * 增加节点<br>
     * 每增加一个节点，就会在闭环上增加给定复制节点数<br>
     * 例如复制节点数是2，则每调用此方法一次，增加两个虚拟节点，这两个节点指向同一Node
     * 由于hash算法会调用node的toString方法，故按照toString去重
     *
     * @param node 节点对象
     */
    public void addNode(Node node) {
        for (int i = 0; i < virtualNodeNum; i++) {
            circleMap.put(HashUtils.murMurHash(node.toString() + i), node);
        }
    }

    /**
     * 移除节点的同时移除相应的虚拟节点
     *
     * @param node 节点对象
     */
    public void remove(Node node) {
        for (int i = 0; i < virtualNodeNum; i++) {
            circleMap.remove(HashUtils.murMurHash(node.toString() + i));
        }
    }
//
//    /**
//     * 获得一个最近的顺时针节点
//     *
//     * @param key 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
//     * @return 节点对象
//     */
//    public Node get(Object key) {
//        if (circleMap.isEmpty()) {
//            return null;
//        }
//        long hash = HashUtils.murMurHash(RText.toString(key));
//        if (!circleMap.containsKey(hash)) {
//            SortedMap<Long, Node> tailMap = circleMap.tailMap(hash); //返回此映射的部分视图，其键大于等于 hash
//            hash = tailMap.isEmpty() ? circleMap.firstKey() : tailMap.firstKey();
//        }
//        //正好命中
//        return circleMap.get(hash);
//    }
}
