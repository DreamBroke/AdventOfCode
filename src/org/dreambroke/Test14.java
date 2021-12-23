package org.dreambroke;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test14 {

    private static String initStr;

    private static final Map<String, Character> PAIR_INSERTIONS = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test14.class.getClassLoader().getResource("question14.txt")).getFile()));
        initStr = textFile.nextLine();
        textFile.nextLine();
        while (textFile.hasNextLine()) {
            String[] input = textFile.nextLine().split(" -> ");
            PAIR_INSERTIONS.put(input[0], input[1].charAt(0));
        }
        System.out.println(partA());
        long start = System.currentTimeMillis();
        System.out.println(partB());
        System.out.println(System.currentTimeMillis() - start);
    }


    /**
     * A部分解答
     */
    private static long partA() {
        return getResult(10);
    }

    private static long getResult(int times) {
        Map<String, List<Map<Character, Long>>> map = initMap();
        for (int i = 0; i < times; i++) {
            for (Map.Entry<String, List<Map<Character, Long>>> entry : map.entrySet()) {
                List<Map<Character, Long>> charCountMap = entry.getValue();
                String key = entry.getKey();
                Map<Character, Long> leftMap = map.get(findLeftSub(key)).get(i);
                Map<Character, Long> rightMap = map.get(findRightSub(key)).get(i);
                Map<Character, Long> nextMap = new HashMap<>(map.get(key).get(0));
                addAll(nextMap, leftMap);
                addAll(nextMap, rightMap);
                charCountMap.add(nextMap);
            }
        }
        Map<Character, Long> result = new HashMap<>();
        result.put(initStr.charAt(0), 1L);
        for (int i = 1; i < initStr.length(); i++) {
            String subStr = initStr.substring(i - 1, i + 1);
            addAll(result, map.get(subStr).get(times - 1));
            char c = initStr.charAt(i);
            if (result.containsKey(c)) {
                result.put(c, result.get(c) + 1);
            } else {
                result.put(c, 1L);
            }
        }
        return calcGap(result);
    }

    private static long calcGap(Map<Character, Long> result) {
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;
        for (long count : result.values()) {
            if (count > max) {
                max = count;
            }
            if (count < min) {
                min = count;
            }
        }
        return max - min;
    }

    private static Map<String, List<Map<Character, Long>>> initMap() {
        Map<String, List<Map<Character, Long>>> map = new HashMap<>();
        for (Map.Entry<String, Character> entry : PAIR_INSERTIONS.entrySet()) {
            List<Map<Character, Long>> list = new ArrayList<>();
            Map<Character, Long> charCountMap = new HashMap<>();
            charCountMap.put(entry.getValue(), 1L);
            list.add(charCountMap);
            map.put(entry.getKey(), list);
        }
        return map;
    }

    private static String findLeftSub(String str) {
        return str.charAt(0) + "" + PAIR_INSERTIONS.get(str);
    }

    private static String findRightSub(String str) {
        return PAIR_INSERTIONS.get(str) + "" + str.charAt(1);
    }

    private static void addAll(Map<Character, Long> nextMap, Map<Character, Long> subMap) {
        for (Map.Entry<Character, Long> entry : subMap.entrySet()) {
            char key = entry.getKey();
            if (nextMap.containsKey(key)) {
                nextMap.put(key, nextMap.get(key) + entry.getValue());
            } else {
                nextMap.put(key, entry.getValue());
            }
        }
    }

    /**
     * B部分解答
     */
    private static long partB() {
        return getResult(40);
    }

}
