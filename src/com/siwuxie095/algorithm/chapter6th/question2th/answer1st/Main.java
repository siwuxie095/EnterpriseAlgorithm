package com.siwuxie095.algorithm.chapter6th.question2th.answer1st;

/**
 * 只用 2GB 内存在 20 亿个整数中找到出现次数最多的数
 *
 * 题目：
 * 有一个包含 20 亿个全是 32 位整数的大文件，在其中找到出现次数最多的数。
 *
 * 要求：
 * 内存限制为 2GB。
 *
 * 解答：
 * 想要在很多整数中找到出现次数最多的数，通常的做法是使用哈希表对出现的每一个数做词频统计，哈希表的 key 是某一个整数，
 * value 是这个数出现的次数。就本题来说，一共有 20 亿个数，哪怕只是 1 个数出现了 20 亿次，用 32 位的整数也可以表示
 * 其出现的次数而不会产生溢出，所以哈希表的 key 需要占用 4B，value 也是 4B。那么哈希表的一条记录 (key, value) 需
 * 要占用 8B，当哈希表记录数为 2 亿个时，需要至少 1.6GB 的内存。
 * 但如果 20 亿个数中不同的数超过 2 亿种，最极端的情况是 20 亿个数都不同，那么在哈希表中可能需要产生 20 亿条记录，这
 * 样内存会不够用，所以一次性用哈希表统计 20 亿个数的办法有很大风险。
 * 解决办法是把包含 20 亿个数的大文件用哈希函数分成 16 个小文件，根据哈希函数的性质，同一种数不可能被哈希到不同的小文
 * 件上，同时每个小文件中不同的数一定不会大于 2 亿种，假设哈希函数足够好。然后对每一个小文件用哈希表来统计其中每种数出
 * 现的次数，这样我们就得到了 16 个小文件中各自出现次数最多的数，还有各自的次数统计。接下来只要选出这 16 个小文件各自
 * 的第一名中谁出现的次数最多即可。
 * 把一个大集合通过哈希函数分配到多台机器中，或者分配到多个文件里，这种技巧是处理大数据面试题时最常用的技巧之一。但是到
 * 底分配到多少台机器、分配到多少文件，在解题时一定要确定下来。可能是在与面试官沟通的过程中由面试官指定，也可能是根据具
 * 体的限制来确定，比如本题确定分成 16 个文件，就是根据内存显示 2GB 的条件来确定的。
 *
 * @author Jiajing Li
 * @date 2019-05-16 09:38:59
 */
public class Main {

}
