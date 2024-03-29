package com.siwuxie095.algorithm.chapter2nd.question9th.answer2nd;

/**
 * @author Jiajing Li
 * @date 2019-02-19 10:22:23
 */
public class Node {

    public int value;

    /**
     * 指向下一个节点
     */
    public Node next;

    /**
     * 指向随机一个节点，也可能指向 null
     */
    public Node rand;

    public Node(int data) {
        this.value = data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", next=" + next +
                '}';
    }
}
