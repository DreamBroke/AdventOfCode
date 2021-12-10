package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test09 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test09.class.getClassLoader().getResource("question09.txt")).getFile()));
        long start = System.currentTimeMillis();
        int[][] map = new int[100][100];
        int i = 0;
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            char[] chars = line.toCharArray();
            int[] oneLine = new int[chars.length];
            for (int j = 0; j < chars.length; j++) {
                oneLine[j] = Integer.parseInt(String.valueOf(chars[j]));
            }
            map[i] = oneLine;
            i++;
        }
//        System.out.println(partA(map));
        long now = System.currentTimeMillis();
        System.out.println(partB(map));
        System.out.println(System.currentTimeMillis() - now);
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * A部分解答
     */
    private static int partA(int[][] map) {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == 0) {
                    if (j == 0) {
                        if (map[i][j] < map[i + 1][j] && map[i][j] < map[i][j + 1]) {
                            sum += map[i][j] + 1;
                        }
                    } else if (j == map[i].length - 1) {
                        if (map[i][j] < map[i + 1][j] && map[i][j] < map[i][j - 1]) {
                            sum += map[i][j] + 1;
                        }
                    } else {
                        if (map[i][j] < map[i + 1][j] && map[i][j] < map[i][j - 1] && map[i][j] < map[i][j + 1]) {
                            sum += map[i][j] + 1;
                        }
                    }
                } else if (i == map.length - 1) {
                    if (j == 0) {
                        if (map[i][j] < map[i - 1][j] && map[i][j] < map[i][j + 1]) {
                            sum += map[i][j] + 1;
                        }
                    } else if (j == map[i].length - 1) {
                        if (map[i][j] < map[i - 1][j] && map[i][j] < map[i][j - 1]) {
                            sum += map[i][j] + 1;
                        }
                    } else {
                        if (map[i][j] < map[i - 1][j] && map[i][j] < map[i][j - 1] && map[i][j] < map[i][j + 1]) {
                            sum += map[i][j] + 1;
                        }
                    }
                } else {
                    if (j == 0) {
                        if (map[i][j] < map[i - 1][j] && map[i][j] < map[i][j + 1] && map[i][j] < map[i + 1][j]) {
                            sum += map[i][j] + 1;
                        }
                    } else if (j == map[i].length - 1) {
                        if (map[i][j] < map[i - 1][j] && map[i][j] < map[i][j - 1] && map[i][j] < map[i + 1][j]) {
                            sum += map[i][j] + 1;
                        }
                    } else {
                        if (map[i][j] < map[i - 1][j] && map[i][j] < map[i][j - 1] && map[i][j] < map[i][j + 1] && map[i][j] < map[i + 1][j]) {
                            sum += map[i][j] + 1;
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * B部分解答
     */
    private static int partB(int[][] map) {
        List<Integer> maxSize = new ArrayList<>();
        int[][] markIndex = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (markIndex[i][j] != 0 || map[i][j] == 9) {
                    continue;
                }
                int size = getSize(map, markIndex, i, j);
                addToList(maxSize, size);
            }
        }
        System.out.println(maxSize);
        return maxSize.get(0) * maxSize.get(1) * maxSize.get(2);
    }

    private static void addToList(List<Integer> list, int size) {
        if (list.size() < 3) {
            list.add(size);
            list.sort(Comparator.naturalOrder());
        } else if (size > list.get(0)) {
            list.set(0, size);
            list.sort(Comparator.naturalOrder());
        }
    }

    private static int getSize(int[][] map, int[][] markIndex, int i, int j) {
        if (i < 0 || j < 0 || i == map.length || j == map[i].length || markIndex[i][j] != 0 || map[i][j] == 9) {
            return 0;
        }
        markIndex[i][j]++;
        return 1 + getSize(map, markIndex, i + 1, j) + getSize(map, markIndex, i, j + 1) + getSize(map, markIndex, i - 1, j) + getSize(map, markIndex, i, j - 1);
    }
}