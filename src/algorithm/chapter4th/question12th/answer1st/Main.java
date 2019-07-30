package algorithm.chapter4th.question12th.answer1st;

/**
 * 数字字符串转换为字母组合的种数
 *
 * 题目：
 * 给定一个字符串 str，str 全部由数字字符组成，如果 str 中某一个或某相邻两个字符组成的子串值
 * 在 1～26 之间，则这个子串可以转换为一个字母。规定 "1" 转换为 "A"，"2" 转换为 "B"，"3"
 * 转换为 "C" ... "26" 转换为 "Z"。写一个函数，求 str 有多少种不同的转换结果，并返回种数。
 *
 * 比如：
 * str = "1111"。
 * 能转换出的结果有 "AAAA"、"LAA"、"ALA"、"AAL" 和 "LL"，返回 5。
 * str = "01"。
 * "0" 没有对应的字母，而 "01" 根据规定不可转换，返回 0。
 * str = "10"。
 * 能转换出的结果是 "J"，返回 1。
 *
 * 解答：
 * 暴力递归的方法。假设 str 的长度为 N，先定义递归函数 p(i)，其中 0 <= i <= N。p(i) 的含义
 * 是 str[0..i-1] 已经转换完毕，而 str[i..N-1] 还没有转换的情况下，最终合法的转换种数有多少
 * 并返回。特别指出，p(N) 表示 str[0..N-1]（也就是 str 的整体）都已经转换完，没有后续的字符了，
 * 那么合法的转换种数为 1，即 p(N) = 1。比如，str = "111123"，p(4) 表示 str[0..3]（即 "1111"）
 * 已经转换完毕，具体结果是什么不重要，反正已经转换完毕并且不可改变，没转换的部分是 str[4..5]（即 "23"），
 * 可转换的为 "BC" 或 "W" 只有两种，所以 p(4) = 2。p(6) 表示 str 整体已经转换完毕，所以 p(6)
 * = 1。那么 p(i) 如何计算呢？只有以下 4 种情况。
 * （1）如果 i == N。根据上文对 p(N) = 1 的解释，直接返回 1。
 * （2）如果不满足情况（1），又有 str[i] == '0'。str[0..i-1] 已经转换完毕，而 str[i..N-1]
 * 此时又以 '0' 开头，str[i..N-1] 无论怎样都不可能合法转换，所以直接返回 0。
 * （3）如果不满足情况（1）和情况（2），说明 str[i] 属于 '1'～'9'，str[i] 可以转换成 'A'~'I'，
 * 那么 p(i) 的值一定包含 p(i+1) 的值，即 p(i) = p(i+1)。
 * （4）如果不满足情况（1）和情况（2），说明 str[i] 属于 '1'～'9'，如果又有 str[i..i+1] 在 '10'
 * ～'26' 之间，str[i..i+1] 可以转换为 'J'~'Z'，那么 p(i) 的值一定也包含 p(i+2) 的值，即 p(i)
 * += p(i+2)。
 * 具体代码请参看 num1() 方法。
 *
 * 以上过程中，p(i) 最多会有两个递归分支 p(i+1) 和 p(i+2)，一共有 N 层递归，所以时间复杂度为
 * O(2^N)，额外空间复杂度就是递归使用的函数栈的大小为 O(N)。但是研究一下递归函数 p(i) 就会发现，
 * p(i) 最多依赖 p(i+1) 和 p(i+2) 的值，这是可以从后往前进行顺序计算的，也就是先计算 p(N) 和
 * p(N-1)，然后根据这两个值计算 p(N-2)，再根据 p(N-1) 和 p(N-2) 计算 p(N-3)，最后根据 p(1)
 * 和 p(2) 计算出 p(0) 即可，类似斐波那契数列的求解过程，只不过斐波那契数列是从前往后计算的，这
 * 里是从后往前计算而已。具体代码请参看 num2() 方法。
 *
 * 因为是顺序计算，所以 num2() 方法的时间复杂度为 O(N)，同时只用了 curr、next、tmp 进行滚动
 * 更新，所以额外空间复杂度为 O(1)。但是本题不像斐波那契数列问题那样用矩阵乘法的优化方法将时间复
 * 杂度优化到 O(logN)，这是因为斐波那契数列是严格的 f(i) = f(i-1) + f(i-2)，但是本题并不严
 * 格，str[i] 的具体情况决定了 p(i) 是等于 0 还是等于 p(i+1)，还是等于 p(i+1) + p(i+2)。
 * 有状态转移的表达式不可以用矩阵乘法将时间复杂度优化到 O(logN)。但是如果 str 只由字符 '1' 和
 * '2' 组成，比如 "12121121212122"，那么就可以使用矩阵乘法的方法将时间复杂度优化为 O(logN)。
 * 因为 str[i] 都可以单独转换成字母，str[i..i+1] 也都可以一起转换成字母，此时一定有 p(i) =
 * p(i+1) + p(i+2)。总之，可以使用矩阵乘法的前提是递归表达式不会发生转移。
 *
 * @author Jiajing Li
 * @date 2019-04-07 21:05:49
 */
public class Main {

    public static void main(String[] args) {
        String str = "1111";
        System.out.println(Number.num1(str));
        System.out.println(Number.num2(str));
    }

}
