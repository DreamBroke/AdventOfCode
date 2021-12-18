package org.dreambroke;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test13 {

    public static void main(String[] args) throws IOException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test13.class.getClassLoader().getResource("question13.txt")).getFile()));
        Set<Point> points = new HashSet<>();
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            if (line.isEmpty()) {
                break;
            }
            String[] input = line.split(",");
            points.add(new Point(input[0], input[1]));
        }
        List<String[]> instructions = new ArrayList<>();
        while (textFile.hasNextLine()) {
            String[] input = textFile.nextLine().split(" ")[2].split("=");
            instructions.add(input);
        }
        System.out.println(partA(points, instructions));
        partB(points, instructions);
    }

    private static int partA(Set<Point> points, List<String[]> instructions) {
        return foldPaper(points, instructions, 1).size();
    }

    /**
     * A部分解答
     */
    private static Set<Point> foldPaper(Set<Point> points, List<String[]> instructions, int foldTime) {
        for (int i = 0; i < foldTime; i++) {
            String[] instruction = instructions.get(i);
            int foldLine = Integer.parseInt(instruction[1]);
            Set<Point> newPoints = new HashSet<>();
            for (Point point : points) {
                if (instruction[0].equals("y")) {
                    if (point.y > foldLine) {
                        point.y = foldLine - (point.y - foldLine);
                    }
                } else {
                    if (point.x > foldLine) {
                        point.x = foldLine - (point.x - foldLine);
                    }
                }
                if (point.x >= 0 && point.y >= 0) {
                    newPoints.add(point);
                }
            }
            points = newPoints;
        }
        return points;
    }


    /**
     * B部分解答
     */
    private static void partB(Set<Point> points, List<String[]> instructions) {
        points = foldPaper(points, instructions, instructions.size());
        int[][] map = new int[6][41];
        for (Point point : points) {
            map[point.y][point.x] = 1;
        }
        for (int[] ints : map) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    System.out.print("-");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }
}

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    @Override
    public int hashCode() {
        return x * 10 + y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Point object && object.x == this.x && object.y == this.y;
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }
}
