package com.siwuxie095.algorithm.chapter5th.question10th.answer1st;

/**
 * 字符串的调整与替换
 *
 * 题目：
 * 给定一个字符类型的数组 charArr[]，charArr 右半区全是空字符，左半区不含有
 * 空字符。现在想将左半区中所有的空格字符替换成 "%20"，假设 charArr 右半区足
 * 够大，可以满足替换所需要的空间，请完成替换函数。
 *
 * 比如：
 * 如果把 charArr 的左半区看作字符串，为 "a b  c"，假设 charArr 的右半区足
 * 够大。替换后，charArr 的左半区为 "a%20b%20%20c"。
 *
 * 要求：
 * 替换函数的时间复杂度为 O(N)，额外空间复杂度为 O(1)。
 *
 * 补充题目：
 * 给定一个字符类型的数组 charArr[]，其中只含有数字字符和 "*" 字符。现在想把
 * 所有的 "*" 字符挪到 charArr 的左边，数字字符挪到 charArr 的右边。请完成
 * 调整函数。
 *
 * 比如：
 * 如果把 charArr 看作字符串，为 "12**345"。调整后 charArr 为 "**12345"。
 *
 * 要求：
 * 1、调整函数的时间复杂度为 O(N)，额外空间复杂度为 O(1)。
 * 2、不得改变数字字符从左到右出现的顺序。
 *
 * 解答：
 * 原问题。遍历一遍可以得到两个信息，charArr 的左半区有多大，记为 len，左半区
 * 的空格数有多少，记为 num，那么可知空格字符被 "%20" 替代后，长度将是 len +
 * 2*num。接下来从左半区的最后一个字符开始倒着遍历，同时将字符复制到新长度最后
 * 的位置，并依次向左倒着复制。遇到空格字符就依次把 "0"、"2"、"%" 进行复制。这
 * 样就可以得到替换后的 charArr 数组。具体代码请参看 replace() 方法。
 *
 * 补充问题。依然是从右向左倒着复制，遇到数字字符则直接复制，遇到 "*" 字符不复制。
 * 当把数字字符复制完，把左半区全部设置成 "*" 即可。具体代码请参看 modify() 方
 * 法。
 *
 * 以上两道题目都是利用倒着复制这个技巧，其实很多字符串问题也和这个小技巧有关。
 *
 * @author Jiajing Li
 * @date 2019-04-17 14:33:54
 */
public class Main {

    public static void main(String[] args) {
        // 其中 \0 即为空字符
        ReplaceAndModify.replace("a b  c\0\0\0\0\0\0\0\0".toCharArray());
        ReplaceAndModify.modify("12**345".toCharArray());
    }

}
