package algorithm.chapter3rd.question19th.answer1st;

/**
 * Tarjan 算法与并查集解决二叉树节点间最近公共祖先的批量查询问题
 *
 * 问题：
 * 定义一个 Query 类，一个 Query 类的实例表示一条查询语句，表示想要查询 o1 节点和 o2 节点的
 * 最近公共祖先节点。
 * 给定一棵二叉树的头节点 head，并给定所有的查询语句，即 一个 Query 类型的数组 Query[] ques，
 * 请返回 Node 类型的数组 Node[] ans，ans[i] 表示 ques[i] 这条查询的答案，即ques[i].o1
 * 和 ques[i].o2 的最近公共祖先。
 *
 * 解答：
 * 一棵二叉树如下：
 *                   1
 *              /         \
 *         2                   3
 *      /     \                  \
 *   4          5                   6
 *            /    \              /
 *          7       8           9
 *
 * 本题的解法利用了 Tarjan 算法与并查集结构的结合。假设想要进行的查询为 ques[0] = (4, 7)，
 * ques[1] = (7, 8)，ques[2] = (8, 9)， ques[3] = (9, 3)， ques[4] = (6, 6)，
 * ques[5] = (null, 5)， ques[6] = (null, null)。
 *
 * 首先生成和 ques 长度一样的 ans 数组，如下三种情况的查询是可以直接得到答案的：
 * 1、如果 o1 等于 o2，答案为 o1。例如 ques[4]，令 ans[4] = 6。
 * 2、如果 o1 和 o2 只要有一个为 null，答案是不为空的那个。例如 ques[5]，令 ans[5] = 5。
 * 3、如果 o1 和 o2 都为 null，答案为 null。例如 ques[6]，令 ans[6] = null。
 *
 * 对不能直接得到答案的查询，需要把查询的格式转换一下，具体过程如下：
 * 1、生成两张哈希表 queryMap 和 indexMap。queryMap 类似于邻接表，key 表示查询涉及的某个
 * 节点，value 是一个链表类型，表示 key 与哪些节点之间有查询任务。indexMap 的 key 也表示查
 * 询涉及的某个节点，value 也是链表类型，表示如果依次解决有关 key 节点的每个问题，该把答案放在
 * ans 的什么位置。也就是说，如果一个节点为 node，node 与哪些节点之间有查询任务呢？都放在 queryMap
 * 中；获得的答案该放在 ans 的什么位置呢？都放在 indexMap 中。
 *
 * 比如，根据 ques[0~3]，queryMap 和 indexMap 生成记录如下：
 *      key                 value
 *      4                   queryMap 中 4 的链表为：7；indexMap 中 4 的链表为：0。
 *      7                   queryMap 中 7 的链表为：4、8；indexMap 中 7 的链表为：0、1。
 *      8                   queryMap 中 8 的链表为：7、9；indexMap 中 8 的链表为：1、2。
 *      9                   queryMap 中 9 的链表为：8、3；indexMap 中 9 的链表为：2、3。
 *      3                   queryMap 中 3 的链表为：9；indexMap 中 3 的链表为：3。
 * 不难发现，一条(o1, o2) 的查询语句在上面的两个表中其实生成了两次。这么做的目的是为了处理时方便
 * 找到关于每个节点的查询任务，也方便设置答案，介绍完整个流程之后，会有进一步说明。
 *
 * 接下来是 Tarjan 算法处理 M 条查询的过程，整个过程是二叉树的先左、再根、再右、最后再回到根的遍
 * 历，过程如下：
 * 1、对每个节点生成各自的集合，开始时每个集合的祖先节点设为空。遍历 4，发现它属于集合 {4}，设置集
 * 合 {4} 的祖先节点为 4，发现有关于 4 和 7 的查询任务，发现 7 属于集合 {7}，但集合 {7} 的祖先
 * 节点为空，说明还没遍历到，所以暂时不执行这个查询任务。
 * 2、遍历 2，发现它属于集合 {2}，设置集合 {2} 的祖先为 2，此时左孩子 4 属于集合 {4}，将集合 {4}
 * 和集合 {2} 合并，两个集合一旦合并，小的不再存在，而是生成更大的集合 {4, 2}，并设置集合 {4, 2}
 * 的祖先节点为当前节点 2。
 * 3、遍历 7，发现它属于集合 {7}，设置集合 {7} 的祖先为 7，发现有关于 7 和 4 的查询任务，发现 4
 * 属于集合 {4, 2}，集合 {4, 2} 的祖先节点为 2，说明 4 和 7 都已遍历到，根据 indexMap 知道答
 * 案应该放在 0 位置，所以设置 ans[0] = 2；又发现有 7 和 8 的查询任务，发现 8 属于集合 {8}，但
 * 集合 {8} 的祖先节点为空，说明还没有遍历到，忽略。
 * 4、遍历 5，发现它属于集合 {5}，设置集合 {5} 的祖先节点为 5，此时左孩子 7 属于集合 {7}，两集
 * 合合并为 {7, 5}，并设置集合 {7, 5} 的祖先为当前节点 5。
 * 5、遍历 8，发现它属于集合 {8}，设置集合 {8} 的祖先节点为 8，发现有 8 和 7 的查询任务，发现 7
 * 属于集合 {7, 5}，集合 {7, 5} 的祖先节点为 5，设置 ans[1] = 5，发现有 8 和 9 的查询任务，忽
 * 略。
 * 6、从 5 的右子树重新回到 5，5 属于集合 {7, 5}，5 的右孩子 8 属于集合 {8}，两集合合并为 {7,
 * 5, 8}，并设置集合 {7, 5, 8} 的祖先为当前节点 5。
 * 7、从 2 的右子树重新回到 2，2 属于集合 {2, 4}，2 的右孩子 5 属于集合 {7, 5, 8}，两集合合并
 * 为 {2, 4, 7, 5, 8}，并设置这个集合的祖先为当前节点 2。
 * 8、遍历 1，{2, 4, 7, 5, 8} 与 {1} 合并为 {2, 4, 7, 5, 8, 1}，这个集合祖先节点为当前节点 1。
 * 9、遍历 3，发现它属于集合 {3}，设置集合 {3} 的祖先节点为 3，发现有 3 和 9 的查询任务，但 9 没
 * 遍历到，忽略。
 * 10、遍历 6，发现它属于集合 {6}，设置集合 {6} 的祖先节点为 6。
 * 11、遍历 9，发现它属于集合 {9}，设置集合 {9} 的祖先节点为 9。发现有 9 和 8 的查询任务，8 属于
 * 集合 {2, 4, 7, 5, 8, 1}，这个集合的祖先节点为 1，根据 indexMap 知道答案应该放在 2 位置，所
 * 以设置 ans[2] = 1；发现有 9 和 3 的查询任务，3 属于集合 {3}，这个集合的祖先节点为 3，根据 indexMap
 * 答案应该放在 3 位置，所以设置 ans[3] = 1。
 * 12、回到 6，合并 {6} 和 {9} 为 {6, 9}，设置 {6, 9} 的祖先节点为 6。
 * 13、回到 3，合并 {3} 和 {6, 9} 为 {3, 6, 9}，设置 {6, 9} 的祖先节点为 3。
 * 14、回到 1，合并 {2, 4, 7, 5, 8, 1} 和 {3, 6, 9} 为 {1, 2, 3, 4, 5, 6, 7, 8, 9}，设置
 * 祖先节点为 1。
 * 15、过程结束，所有答案都已得到。
 *
 * 现在可以解释生成 queryMap 和 indexMap 的意义了，遍历到一个节点时记为 a，queryMap 可以迅速
 * 查到有哪些节点和 a 之间有查询任务，如果能够得到答案，indexMap 还能告诉我们把答案放在 ans 的什
 * 么位置。假设 a 和节点 b 之间有查询任务，如果此时 b 已经遍历过，自然可以取得答案，然后在有关 a
 * 的链表中，删除这个查询任务；如果此时 b 没有遍历过，依然在属于 a 的链表中删除这个查询任务，这个
 * 任务会在遍历到 b 时重新被发现，因为同样的任务 b 也存了一份。所以遍历到一个节点，有关这个节点的
 * 任务列表会被完全清空，可能有些任务已经被解决，有些则没有也不要紧，一定会在后序的过程中被发现并得
 * 以解决。这就是 queryMap 和 indexMap 生成两遍查询任务信息的意义。
 *
 * 上述流程很好理解，但大量出现生成集合、合并集合和根据节点找到所在集合的操作，如果二叉树的节点数为
 * N，那么生成集合操作 O(N) 次，合并集合操作 O(N) 次，根据节点找到所在集合 O(N+M) 次。所以如果
 * 上述整个过程想达到 O(N+M) 的时间复杂度，那就要求有关集合的单次操作，平均时间复杂度要求为 O(1)，
 * 请注意这里说的是平均。存在这么好的集合结构吗？存在。这种集合结构就是接下来要介绍的并查集结构。
 *
 * 并查集结构由 Bernard A. Galler 和 Michael J. Fischer 在 1964 年发明，但证明时间复杂度的
 * 工作却持续了数十年之久，直到 1989 年才彻底证明完毕。有兴趣的可以参考《算法导论》来了解整个证明过
 * 程，这里不再详述，这里只重点介绍并查集的结构和各种操作的细节，并实现针对二叉树结构的并查集，这是一
 * 种经常使用的高级数据结构。
 * 值得注意的是，上述流程提到一个集合祖先节点的概念，与接下来介绍的并查集时提到的一个集合代表节点（父
 * 节点）的概念不是一回事。本题流程中设置的一个集合祖先节点的操作也不属于并查集自身的操作，关于这个操
 * 作，会在介绍完并查集结构后再详细说明。
 *
 * 并查集由一群集合构成，比如步骤 1 中对每个节点都生成各自的集合，所有集合的全体构成一个并查集。这些
 * 集合可以合并，如果最终合并成一个大集合（比如步骤 14），那么此时并查集中有一个元素，这个元素就是这
 * 个大集合。这里其实主要是想说明并查集是集合的集合这个概念。
 *
 * 并查集先经历初始化的过程，就像流程中的步骤 1 一样，把每个节点都生成一个只含有自己的集合。那么并查
 * 集中的单个集合是什么结构呢？如果集合中只有一个元素，记为节点 a，那么 a 的 father 就是自己，也就
 * 意味着这个集合的代表节点就是唯一的元素。实现记录节点 father 信息的方式有很多，这里使用哈希表来保
 * 存所有并查集中所有集合的所有元素的 father 信息，记为 fatherMap。比如，对于这个集合，在 fatherMap
 * 中肯定有某一条记录为 (a, a)，表示 key 节点的 father 为 value 节点。每个元素除了 father 信息
 * 之外，还有另一个信息叫 rank，rank 为整数，代表一个节点的秩，秩的概念可以粗略理解为一个节点下面还
 * 有多少层的节点，但是并查集结构对于每个节点的秩的更新并不严格，所以每个节点的秩只能粗略描述该节点下
 * 面的深度，正是由于秩在更新上的不严格，换来了极好的时间复杂度，而也正是因为这种不严格增加了并查集时
 * 间复杂度证明的难度。集合中只有一个元素时，这个元素的 rank 初始化为 0。所有节点的秩信息保存在 rankMap
 * 中。
 *
 * 对二叉树结构并查集初始化的过程请参看 DisjointSets 类中的 makeSets() 方法。
 *
 * 当集合中有多个节点时，下层节点的 father 为上层节点，最上层节点的 father 指向自己，最上层节点又
 * 叫集合的代表节点。
 * 在并查集中，若要查一个节点属于哪个集合，就是在查这个节点所在集合的代表节点是什么，一个节点通过 father
 * 信息逐渐找到最上面的节点，这个节点的 father 是自己，代表整个集合。通过一个节点找到所在集合代表节
 * 点的过程叫做 findFather 过程。findFather 最终会返回代表节点，但过程并不仅是单纯的查找过程，还
 * 会把整个查找路径压缩。即 把一条路径上的所有节点的 father 都设置成最上层的节点 a。
 * 经过路径压缩后，路径上每个节点下次再找代表节点的时候都只需要经过一次移动的过程。这也是整个并查集结
 * 构的设计中最重要的优化。
 * 根据一个节点查找所在集合代表节点的过程，请参看 DisjointSets 类中的 findFather() 方法。
 *
 * 前面已经展示了并查集中的集合如何初始化，如何根据某一个节点查找所在集合的代表元素以及如何做路径压缩
 * 的过程，接下来介绍集合如何合并。首先，两个集合进行合并操作时，参数并不是两个集合，而是两个集合中任
 * 意的两个节点，记为 a 和 b。所以集合的合并更准确的说法是，根据 a 找到 a 所在集合的代表节点 findFather(a)，
 * 记为 aF，根据 b 找到 b 所在集合的代表节点 findFather(b)，记为 bF，然后用如下策略决定由哪个代表
 * 节点作为合并后大集合的代表节点：
 * 1、如果 aF == bF，说明 a 和 b 本身就在一个集合里，不用合并。
 * 2、如果 aF != bF，那么假设 aF 的 rank 值记为 aFrank，bF 的 rank 值记为 bFrank。根据对 rank
 * 的解释，rank 可以粗略描述一个节点下面的层数，而 aF 和 bF 本身又是各自集合中最上面的节点，所以 aFrank
 * 粗略描述 a 所在集合的总层数，bFrank 粗略描述 b 所在集合的总层数。如果 aFrank < bFrank，那么把
 * aF 的 father 设为 bF，表示 a 所在集合因为层数较少，所以挂在 b 所在集合的下面，这样合并之后的大集
 * 合 rank 不会有变化。如果 aFrank > bFrank，就把 bF 的 father 设为 aF。如果 aFrank == bFrank，
 * 那么 aF 和 bF 谁做大集合的代表都可以，本题是把 aF 作为代表，即 把 bF 的 father 设为 aF，此时 aF
 * 的 rank 值增加 1。
 *
 * 介绍完并查集的结构之后，最后解释一下在总流程中如何设置一个集合的祖先节点，如上流程中的每一步都有把
 * 当前节点 node 所在集合的祖先节点设置为 node 的操作。在整个流程开始之前，建立一张哈希表，参看 Tarjan
 * 类中的 ancestorMap。我们知道在并查集中，每个集合都是用该集合的代表节点来表示的。所以如果想把 node
 * 所在集合的祖先节点设为 node，只用把记录 (findFather(node), node) 放入 ancestorMap 中即可。
 * 同理，如果想得到一个节点 a 所在集合的祖先节点，令 key 为 findFather(a)，然后从 ancestorMap
 * 中取出相应的记录即可。ancestorMap 同时还可以表示一个节点是否被访问过。
 *
 * 全部的处理流程请参看 tarjanQuery() 方法。
 *
 * @author Jiajing Li
 * @date 2019-03-28 18:43:05
 */
public class Main {

    /**
     * 一棵二叉树如下：
     *               1
     *          /        \
     *      2               3
     *   /     \          /    \
     * 4        5       6       7
     *                        /
     *                      8
     */
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);

        node1.left = node2;
        node1.right = node3;

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        node7.left = node8;

        Query[] queryArr = new Query[2];
        queryArr[0] = new Query(node4, node5);
        queryArr[1] = new Query(node5, node8);

        Node[] answerArr = tarjanQuery(node1, queryArr);
        for (Node answer : answerArr) {
            System.out.println(answer.value);
        }
    }

    public static Node[] tarjanQuery(Node head, Query[] queryArr) {
        Node[] answerArr = new Tarjan().query(head, queryArr);
        return answerArr;
    }

}