package org.dreambroke;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test12 {

    private static final Map<String, Set<String>> MAP = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test12.class.getClassLoader().getResource("question12.txt")).getFile()));
        while (textFile.hasNextLine()) {
            String[] input = textFile.nextLine().split("-");
            Set<String> target1 = MAP.getOrDefault(input[0], new HashSet<>());
            target1.add(input[1]);
            Set<String> target0 = MAP.getOrDefault(input[1], new HashSet<>());
            target0.add(input[0]);
            MAP.putIfAbsent(input[0], target1);
            MAP.putIfAbsent(input[1], target0);
        }
//        System.out.println(partA());
        System.out.println(partB());
    }


    /**
     * A部分解答
     */
    private static int partA() {
        return searchCrossRoad("start", new HashSet<>());
    }

    private static int searchCrossRoad(String current, Set<String> road) {
        if (current.equals("end")) {
            return 1;
        }
        if (road.contains(current) && isSmallCaves(current.charAt(0))) {
            return 0;
        }
        road.add(current);
        Set<String> targets = MAP.getOrDefault(current, new HashSet<>());
        int result = 0;
        for (String target : targets) {
            result += searchCrossRoad(target, road);
        }
        road.remove(current);
        return result;
    }

    private static boolean isSmallCaves(char c) {
        return c >= 'a' && c <= 'z';
    }

    /**
     * B部分解答
     */
    private static int partB() {
        return searchCrossRoadTwice("start", new HashSet<>(), null, 1);
    }

    private static int searchCrossRoadTwice(String current, Set<String> road, String twiceStr, int twiceStrTime) {
        if (current.equals("end")) {
            return 1;
        }
        if (road.contains("start") && current.equals("start")) {
            return 0;
        }
        if (isSmallCaves(current.charAt(0)) && twiceStr != null && twiceStrTime > 2) {
            return 0;
        }
        road.add(current);
        Set<String> targets = MAP.getOrDefault(current, new HashSet<>());
        int result = 0;
        for (String target : targets) {
            if (road.contains(target) && isSmallCaves(target.charAt(0))) {
                result += searchCrossRoadTwice(target, road, target, twiceStrTime + 1);
            } else {
                result += searchCrossRoadTwice(target, road, twiceStr, twiceStrTime);
            }
        }
        if (!current.equals(twiceStr)) {
            road.remove(current);
        }
        return result;
    }
}
