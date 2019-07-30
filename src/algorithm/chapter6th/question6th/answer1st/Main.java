package algorithm.chapter6th.question6th.answer1st;

/**
 * 一致性哈希算法的基本原理
 *
 * 题目：
 * 工程师常使用服务器集群来设计和实现数据缓存，以下是常见的策略：
 * 1、无论是添加、查询还是删除数据，都先将数据的 id 通过哈希函数转换成一个哈希值，记为 key。
 * 2、如果目前机器有 N 台，则计算 key%N 的值，这个值就是该数据所属的机器编号，无论是添加、删除还是查询操作，
 * 都只在这台机器上进行。
 * 请分析这种缓存策略可能带来的问题，并提出改进的方案。
 *
 * 解答：
 * 题目中描述的缓存策略的潜在问题是如果增加或删除机器时（N 变化）代价会很高，所有的数据都不得不根据 id 重新
 * 计算一遍哈希值，并将哈希值对新的机器数进行取模操作，然后进行大规模的数据迁移。
 * 为了解决这些问题，下面介绍一下一致性哈希算法，这是一种很好的数据缓存设计方案。我们假设数据的 id 通过哈希
 * 函数转换成的哈希值范围是 2^32，也就是 0～2^32-1 的数字空间中。现在我们可以将这些数字头尾相连，想象成一
 * 个闭合的环形，那么一个数据 id 在计算处哈希值之后认为对应到环中的一个位置上。
 * 接下来想象有三台机器也处在这样一个环中，这三台机器在环中的位置根据机器 id 计算出的哈希值来决定。那么一条
 * 数据如何确定归属哪台机器呢？首先把该数据的 id 用哈希函数算出哈希值，并映射到环中的相应位置，然后顺时针找
 * 寻离这个位置最近的机器，那台机器就是该数据的归属。添加机器时代价是比较小的。在删除机器时也是一样，只要把
 * 删除机器的数据全部复制到顺时针找到的下一台机器上即可。
 * 机器负载不均衡时的处理。如果机器较少，很有可能造成机器在整个环上的分布不均匀，从而导致机器之间的负载不均衡。
 * 为了解决这种数据倾斜问题，一致性哈希算法引入了虚拟节点机制，即对每一台机器通过不同的哈希函数计算出多个哈希
 * 值，对多个位置都放置一个服务节点，称为虚拟节点。具体做法可以在机器 ip 或主机名的后面增加编号或端口号来实现。
 * 当增加了虚拟节点之后，节点数变多了，根据哈希函数的性质，平衡性自然会变好。
 * 此时数据定位算法不变，只是多了一步虚拟节点到实际节点的映射。当某一条数据计算出归属于虚拟节点，再根据映射关系，
 * 数据最终将归属于实际节点。
 * 基于一致性哈希的原理有多种具体的实现，包括 Chord 算法、KAD 算法等。
 *
 * @author Jiajing Li
 * @date 2019-05-17 21:21:53
 */
public class Main {

}
