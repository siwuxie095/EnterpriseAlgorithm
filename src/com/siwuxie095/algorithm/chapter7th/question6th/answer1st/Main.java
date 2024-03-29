package com.siwuxie095.algorithm.chapter7th.question6th.answer1st;

/**
 * 在其他数都出现 k 次的数组中找到只出现一次的数
 *
 * 题目：
 * 给定一个整型数组 arr 和一个大于 1 的整数 k。已知 arr 中只有一个数出现了 1 次，其他的数都出现了 k 次，
 * 请返回只出现了 1 次的数。
 *
 * 要求：
 * 时间复杂度为 O(N)，额外空间复杂度为 O(1)。
 *
 * 解答：
 * 以下的例子是两个七进制数的无进位相加，即忽略进位的相加，比如：
 * 七进制数 a：    6 4 3 2 6 0 1
 * 七进制数 b：    3 4 5 0 1 1 1
 * 无进位相加结果： 2 1 1 2 0 1 2
 * 可以看出，两个七进制的数 a 和 b，在 i 位上无进位相加的结果就是 (c(i)+d(i))%k。那么，如果 k 个相同的
 * k 进制数进行无进位相加，相加的结果一定是每一位上都是 0 的 k 进制数。
 * 理解了上述过程之后，解这道题就变得简单了，首先设置一个变量 eO，它是一个 32 位的 k 进制数，且每个位置上
 * 都是 0。然后遍历 arr，把遍历到的每一个整数都转换为 k 进制数，然后与 eO 进行无进位相加。遍历结束时，把
 * 32 位的 k 进制数 eORes 转换为十进制整数，就是想要的结果。因为 k 个相同的 k 进制数无进位相加，结果一定
 * 是每一位上都是 0 的 k 进制数，所以只出现一次的那个数最终就会剩下来。具体代码请参看 onceNum() 方法。
 *
 * @author Jiajing Li
 * @date 2019-05-19 17:34:33
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 2, 2, 2, 3};
        int k = 3;
        System.out.println(Num.onceNum(arr, k));
    }

}
