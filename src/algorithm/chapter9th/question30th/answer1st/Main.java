package algorithm.chapter9th.question30th.answer1st;

/**
 * Manacher 算法
 *
 * 题目：
 * 给定一个字符串 str，返回 str 中最长回文子串的长度。
 *
 * 比如：
 * str = "123"，其中的最长回文子串为 "1"、"2" 或者 "3"，所以返回 1。
 * str = "abc1234321ab"，其中的最长回文子串为 "1234321"，所以返回 7。
 *
 * 进阶题目：
 * 给定一个字符串 str，想通过添加字符的方式使得 str 整体都变成回文字符串，但要求只能在 str 的末尾
 * 添加字符，请返回在 str 后面添加的最短字符。
 *
 * 比如：
 * str = "12"。在末尾添加 "1" 之后，str 变为 "121"，是回文串。在末尾添加 "21" 之后，str 变为
 * "1221"，也是回文串。但 "1" 是所有添加方案中最短的，所以返回 "1"。
 *
 * 要求：
 * 如果 str 的长度为 N，解决原问题和进阶问题的时间复杂度都达到 O(N)。
 *
 * 解答：
 * 本文的重点是介绍 Manacher 算法，该算法是由 Glenn Manacher 于 1975 年首次发明的。Manacher
 * 算法解决的问题是在线性时间内找到一个字符串的最长回文子串，比起能够解决该问题的其他算法，Manacher
 * 算法算比较好理解和实现的。
 * 先来说一个很好理解的方法。从左到右遍历字符串，遍历到每个字符的时候，都看看以这个字符作为中心能够
 * 产生多大的回文字符串。比如 str = "abacaba"，以 str[0] == 'a' 为中心的回文字符串最大长度为 1，
 * 以 str[1] == 'b' 为中心的回文字符串最大长度为 3，...... 其中最大的回文字符串是以 str[3] ==
 * 'c' 为中心的时候。这种方法非常容易理解，只要解决奇回文和偶回文寻找方式的不同就可以。比如 "121"
 * 是奇回文，有确定的轴 '2'。"1221" 是偶回文，没有确定的轴，回文的虚轴在 "22" 中间。但是这种方法
 * 有明显的问题，之前遍历过的字符完全无法指导后面遍历的过程，也就是对每个字符来说都是从自己的位置出
 * 发，往左右两个方向扩出去检查。这样，对每个字符来说，往外扩的代价都是一个级别的。举一个极端的例子
 * "aaaaaaaaaaaaaaa"（15 个 a），对每一个 'a' 来讲，都是扩到边界才停止。所以每一个字符扩出去检
 * 查的代价都是 O(N)，所以总的时间复杂度为 O(N^2)。Manacher 算法可以做到 O(N) 的时间复杂度，精
 * 髓是之前字符的 "扩" 过程，可以指导后面字符的 "扩" 过程，使得每次的 "扩" 过程不都是从无开始。以
 * 下是 Manacher 算法解决原问题的过程：
 * 1、因为奇回文和偶回文在判断时比较麻烦，所以对 str 进行处理，把每个字符开头、结尾和中间插入一个特
 * 殊字符 '#' 来得到一个新的字符串数组。比如 str = "bcbaa"，处理后为 "#b#c#b#a#a#"，然后从每个
 * 字符左右扩出去的方式找最大回文子串就方便多了。对奇回文来说，不这么处理也能通过扩的方式找到，比如
 * "bcb"，从 'c' 开始向左右两侧扩出去能找到最大回文。处理后为 "#b#c#b#"，从 'c' 开始向左右两侧扩
 * 出去依然能找到最大回文。对偶回文来说，不处理而直接通过扩的方式是找不到的，比如 "aa"，因为没有确
 * 定的轴，但是处理后为 "#a#a#"，就可以通过从中间的 '#' 扩出去的方式找到最大回文。所以通过这样的
 * 处理方式，最大回文子串无论是偶回文还是奇回文，都可以通过统一的 "扩" 过程找到，解决了差异性的问题。
 * 同时要说的是，这个特殊字符是什么无所谓，甚至可以是字符串中出现的字符，也不会影响最终的结果，就是
 * 一个纯辅助的作用。具体代码请参看 manacherString() 方法。
 * 2、假设 str 处理之后的字符串记为 charArr。对每个字符（包括特殊字符）都进行 "优化后" 的扩过程。
 * 在介绍 "优化后" 的扩过程之前，先解释如下三个辅助变量的意义。
 * （1）数组 pArr。长度与 charArr 长度一样。pArr[i] 的意义是以 i 位置上的字符（charArr[i]）作
 * 为回文中心的情况下，扩出去得到的最大回文半径是多少。举个例子来说明，对 "#c#a#b#a#c#" 来说，pArr
 * [0..9] 为 [1, 2, 1, 2, 1, 6, 1, 2, 1, 2, 1]。我们的整个过程就是在从左到右遍历的过程中，依
 * 次计算每个位置的最大回文半径值。
 * （2）整数 pR。这个变量的意义是之前遍历的所有字符的所有回文半径中，最右即将到达的位置。还是以 "#c
 * #a#b#a#c#" 为例来说，还没遍历之前 pR，初始设置为 -1。charArr[0] = '#' 的回文半径为 1，所以
 * 目前回文半径向右只能扩到位置 0，回文半径最右即将到达的位置变为 1（pR = 1）。charArr[1] = '#'
 * 的回文半径为 2，此时所有的回文半径向右能扩到位置 2，所以回文半径最右即将到达的位置变为 3（pR = 3）。
 * charArr[2] = '#' 的回文半径为 1，所以位置 2 向右只能扩到位置 2，回文半径最右即将到达的位置不变，
 * 仍是 3（pR = 3）。charArr[3] = 'a' 的回文半径为 2，所以位置 3 向右能扩到位置 4，所以回文半径
 * 最右即将到达的位置变为 5（pR = 5）。charArr = '#' 的回文半径为 1，所以位置向右只能扩到位置 4，
 * 回文半径最右即将到达的位置不变仍是 5（pR = 5）。charArr[5] = 'b' 的回文半径为 6，所以位置 4
 * 向右能扩到位置 10，回文半径最右即将到达的位置变为 11（pR = 11）。此时已经到达整个字符数组的结尾，
 * 所以之后的过程中 pR 将不再变化。换句话说，pR 就是遍历过的所有字符中向右扩出来的最大右边界。只要右
 * 边界更往右，pR 就更新。
 * （3）整数 index。这个变量表示最近一次 pR 更新时，那个回文中心的位置。以刚刚的例子来说，遍历到
 * charArr[0] 时 pR 更新，index 就更新为 0。遍历到 charArr[1] 时 pR 更新，index 就更新为 1
 * ...... 遍历到 charArr[5] 时 pR 更新，index 就更新为 5。之后的过程中，pR 将不再更新，所以 index
 * 将一直是 5。
 * 3、只要能够从左到右依次算出数组 pArr 每个位置的值，最大的那个值实际上就是处理后的 charArr 中最
 * 大的回文半径，根据最大的回文半径，再对应回原字符的话，整个问题就解决了。步骤 3 就是从左到右依次计
 * 算出 pArr 数组每个位置的值的过程。
 * 1）假设现在计算到位置 i 的字符 charArr[i]，在 i 之前位置的计算过程中，都会不断地更新 pR 和 index
 * 的值，即位置 i 之前的 index 这个回文中心扩出了一个目前最右的回文边界 pR。
 * 2）如果 pR-1 位置没有包住当前的 i 位置。比如 "#c#a#b#a#c#"，计算到 charArr[1] = 'c' 时，pR
 * 为 1。也就是说，右边界在 1 位置，1 位置为最右回文半径即将到达但还没有达到的位置，所以当前的 pR-1
 * 位置没有包住当前的 i 位置。此时和普通做法一样，从 i 位置字符开始，从左右两侧扩出去检查，此时的 "扩"
 * 过程没有获得加速。
 * 3）如果 pR-1 位置包住了当前的 i 位置。比如 "#c#a#b#a#c#"，计算到 charArr[6..10] 时，pR 都
 * 为 11，此时 pR-1 包住了位置 6～10。这种情况下，检查过程是可以获得优化的，这也是 manacher 算法
 * 的核心内容。
 * 4、按照步骤 3 的逻辑从左到右计算出 pArr 数组，计算完成后再遍历一遍 pArr 数组，找到最大的回文半
 * 径，假设位置 i 的回文半径最大，即 pArr[i] = max。但 max 只是 charArr 的最大回文半径，还得对
 * 应回原来的字符串，求出最大回文半径的长度（其实就是 max-1）。比如原字符串为 "121"，处理成 charArr
 * 之后为 "#1#2#1#"。在 charArr 中位置 3 的回文半径最大，最大值为 4（即 pArr[3] = 4），对应原
 * 字符串的最大回文子串长度为 4-1 = 3。
 * Manacher 算法时间复杂度是 O(N) 的证明。虽然可以很明显地看到 Manacher 算法与普通方法相比，在
 * 扩出去检查这一行为上有明显的优化，但如何证明该算法的时间复杂度就是 O(N) 呢？关键之处在于估算扩
 * 出去检查这一行为发生的数量。原字符在处理后的长度由 N 变为 2N，从步骤 3 的主要逻辑来看，要么在计
 * 算一个位置的回文半径时完全不需要扩出去检查，比如，步骤 3 中的 3）介绍的情况一和情况二，都可以直
 * 接获得位置 i 的回文半径长度；要么每一次扩出去检查都会导致 pR 变量的更新，比如步骤 3 的 2）和 3）
 * 介绍的情况三，扩出去检查时都让回文半径到达更右的位置，当然会使 pR 更新。然而 pR 最多是从 -1 增
 * 加到 2N（右边界），并且从来不减小，所以扩出去检查的次数就是 O(N) 的级别。所以 Manacher 算法时
 * 间复杂度为 O(N)。具体代码请参看 maxLcpsLength() 方法。
 *
 * 进阶问题。在字符串的最右添加最少字符，使整个字符串都成为回文串，其实就是查找在必须包含最后一个字
 * 符的情况下，最长的回文子串是什么。那么之前不是最长回文子串的部分逆序过来，就是应该添加的部分。比
 * 如 "abcd123321"，在必须包含最后一个字符的情况下，最长的回文子串是 "123321"，之前不是最长回文
 * 子串的部分是 "abcd"，所以末尾应该添加的部分就是 "dcba"。那么只要把 manacher 算法稍作修改就可
 * 以。具体改成：从左到右计算回文半径时，关注回文半径最右即将到达的位置（pR），一旦发现已经到达最后
 * （pR == charArr.length），说明必须包含最后一个字符的最长回文半径已经找到，直接退出检查过程，
 * 返回该添加的字符串即可。具体代码请参看 shortestEnd() 方法。
 *
 * @author Jiajing Li
 * @date 2019-07-27 14:24:29
 */
public class Main {

    public static void main(String[] args) {
        String str = "123";
        System.out.println(Manacher.maxLcpsLength(str));
        str = "abc1234321ab";
        System.out.println(Manacher.maxLcpsLength(str));
        System.out.println();
        str = "12";
        System.out.println(Manacher.shortestEnd(str));
    }

}
