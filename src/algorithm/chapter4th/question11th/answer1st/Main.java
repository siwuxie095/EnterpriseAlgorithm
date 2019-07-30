package algorithm.chapter4th.question11th.answer1st;

/**
 * 龙与地下城游戏问题
 *
 * 题目：
 * 给定一个二维数组 map，含义是一张地图，例如，如下矩阵：
 * -2      -3      3
 * -5      -10     1
 * 0       30      -5
 * 游戏规则如下：
 * （1）骑士从左上角出发，每次只能向右或向下走，最后到达右下角见到公主。
 * （2）地图中的每个位置的值代表骑士要遭遇的事情。如果是负数，说明此处有怪兽，要
 * 让骑士损失血量。如果是非负数，代表此处有血瓶，能让骑士回血。
 * （3）骑士从左上角到右下角的过程中，走到任何一个位置时，血量都不能少于 1。
 * 为了保证骑士能见到公主，初始血量至少是多少？根据 map，返回初始血量。
 *
 * 解答：
 * 先介绍经典动态规划的方法，定义和地图大小一样的矩阵，记为 dp，dp[i][j] 的含义
 * 是如果骑士要走上位置 (i, j)，并且从该位置选一条最优的路径，最后走到右下角，骑
 * 士起码应该具备的血量。根据 dp 的含义，最终需要的是 dp[0][0] 的结果。以题目的
 * 例子来说，map[2][2] 的值为 -5，所以骑士若要走上这个位置，需要 6 点血才能让自
 * 己不死。同时位置 (2, 2) 已经是最右下角的位置，即 没有后续的路径，所以 dp[2][2]
 * == 6。
 * 那么 dp[i][j] 的值应该怎么计算呢？
 * 骑士还要面临向下还是向右的选择，dp[i][j+1] 是骑士选择当前向右走并最终达到右下
 * 角的血量要求。同理，dp[i+1][j] 是向下走的要求。如果骑士决定向右走，那么骑士在
 * 当前位置加完血或者扣完血之后的血量只要等于 dp[i][j+1] 即可。那么骑士在加血或
 * 扣血之前的血量要求（也就是在没有踏上 (i, j) 位置之前的血量要求），就是
 * dp[i][j+1] - map[i][j]。同时，骑士血量要求随时不少于 1，所以向右的要求为
 * max{dp[i][j+1] - map[i][j], 1}。如果骑士决定向下走，分析方式相同，向下的
 * 要求为 max{dp[i+1][j] - map[i][j], 1}。
 * 骑士可以有两种选择，当然要选最优的一条，所以 dp[i][j] = min{向右的要求，
 * 向下的要求}。计算 dp 矩阵时从右下角开始计算，选择依次从右至左、再从下至上
 * 的计算方式即可。具体代码请参看 minHP1() 方法。
 *
 *
 * 如果 map 大小为 M * N，经典动态规划方法的时间复杂度为 O(M * N)，额外空间
 * 复杂度为 O(M * N)。结合空间压缩之后，可以将额外空间复杂度降至 O(min{M, N})。
 * 具体代码请参看 minHP2() 方法。
 *
 * @author Jiajing Li
 * @date 2019-04-07 20:23:27
 */
public class Main {

    public static void main(String[] args) {
        int[][] m = new int[][] {
                {-2, -3, 3},
                {-5, -10, 1},
                {0, 30, -5}
        };
        System.out.println(HitPoint.minHP1(m));
        System.out.println(HitPoint.minHP2(m));
    }

}
