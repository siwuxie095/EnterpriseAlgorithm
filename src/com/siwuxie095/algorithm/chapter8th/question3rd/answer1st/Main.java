package com.siwuxie095.algorithm.chapter8th.question3rd.answer1st;

/**
 * "之" 字形打印矩阵
 *
 * 题目：
 * 给定一个矩阵 matrix，按照 "之" 字形的方式打印这个矩阵。
 *
 * 比如：
 * 1    2   3   4
 * 5    6   7   8
 * 9    10  11  12
 * "之" 字形打印的结果为：1、2、5、9、6、3、4、7、10、11、8、12。
 *
 * 要求：额外空间复杂度为 O(1)。
 *
 * 解答：
 * 这里提供的实现方式是这样处理的：
 * 1、上坐标 (tR, tC) 初始为 (0, 0)，先沿着矩阵第一行移动（tC++），当到达第一行最右边的元素后，
 * 再沿着矩阵最后一列移动（tR++）。
 * 2、下坐标 (dR, dC) 初始为 (0, 0)，先沿着矩阵第一列移动（dR++），当到达第一列最下边的元素后，
 * 再沿着矩阵最后一行移动（dC++）。
 * 3、上坐标与下坐标同步移动，每次移动后的上坐标与下坐标的连线就是矩阵中的一条斜线，打印斜线上的元
 * 素即可。
 * 4、如果上次斜线是从左下向右上打印的，这次一定是从右上向左下打印，反之亦然。总之，可以把打印的方
 * 向用 boolean 值表示，每次取反即可。
 *
 * @author Jiajing Li
 * @date 2019-05-21 22:41:38
 */
public class Main {

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        Print.printMatrixZigZag(matrix);
    }

}
