package algorithm.chapter4th.question13th.answer1st;

/**
 * 表达式得到期望结果的组成种数
 *
 * 题目：
 * 给定一个只由 0（假）、1（真）、&（逻辑与）、|（逻辑或）、和 ^（异或）五种字符组成的字符串 express，
 * 再给定一个布尔值 desired。返回 express 能有多少种组合方式，可以达到 desired 的结果。
 *
 * 比如：
 * express = "1^0|0|1"，desired = false。
 * 只有 1^((0|0)|1) 和 1^(0|(0|1)) 的组合可以得到 false，返回 2。
 * express = "1"，desired = false。
 * 无组合则可以得到 false，返回 0。
 *
 * 解答：
 * 应该首先判断 express 是否合乎题目要求，比如 "1^" 和 "10"，都不是有效的表达式。总结起来有以下三个
 * 判断标准：
 * （1）表达式的长度必须是奇数。
 * （2）表达式下标为偶数位置的字符一定是 '0' 或者 '1'。
 * （3）表达式下标为奇数位置的字符一定是 '&'、'|' 或者 '^'。
 * 只要符合上述三个标准，表达式必然是有效的。具体代码请参看 isValid() 方法。
 *
 * 暴力递归方法。在判断 express 符合标准之后，将 express 划分成左右两个部分，求出各种划分的情况下，
 * 能得到 desired 的种数是多少。以本题的例子说明， express = "1^0|0|1"，desired = false，总的
 * 种数求法如下：
 * （1）第一个划分为 '^'，左部分为 "1"，右部分为 "0|0|1"，因为当前划分的逻辑符号为 ^，所以要想在此
 * 划分下得到 false，包含的可能性有两种：左部分为真，右部分为真；左部分为假，右部分为假。
 * 结果 1 = 左部分为真的种数 * 右部分为真的种数 + 左部分为假的种数 * 右部分为假的种数。
 * （2）第二个划分为 "|"，左部分为 "1^0"，右部分为 "0|1"，因为当前划分的逻辑符号为 |，所以要想在此
 * 划分下得到 false，包含的可能性只有一种：左部分为假，右部分为假。
 *  * 结果 2 =  左部分为假的种数 * 右部分为假的种数。
 *  （3）第二个划分为 "|"，左部分为 "1^0|0"，右部分为 "1"，因为当前划分的逻辑符号为 |，所以
 * 结果 3 =  左部分为假的种数 * 右部分为假的种数。
 * （4）结果 1 + 结果 2 + 结果 3 就是总的种数，也就是说，一个字符串中有几个逻辑符号，就有多少种划分，
 * 把每种划分能够得到最终 desired 值的种数全加起来，就是总的种数。
 *
 * 现在来系统地总结一下划分符号和 desired 地情况。
 * （1）划分符号为 ^，desired 为 true 的情况下：
 * 种数 = 左部分为真的种数 * 右部分为假的种数 + 左部分为假的种数 * 右部分为真的种数。
 *（2）划分符号为 ^，desired 为 false 的情况下：
 * 种数 = 左部分为真的种数 * 右部分为真的种数 + 左部分为假的种数 * 右部分为假的种数。
 *（3）划分符号为 &，desired 为 true 的情况下：
 * 种数 = 左部分为真的种数 * 右部分为真的种数。
 *（4）划分符号为 &，desired 为 false 的情况下：
 * 种数 = 左部分为真的种数 * 右部分为假的种数 + 左部分为假的种数 * 右部分为真的种数 + 左部分为假的种数 * 右部分为假的种数。
 * （5）划分符号为 |，desired 为 true 的情况下：
 * 种数 = 左部分为真的种数 * 右部分为假的种数 + 左部分为假的种数 * 右部分为真的种数 + 左部分为真的种数 * 右部分为真的种数。
 * （6）划分符号为 |，desired 为 false 的情况下：
 * 种数 = 左部分为假的种数 * 右部分为假的种数。
 *
 * 根据如上总结，以  express 中的每一个逻辑符号来划分 express，每种划分都求出各自的种数，再把种数累加
 * 起来，就是 express 达到 desired 总的种数。每次划分出的左右两部分递归求解即可。
 * 具体代码请参看 num1() 方法。
 *
 * 一个长度为 N 的 express，假设计算 express[i..j] 的过程记为 p(i, j)，那么计算 p(0, N-1) 需要计
 * 算 p(0, 0) 与 p(1, N-1)、p(0, 1) 与 p(2, N-1) ... p(0, i) 与 p(i+1, N-1) ... p(0, N-2)
 * 与 p(N-1, N-1)，起码 2N 种状态。对于每一组 p(0, i) 与 p(i+1, N-1) 来说，两者相加的划分种数又是
 * N-1 种，所以起码要计算 2(N-1) 种状态。所以用 num1() 方法来计算一个长度为 N 的 express，总的时间
 * 复杂度为 O(N!)，额外空间复杂度为 O(N)，因为函数栈的大小为 N。之所以用暴力递归方法的时间复杂度这么高，
 * 是因为每一种状态计算过后没有保存下来，导致重复计算大量发生。
 *
 * 动态规划的方法。如果 express 长度为 N，生成两个 N*N 的矩阵 t 和 f，t[j][i] 表示 express[j..i]
 * 组成 true 的种数，f[j][i] 表示 express[j..i] 组成 false 的种数。t[j][i] 和 f[j][i] 的计算方
 * 式还是枚举 express[j..i] 上的每种划分。具体代码请参看 num2() 方法。
 *
 * 矩阵 t 和 f 的大小为 N*N，每个位置在计算的时候都有枚举的过程，所以动态规划方法的时间复杂度为 O(N^3)，
 * 额外空间复杂度为 O(N^2)。
 *
 * @author Jiajing Li
 * @date 2019-04-07 23:16:56
 */
public class Main {

    public static void main(String[] args) {
        String express = "1^0|0|1";
        System.out.println(Number.num1(express, true));
        System.out.println(Number.num2(express, true));
        System.out.println();
        System.out.println(Number.num1(express, false));
        System.out.println(Number.num2(express, false));
    }

}
