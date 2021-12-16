package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test08 {

    private Map<Signal, Character> positionCharMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test08.class.getClassLoader().getResource("question08.txt")).getFile()));
        List<List<String>> signalPatternsList = new ArrayList<>(200);
        List<List<String>> digitOutputList = new ArrayList<>(200);
        List<String> allDigitOutput = new ArrayList<>(800);
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            String[] singleInput = line.split("\\|");
            List<String> patterns = new ArrayList<>(10);
            patterns.addAll(Arrays.stream(singleInput[0].split(" +")).toList());
            signalPatternsList.add(patterns);
            List<String> digitOutput = new ArrayList<>(10);
            digitOutput.addAll(Arrays.stream(singleInput[1].split(" +")).toList());
            digitOutputList.add(digitOutput);
            allDigitOutput.addAll(digitOutput);
        }
        Test08 main = new Test08();
//        System.out.println(partA(allDigitOutput));
        long start = System.currentTimeMillis();
        System.out.println(main.partB(signalPatternsList, digitOutputList));
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void otherSolution(String line) {

    }

    /**
     * A部分解答
     */
    private int partA(List<String> digitOutput) {
        int count = 0;
        for (String digit : digitOutput) {
            if (digit.length() == 2 || digit.length() == 4 || digit.length() == 3 || digit.length() == 7) {
                count++;
            }
        }
        return count;
    }

    /**
     * B部分解答
     */
    private long partB(List<List<String>> signalPatternsList, List<List<String>> digitOutputList) {
        int sum = 0;
        for (int i = 0; i < digitOutputList.size(); i++) {
            initPositionCharMap(signalPatternsList.get(i));
            for (int j = 0; j < 4; j++) {
                int digit = getDigitFromString(digitOutputList.get(i).get(j));
                sum += digit * Math.pow(10, 3 - j);
            }
        }
        return sum;
    }

    private int getDigitFromString(String input) {
        int length = input.length();
        if (length == 2) {
            return 1;
        } else if (length == 4) {
            return 4;
        } else if (length == 3) {
            return 7;
        } else if (length == 7) {
            return 8;
        } else if (!input.contains(positionCharMap.get(Signal.CENTRE).toString())) {
            return 0;
        } else if (!input.contains(positionCharMap.get(Signal.UP_RIGHT).toString()) && !input.contains(positionCharMap.get(Signal.DOWN_LEFT).toString())) {
            return 5;
        } else if (!input.contains(positionCharMap.get(Signal.DOWN_LEFT).toString()) && !input.contains(positionCharMap.get(Signal.UP_LEFT).toString())) {
            return 3;
        } else if (!input.contains(positionCharMap.get(Signal.DOWN_LEFT).toString())) {
            return 9;
        } else if (!input.contains(positionCharMap.get(Signal.UP_RIGHT).toString())) {
            return 6;
        }
        return 2;
    }

    private void initPositionCharMap(List<String> signalPatterns) {
        positionCharMap = new HashMap<>();
        int[] counts = new int[7];
        for (String pattern : signalPatterns) {
            for (char c : pattern.toCharArray()) {
                switch (c) {
                    case 'a' -> counts[0]++;
                    case 'b' -> counts[1]++;
                    case 'c' -> counts[2]++;
                    case 'd' -> counts[3]++;
                    case 'e' -> counts[4]++;
                    case 'f' -> counts[5]++;
                    case 'g' -> counts[6]++;
                }
            }
        }
        StringBuilder seven = new StringBuilder();
        String one = "";
        String four = "";
        for (String s : signalPatterns) {
            if (s.length() == 3) {
                seven = new StringBuilder(s);
            } else if (s.length() == 2) {
                one = s;
            } else if (s.length() == 4) {
                four = s;
            }
            if (one.length() != 0 && four.length() != 0 && seven.length() != 0) {
                break;
            }
        }
        for (Character c : one.toCharArray()) {
            seven.deleteCharAt(seven.indexOf(c.toString()));
        }
        positionCharMap.put(Signal.UP, seven.charAt(0));
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 9) {
                positionCharMap.put(Signal.DOWN_RIGHT, (char) ('a' + i));
            } else if (counts[i] == 4) {
                positionCharMap.put(Signal.DOWN_LEFT, (char) ('a' + i));
            } else if (counts[i] == 6) {
                positionCharMap.put(Signal.UP_LEFT, (char) ('a' + i));
            } else if (counts[i] == 8 && i != positionCharMap.get(Signal.UP) - 'a') {
                positionCharMap.put(Signal.UP_RIGHT, (char) ('a' + i));
            } else {
                if (counts[i] == 7) {
                    if (four.contains(String.valueOf((char) ('a' + i)))) {
                        positionCharMap.put(Signal.CENTRE, (char) ('a' + i));
                    } else {
                        positionCharMap.put(Signal.DOWN, (char) ('a' + i));
                    }
                }
            }
        }
    }
}

enum Signal {
    UP,
    DOWN,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    CENTRE
}
