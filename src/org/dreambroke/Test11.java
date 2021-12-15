package org.dreambroke;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test11 {

    public static void main(String[] args) throws IOException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test11.class.getClassLoader().getResource("question11.txt")).getFile()));
        int[][] map = new int[10][10];
        for (int i = 0; i < 10; i++) {
            String line = textFile.nextLine();
            int[] nums = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray();
            map[i] = nums;
        }
//        System.out.println(partA(map));
        System.out.println(partB(map));
    }


    /**
     * A部分解答
     */
    private static int partA(int[][] map) {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    increases(map, j, k);
                }
            }
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (map[j][k] > 9) {
                        sum++;
                        map[j][k] = 0;
                    }
                }
            }
        }
        return sum;
    }

    private static void increases(int[][] map, int j, int k) {
        if (j < 0 || k < 0 || j > 9 || k > 9 || map[j][k] > 9) {
            return;
        }
        map[j][k]++;
        if (map[j][k] > 9) {
            increases(map, j - 1, k - 1);
            increases(map, j - 1, k);
            increases(map, j - 1, k + 1);
            increases(map, j, k - 1);
            increases(map, j, k + 1);
            increases(map, j + 1, k - 1);
            increases(map, j + 1, k);
            increases(map, j + 1, k + 1);
        }
    }

    /**
     * B部分解答
     */
    private static int partB(int[][] map) {
        int count = 0;
        OUT:
        while (true) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    increases(map, j, k);
                }
            }
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (map[j][k] > 9) {
                        map[j][k] = 0;
                    }
                }
            }
            count++;
            int temp = map[0][0];
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (map[j][k] != temp) {
                        continue OUT;
                    }
                }
            }
            return count;
        }
    }
}
