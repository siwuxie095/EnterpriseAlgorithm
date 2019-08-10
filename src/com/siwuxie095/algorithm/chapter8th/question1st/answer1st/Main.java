package com.siwuxie095.algorithm.chapter8th.question1st.answer1st;

/**
 * 转圈打印矩阵
 *
 * 题目：
 * 给定一个整型矩阵 matrix，请按照转圈的方式打印它。
 *
 * 例如：
 * 1    2   3   4
 * 5    6   7   8
 * 9    10  11  12
 * 13   14  15  16
 * 打印结果为：1、2、3、4、8、12、16、15、14、13、9、5、6、7、11、10。
 *
 * 要求：
 * 额外空间复杂度为 O(1)。
 *
 * 解答：
 * 本题在算法上没有难度，关键在于设计一种逻辑容易理解、代码易于实现的转圈遍历方式。这里介绍
 * 这样一种矩阵处理方式，该方式不仅可用于这道题，还适合很多其他的面试题，就是矩阵分圈处理。
 * 在矩阵中用左上角的坐标 (tR, tC) 和右下角的坐标 (dR, dC) 就可以表示一个子矩阵，比如，
 * 题目中的矩阵，当 (tR, tC) = (0, 0)，(dR, dC) = (3, 3) 时，表示的子矩阵就是整个矩阵，
 * 那么这个子矩阵最外层的部分如下：
 * 1    2   3   4
 * 5            8
 * 9            12
 * 13   14  15  16
 * 如果能把这个子矩阵的外层转圈打印出来，那么在 (tR, tC) = (0, 0)，(dR, dC) = (3, 3)
 * 时，打印的结果为：1、2、3、4、8、12、16、15、14、13、9、5。接下来令 tR 和 tC 加 1，
 * 即 (tR, tC) = (1, 1)，令 dR 和 dC 减 1，即 (dR, dC) = (2, 2)，此时表示的子矩阵
 * 如下：
 * 6    7
 * 10   11
 * 再把这个子矩阵转圈打印出来，结果为：6、7、11、10。把 tR 和 tC 加 1，即 (tR, tC) =
 * (2, 2)，令 dR 和 dC 减 1，即 (dR, dC) = (1, 1)。如果发现左上角坐标跑到了右下角坐标
 * 的右方或下方，整个过程就停止。已经打印的所有结果连起来就是要求的打印结果。具体代码请参看
 * spiralOrderPrint() 方法，其中 printEdge() 方法是转圈打印一个子矩阵的外层。
 *
 * @author Jiajing Li
 * @date 2019-05-19 20:30:04
 */
public class Main {

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Print.spiralOrderPrint(matrix);
    }

}