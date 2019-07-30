package algorithm.chapter2nd.question10th.answer1st;

/**
 * @author Jiajing Li
 * @date 2019-02-19 11:30:38
 */
public class Node {

    public int value;

    public Node next;

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
