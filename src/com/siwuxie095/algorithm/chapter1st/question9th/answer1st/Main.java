package com.siwuxie095.algorithm.chapter1st.question9th.answer1st;

/**
 * 求最大子矩阵的大小
 *
 * 题目：
 * 给定一个整型矩阵 map（实际上就是一个二维数组，注意：这里是规则的二维数组），其中的值只有 0 和 1 两种，
 * 求其中全是 1 的所有矩形区域中，最大的矩形区域中 1 的数量。
 * 如：
 * 1 1 1 0
 * 其中，最大的矩形区域中 1 的数量为 3。
 * 再如：
 * 1 0 1 1
 * 1 1 1 1
 * 1 1 1 0
 * 其中，最大的矩形区域中 1 的数量为 6。
 *
 * 解答：
 * 如果矩阵的大小为 N * M（N 行 M 列），本题可以做到时间复杂度为 O(N * M)，具体如下：
 * 1、按照行做遍历，矩阵的行数为 N，以每一行对该矩阵做切割，统计以当前行为底，每个位置向上的 1 的数量。使用
 * 高度数组 height 来表示。
 * 如：
 * 1 0 1 1
 * 1 1 1 1
 * 1 1 1 0
 * 对于第一行，height 数组为 {1, 0, 1, 1}；
 * 对于第二行，height 数组为 {2, 1, 2, 2}；
 * 对于第三行，height 数组为 {3, 2, 3, 0}；
 * 显然，对于 height[j]，j 表示当前行第几列，height[j] 表示往上的过程中 1 的数量（如果当前行第几列是 0，
 * 则 height[j] = 0）。
 * 不难发现此规律：height[j] = (map[i][j] == 0 ? 0 : height[j] + 1)
 *
 * 2、对于每一次切割，都利用更新后的 height 数组（注意：height数组只有一个）计算出每一行为底的情况下，最大的
 * 矩形是哪个。一直切割到最后，做比较即可。
 *
 * 其中，关键的是步骤 2，如果 height 数组的长度为 M（列数），那么求解步骤 2 的过程可以做到时间复杂度为 O(M)。
 *
 * 对于 height 数组，其实可以理解为一个直方图，在直方图中求解最大矩形的面积即可。方法就是求出每一根柱子向两边
 * 延展出去的最大矩形。最大的就是我们想要的。
 * 以 {3, 2, 3, 0} 为例：
 * 1   1
 * 1 1 1
 * 1 1 1
 * 第一根柱子，高度为 3，向左无法延展，向右也无法延展，则以第一根柱子为高度的矩形面积就是 3 * 1 = 3；
 * 第二根柱子，高度为 2，向左可以延展 1，向右可以延展 1，则以第二根柱子为高度的矩形面积就是 2 * 3 = 6；
 * 第三根柱子，高度为 3，向左无法延展，向右也无法延展，则以第三根柱子为高度的矩形面积就是 3 * 1 = 3；
 * 第四根柱子，高度为 0，向左无法延展，向右也无法延展，则以第一根柱子为高度的矩形面积就是 0 * 1 = 0；
 * 所以这个直方图中的最大矩形面积就是 6。
 *
 * 考察每一根柱子最大能扩多大，实际上就是找到柱子左边刚比它小的柱子位置在哪，以及右边刚比它小的柱子位置
 * 在哪。这个过程怎么计算最快？用栈。
 *
 * 生成一个栈 stack，从左到右遍历该数组 height，每遍历一个位置，就会把该位置（索引）压入栈中。规则如下：
 * （1）如果栈为空，直接压入。
 *
 * （2）当前 i 位置的值 height[i] 大于栈顶位置所代表的值时，i 才能入栈。即 从栈顶到栈底，对应位置所代表
 * 的值，是依次递减且无重复的。
 *
 * （3）当前 i 位置的值 height[i] 小于等于栈顶位置所代表的值时，则弹栈，直到 height[i] 大于栈顶位置所
 * 代表的值时，i 才能入栈。这期间有如下处理：
 * 1）将当前弹栈的元素记为 j（即 当前弹出的栈顶元素），弹栈后，新的栈顶元素记为 k。然后判断 j 位置的柱子
 * 向左向右各能延展到哪里。
 * 2）先看向右：如果 height[i] < height[j],则 i - 1 位置就是向右能延展到的最远位置。因为 j 之所以被
 * 弹出，就是因为遇到了一个比位置 j 处值还小的位置 i。如果 height[i] = height[j]，则此时最起码能延展
 * 到 i 位置，最远能延展到多远，因为要再看下一个位置，所以暂时未知。但可以肯定的是，在此种情况下，i 位置向
 * 左也必然能延展到 j 位置，即 i 和 j 两根柱子延展出来的最大矩形是同一个。所以此时不再计算 j 位置能延展出
 * 的最大矩形，因为 i 肯定要压入栈中，看 i 即可。等 i 被弹栈时，才能计算出（即 对于相同高度的柱子，要看最后
 * 那一根才能计算出它对应的最大矩形面积）。
 * 3）再看向左：肯定是 k + 1 位置。因为 height[k+1 ... j-1] 之间不可能有 >= height[k]的值，否则 k 早
 * 就弹栈而出了。
 * 4）综上，j 位置能延展出的最大矩形面积就是 （(i-1) - (k+1) + 1）* height[j] = (i-k-1) * height[j]
 * 5）注意：当遍历结束，height数组中仍然有元素还没有经历向两边延展的过程（也就是说栈中还有元素），就不知道其对应
 * 的最大矩形面积是多少。
 * 此时，height 数组遍历结束，不能再向右延展，可以认为 i = height.length，即 有一个不存在的元素，且该元素无限小，
 * 也就是说它就是最终向右延展的边界。其实这个过程就是弹栈，计算出栈中元素对应的最大矩形。
 * 当栈为空时，整个过程结束。
 *
 * 注意：当栈为空时，可以认为 k = -1，即 有一个不存在的元素，且该元素无限小，也就是说它是最终向左延展的边界。
 *
 * @author Jiajing Li
 * @date 2019-02-09 21:55:59
 */
public class Main {

    public static void main(String[] args) {
        int[][] map = new int[][]{
                {0, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 1},
                {0, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1}
        };
        int maxArea = RectangleSize.maxRecSize(map);
        System.out.println("最大子矩阵的大小 = " + maxArea);
    }

}