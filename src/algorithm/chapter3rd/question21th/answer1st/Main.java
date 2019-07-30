package algorithm.chapter3rd.question21th.answer1st;

/**
 * 先序、中序、和后序数组两两结合重构二叉树
 *
 * 题目：
 * 已知一棵二叉树的所有节点值都不同，给定这棵二叉树正确的先序、中序和后序数组。请分别
 * 用三个函数实现任意两种数组结合重构原来的二叉树，并返回重构二叉树的头节点。
 *
 * 解答：
 * 先序与中序结合重构二叉树的过程如下：
 * 1、先序数组中最左边的值就是树的头节点值，记为 h，并用 h 生成头节点，记为 head。
 * 然后在中序数组中找到 h，假设位置是 i。那么在中序数组中，i 左边的数组就是头节点左子
 * 树的中序数组，假设长度为 l，则左子树的先序数组就是先序数组中 h 往右长度也为 l 的数
 * 组。
 * 2、用左子树的先序和中序数组，递归整个过程建立左子树，返回的头节点记为 left。
 * 3、i 右边的数组就是头节点右子树的中序数组，假设长度为 r。先序数组中右侧等长的部分就
 * 是头节点右子树的先序数组。
 * 4、用右子树的先序和中序数组，递归建立右子树，返回的头节点记为 right。
 * 5、把 head 的左孩子和右孩子分别设为 left 和 right，返回 head，过程结束。
 * 如果二叉树的节点数为 N，在中序数组中找到位置 i 的过程可以用哈希表来实现，这样整个过
 * 程时间复杂度为 O(N)。
 *
 * 中序和后序重构的过程与先序和中序的过程类似。先序和中序的过程是用先序数组最左的值来对
 * 中序数组进行划分，因为这是头节点的值。后序数组中头节点的值是后序数组最右的值，所以用
 * 后序最右的值来划分中序数组即可。
 *
 * 先序和后序结合重构二叉树。这需要首先分析处节点值都不同的二叉树，即便得到了正确的先序
 * 与后序数组，在大多数情况下也不能通过这两个数组把原来的树重构出来。这是因为很多结构不
 * 同的树中，先序与后序数组是一样的。比如，头节点为 1、左孩子为 2、右孩子为 null 的树，
 * 先序数组为 [1, 2]，后序数组 [2, 1]。而头节点为 1、左孩子为 null、右孩子为 2 的树
 * 也是同样的结果。然后需要分析出什么样的树可以被先序和后序数组重建，如果一棵二叉树除了
 * 叶节点外，其它所有的节点都有左孩子和右孩子，只有这样的树才可以被先序和后序数组重构出
 * 来。最后才是通过划分左右子树各自的先序与后序数组的方式重建整棵树。
 *
 * @author Jiajing Li
 * @date 2019-03-31 12:13:26
 */
public class Main {

    /**
     * 重构后的二叉树为：
     *                       1
     *                /             \
     *           2                    3
     *      /         \            /      \
     *   4               5       6         7
     *                /    \
     *              8       9
     */
    public static void main(String[] args) {

        int[] preArr = new int[] {1, 2, 4, 5, 8, 9, 3, 6, 7};
        int[] inArr = new int[] {4, 2, 8, 5, 9, 1, 6, 3, 7};
        // 先中
        System.out.println(ToTree.preInToTree(preArr, inArr).value);
        int[] postArr = new int[] {4, 8, 9, 5, 2, 6, 7, 3, 1};
        // 中后
        System.out.println(ToTree.inPostToTree(inArr, postArr).value);
        // 先后
        System.out.println(ToTree.prePostToTree(preArr, postArr).value);
    }

}
