package algorithm.chapter8th.question21th.answer1st;

/**
 * 边界都是 1 的最大正方形大小
 *
 * 题目：
 * 给定一个 N*N 的矩阵 matrix，在这个矩阵中，只有 0 和 1 两种值，返回边框全是 1 的最大正方形
 * 的边长长度。
 * 比如：
 * 0    1   1   1   1
 * 0    1   0   0   1
 * 0    1   0   0   1
 * 0    1   1   1   1
 * 0    1   0   1   1
 * 其中，边框全是 1 的最大正方形的大小为 4*4，所以返回 4。
 *
 * 解答：
 * 先介绍一个比较容易理解的解法：
 * 1、矩阵中一共有 N*N 个位置。O(N^2)
 * 2、对每一个位置都看是否可以成长为 N～1 的正方形左上角。比如，对于 (0, 0) 位置，依次检查是否
 * 是边长为 5 的正方形左上角，然后检查边长为 4、3 等。O(N)
 * 3、如何检查一个位置是否可以成为边长为 N 的正方形的左上角呢？遍历这个边长为 N 的正方形边界看是
 * 否只由 1 构成，也就是走过 4 个边的长度（4N）。O(N)
 * 所以普通方法的总的时间复杂度为 O(N^2)*O(N)*O(N) = O(N^4)。
 * 这里提供的方法的时间复杂度为 O(N^3)，基本过程也是如上三个步骤。但是对于步骤 3，可以时间复杂度
 * 由 O(N) 降为 O(1)。具体地说，就是能够在 O(1) 的时间内检查一个位置假设为 (i, j)，是否可以作
 * 为边长为 a(1<=a<=N)的边界全是 1 的正方形左上角。关键是使用预处理技巧，这也是面试经常使用的技
 * 巧之一，下面介绍得到预处理矩阵的过程。
 * 1、预处理过程是根据矩阵 matrix 得到两个矩阵 right 和 down。right[i][j] 的值表示从位置 (i,
 * j) 出发向右，有多少个连续的 1。down[i][j] 的值表示从位置 (i, j) 出发向下有多少个连续的 1。
 * 2、right 和 down 矩阵如何计算？
 * 1）从矩阵的右下角 (n-1, n-1) 位置开始计算，如果 matrix[n-1][n-1] == 1，那么，right[n-1]
 * [n-1] 且 down[n-1][n-1] = 1，否则都等于 0。
 * 2）从右下角开始往上计算，即在 matrix 最后一列上计算，位置就表示为 (i, n-1)。对 right 矩阵
 * 来说，最后一列的右边没有内容，所以，如果 matrix[i][n-1] == 1，因为 down[i+1][n-1] 表示包
 * 括位置 (i+1, n-1) 在内并往下有多少个连续的 1，所以，如果位置 (i, n-1) 是 1，那么，令 down
 * [i][n-1] = down[i+1][n-1] + 1；如果 matrix[i][n-1] == 0，则令 down[i][n-1] = 0。
 * 3）从右下角开始往左计算，即在 matrix 最后一行上计算，位置可以表示为 (n-1, j)。对 right 矩阵
 * 来说，如果 matrix[n-1][j] == 1，因为 right[n-1][j+1] 表示包括位置 (n-1, j+1) 在内右边有
 * 多少个连续的 1。所以，如果位置 (n-1, j) 是 1，则令 right[n-1][j] == right[n-1][j+1] + 1；
 * 如果 matrix[n-1][j] == 0，则令 right[n-1][j] == 0。对 down 矩阵来说，最后一列的下边没有
 * 内容，所以，如果 matrix[n-1][j] == 1，令 down[n-1][j] = 1，否则为 0。
 * 4）计算完步骤 1）～3）之后，剩下的位置都是既有右，也有下，假设位置表示为 (i, j)：
 * 如果 matrix == 1，则令 right[i][j] = right[i][j+1] + 1，down[i][j] = down[i+1][j] + 1。
 * 如果 matrix == 0，则令 right[i][j] = 0，down[i][j] = 0。
 * 预处理的具体过程请参看 setBorderMap() 方法。
 *
 * 得到 right 和 down 矩阵后，如何加速检查过程呢？比如现在想检查一个位置，假设为 (i, j)。是否可以
 * 作为边长 a(1<=a<=N)的边界全为 1 的正方形左上角。
 * 1）位置 (i, j) 的右边和下边连续为 1 的数量必须都大于或等于 a(right[i][j]>=a && down[i][j]
 * >=a)，否则说明上边界和左边界的 1 不够。
 * 2）位置 (i, j) 向右跳到位置 (i, j+a-1)，这个位置是正方形的右上角，那么这个位置的下边连续为 1
 * 的数量也必须大于或等于 a(down[i][j+a-1]>=a)，否则说明右边界的 1 不够。
 * 3）位置 (i, j) 向下跳到位置 (i+a-1, j)，这个位置是正方形的左下角，那么这个位置的右边连续为 1
 * 的数量也必须大于或等于 a(right[i+a-1][j]>=a)，否则说明下边界的 1 不够。
 * 以上三个条件都满足时，就说明位置 (i, j) 符合要求，利用 right 和 down 矩阵之后，加速的过程很
 * 明显，不需要遍历边长上的所有值了，只看 4 个点即可。
 * 全部过程请参看 getMaxSize() 方法。
 *
 * @author Jiajing Li
 * @date 2019-06-10 16:42:23
 */
public class Main {

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 1, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 1, 1, 1, 1},
                {0, 1, 0, 1, 1}
        };
        System.out.println(Size.getMaxSize(matrix));
    }

}
