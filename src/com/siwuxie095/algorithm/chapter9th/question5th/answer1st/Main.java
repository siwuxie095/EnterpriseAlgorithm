package com.siwuxie095.algorithm.chapter9th.question5th.answer1st;

/**
 * 判断一个点是否在三角形内部
 *
 * 题目：
 * 在二维坐标系中，所有的值都是 double 类型，那么一个三角形可以由 3 个点来代表，给定 3 个点代表的三角形，
 * 再给定一个点 (x, y)，判断 (x, y) 是否在三角形中。
 *
 * 解答：
 * 这里提供两种解法，第一种解法是从面积的角度来解决，第二种是从向量的角度来解决。解法一在逻辑上没有问题，
 * 但是没有解法二好，下面会给出详细的解释。
 * 先来介绍解法一，如果点 O 在三角形 ABC 内部，那么有：面积ABC = 面积ABO + 面积BCO + 面积CAO。如果
 * 点 O 在三角形 ABC 外部，那么有：面积ABC < 面积ABO + 面积BCO + 面积CAO。既然得知了这样一种评判标
 * 准，实现代码就变得很简单了。首先实现求两个点 (x1, y1) 和 (x2, y2) 之间距离的函数 getSideLength。
 * 有了这个函数之后，就可以求出一条边的边长。下面根据边长来求三角形的面积，用海伦公式来求解三角形面积是
 * 非常合适的。最后就可以根据标准来求解。具体代码请参看 isInside1() 方法。
 * 虽然解法一的逻辑是正确的，但 double 类型的值在计算时会出现一定程度的偏差。所以经常会发生明明 O 点在
 * 三角形内，但是面积却对不准的情况出现，最后导致判断出错。所以解法一不推荐。
 *
 * 解法二使用了和解法一完全不同的标准，而且几乎不会受精度损耗的影响。如果点 O 在三角形内部，除面积上的关
 * 系外，还有其他关系存在。
 * 如果点 O 在三角形内部 ABC 中，那么如果从三角形的一点出发，逆时针走过所有边的过程中，点 O 始终都在走
 * 过边的左侧。比如，O 都在 AB、BC 和 CA 的左侧。如果点 O 在三角形 ABC 外部，则不满足这个关系。
 * 新的标准有了，接下来解决一个棘手的问题。我们知道作为参数传入的三个点的坐标代表一个三角形，可是这三个点
 * 依次的顺序不一定是逆时针的。比如，如果参数的顺序为 A 坐标、B 坐标和 C 坐标，那就没问题，因为这是逆时
 * 针的。但如果参数的顺序为 C 坐标、B 坐标和 A 坐标，就有问题，因为这是顺时针的。作为程序的实现者，要求
 * 用户按你规定的顺序传入三角形的三个点的坐标，这明显是不合适的。所以需要自己来解决这个问题。假设得到的坐
 * 标依次为点 1、点 2、点 3。顺序可能是顺时针，也可能是逆时针。
 * 如果点 2 在 1-> 3 边的右边，此时按照点 1、点 2 和点 3 的顺序没有问题，这个顺序本来就是逆时针的。但
 * 如果点 2 在 1 -> 3 边的左边，那么按照点 1、点 2 和点 3 的顺序就有问题，因为这个顺序是顺时针的，所
 * 以应该按照点 1、点 3 和点 2 的顺序。
 * 如何判断一个点在一条有向边的左边还是右边？这个利用几何上向量积的求解公式即可。如果有向边 1->2 叉乘有
 * 向边 1->3 的结果为正，说明 2 在有向边 1->3 的左边；如果有向边 1->2 叉乘有向边 1->3 的结果为负，说
 * 明 2 在有向边 1->3 的右边。具体代码请参看 crossProduct() 方法，该方法描述了向量 (x1, y1) 叉乘向
 * 量 (x2, y2) 两个向量的开始点都是原点。全部过程的代码请参看 isInside2() 方法。
 *
 * @author Jiajing Li
 * @date 2019-06-15 21:17:39
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Judge.isInside1(0, 0, 4, 8, 8, 0, 4, 4));
        System.out.println(Judge.isInside2(0, 0, 4, 8, 8, 0, 4, 4));
    }

}