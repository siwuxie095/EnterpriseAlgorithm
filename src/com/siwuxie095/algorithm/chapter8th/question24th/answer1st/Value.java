package com.siwuxie095.algorithm.chapter8th.question24th.answer1st;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Jiajing Li
 * @date 2019-06-11 22:07:56
 */
public class Value {

    public static int minPathValue(int[][] m) {
        if (null == m || m.length == 0 || m[0].length == 0
                || m[0][0] != 1 || m[m.length - 1][m[0].length - 1] != 1) {
            return 0;
        }
        int res = 0;
        int[][] map = new int[m.length][m[0].length];
        map[0][0] = 1;
        Queue<Integer> rQ = new LinkedList<>();
        Queue<Integer> cQ = new LinkedList<>();
        rQ.add(0);
        cQ.add(0);
        int r = 0;
        int c = 0;
        while (!rQ.isEmpty()) {
            r = rQ.poll();
            c = cQ.poll();
            if (r == m.length - 1 && c == m[0].length - 1) {
                return map[r][c];
            }
            // up
            walkTo(map[r][c], r - 1, c, m, map, rQ, cQ);
            // down
            walkTo(map[r][c], r + 1, c, m, map, rQ, cQ);
            // left
            walkTo(map[r][c], r, c - 1, m, map, rQ, cQ);
            // right
            walkTo(map[r][c], r, c + 1, m, map, rQ, cQ);
        }
        return res;
    }

    private static void walkTo(int pre, int toR, int toC,
                               int[][] m, int[][] map,
                               Queue<Integer> rQ, Queue<Integer> cQ) {
        if (toR < 0 || toR == m.length || toC < 0 || toC == m[0].length
                || m[toR][toC] != 1 || map[toR][toC] != 0) {
            return;
        }
        map[toR][toC] = pre + 1;
        rQ.add(toR);
        cQ.add(toC);
    }

}
