package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test7 {

    private static final Map<Integer, Integer> listSizeMap = new HashMap<>();
    private static final Map<Integer, List<Integer>> loopListMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test7.class.getClassLoader().getResource("question7.txt")).getFile()));
        String line = textFile.nextLine();
        long start = System.currentTimeMillis();
        int[] inputArr = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
//        System.out.println(partA(inputArr));
        long now = System.currentTimeMillis();
        System.out.println(partB(inputArr));
        System.out.println(System.currentTimeMillis() - now);
        System.out.println(System.currentTimeMillis() - start);

        long start2 = System.currentTimeMillis();
        otherSolution(line);
        System.out.println(System.currentTimeMillis() - start2);
    }

    private static void otherSolution(String line) {

    }

    /**
     * A部分解答
     */
    private static int partA(int[] inputArr) {
        int max = 0;
        int min = 0;
        for (int i : inputArr) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }
        int least = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            int cost = 0;
            for (int crab : inputArr) {
                cost += Math.abs(i - crab);
            }
            least = Math.min(least, cost);
        }
        return least;
    }

    /**
     * B部分解答
     */
    private static long partB(int[] inputArr) {
        int max = 0;
        int min = 0;
        for (int i : inputArr) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }
        int least = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            int cost = 0;
            for (int crab : inputArr) {
                int gap = Math.abs(i - crab);
                cost += (gap + 1) * gap / 2;
            }
            least = Math.min(least, cost);
        }
        return least;
    }
}
