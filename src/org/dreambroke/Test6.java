package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test6 {

    private static final Map<Integer, Integer> listSizeMap = new HashMap<>();
    private static final Map<Integer, List<Integer>> loopListMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test6.class.getClassLoader().getResource("question6.txt")).getFile()));
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
        ArrayList<Integer> fishies = new ArrayList<>();
        long[] fish = new long[10];

        for (int i = 0; i < line.length() - 1; i++) {
            int indexOfComma = line.indexOf(",", i);
            fishies.add(Integer.parseInt(line.substring(i, indexOfComma)));
            i = indexOfComma;
        }
        fishies.add(Integer.parseInt(line.substring(line.lastIndexOf(",") + 1)));
        int days = 0;
        for (int i : fishies) {
            fish[i]++;
        }
        while (days < 256) {
            fish[9] += fish[0];
            fish[7] += fish[0];
            fish[0] = fish[1];
            fish[1] = fish[2];
            fish[2] = fish[3];
            fish[3] = fish[4];
            fish[4] = fish[5];
            fish[5] = fish[6];
            fish[6] = fish[7];
            fish[7] = fish[8];
            fish[8] = fish[9];
            fish[9] = 0;
            days++;
        }
        long total = 0;
        for (long l : fish) {
            total += l;
        }
        System.out.println(total);

    }

    /**
     * A部分解答
     */
    private static int partA(int[] inputArr) {
        int count = 0;
        for (int i : inputArr) {
            if (listSizeMap.containsKey(i)) {
                count += listSizeMap.get(i);
            } else {
                List<Integer> loopList = getListFromLoop(i, 80);
                listSizeMap.put(i, loopList.size());
                count += loopList.size();
            }
        }
        return count;
    }

    private static List<Integer> getListFromLoop(int initState, int loopTime) {
        List<Integer> list = new ArrayList<>();
        list.add(initState);
        for (int i = 0; i < loopTime; i++) {
            int beforeListSize = list.size();
            for (int j = 0; j < beforeListSize; j++) {
                int state = list.get(j);
                if (state != 0) {
                    list.set(j, --state);
                } else {
                    list.add(8);
                    list.set(j, 6);
                }
            }
        }
        return list;
    }

    /**
     * B部分解答
     */
    private static long partB(int[] inputArr) {
        long count = 0;
        for (int i = 0; i <= 8; i++) {
            if (!loopListMap.containsKey(i)) {
                List<Integer> loopList = getListFromLoop(i, 128);
                loopListMap.put(i, loopList);
            }
            if (!listSizeMap.containsKey(i)) {
                List<Integer> loopList = loopListMap.get(i);
                listSizeMap.put(i, loopList.size());
            }
        }
        for (int i : inputArr) {
            List<Integer> loopList = loopListMap.get(i);
            for (int j : loopList) {
                count += listSizeMap.get(j);
            }
        }
        return count;
    }
}
