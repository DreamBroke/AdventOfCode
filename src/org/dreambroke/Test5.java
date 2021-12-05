package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Test5 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test5.class.getClassLoader().getResource("question5.txt")).getFile()));
        List<int[][]> inputArr = new ArrayList<>();
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            String[] inputSplit = line.split(" -> ");
            int[] startPoint = Arrays.stream(inputSplit[0].split(",")).flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).toArray();
            int[] endPoint = Arrays.stream(inputSplit[1].split(",")).flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).toArray();
            int[][] input = new int[2][2];
            input[0] = startPoint;
            input[1] = endPoint;
            inputArr.add(input);
        }
//        System.out.println(partA(inputArr));
        System.out.println(partB(inputArr));
    }

    /**
     * A部分解答
     */
    private static int partA(List<int[][]> inputArr) {
        int[][] map = new int[1000][1000];
        for (int[][] line : inputArr) {
            if (line[0][0] == line[1][0]) {
                markHorizontalLine(map, line);
            } else if (line[0][1] == line[1][1]) {
                markVerticalLine(map, line);
            }
        }
        return getCount(map);
    }

    /**
     * B部分解答
     */
    private static int partB(List<int[][]> inputArr) {
        int[][] map = new int[1000][1000];
        for (int[][] line : inputArr) {
            if (line[0][0] == line[1][0]) {
                markHorizontalLine(map, line);
            } else if (line[0][1] == line[1][1]) {
                markVerticalLine(map, line);
            } else if (Math.abs(line[0][0] - line[1][0]) == Math.abs(line[0][1] - line[1][1])) {
                int[] leftPoint = line[0][0] < line[1][0] ? line[0] : line[1];
                int[] rightPoint = line[0] == leftPoint ? line[1] : line[0];
                if (rightPoint[1] > leftPoint[1]) {
                    for (int i = leftPoint[0], j = leftPoint[1]; i <= rightPoint[0] && j <= rightPoint[1]; i++, j++) {
                        map[i][j]++;
                    }
                } else {
                    for (int i = leftPoint[0], j = leftPoint[1]; i <= rightPoint[0] && j >= rightPoint[1]; i++, j--) {
                        map[i][j]++;
                    }
                }
            }
        }
        return getCount(map);
    }

    private static void markHorizontalLine(int[][] map, int[][] line) {
        int min = Math.min(line[0][1], line[1][1]);
        int max = Math.max(line[0][1], line[1][1]);
        for (int i = min; i <= max; i++) {
            map[line[0][0]][i]++;
        }
    }

    private static void markVerticalLine(int[][] map, int[][] line) {
        int min = Math.min(line[0][0], line[1][0]);
        int max = Math.max(line[0][0], line[1][0]);
        for (int i = min; i <= max; i++) {
            map[i][line[0][1]]++;
        }
    }

    private static int getCount(int[][] map) {
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (map[i][j] > 1) {
                    count++;
                }
            }
        }
        return count;
    }
}
