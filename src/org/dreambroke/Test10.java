package org.dreambroke;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test10 {

    private static final Map<Character, Character> CORRUPTED_MAP = new HashMap<>() {{
       put('(', ')');
        put('[', ']');
        put('{', '}');
        put('<', '>');
    }};

    private static final Map<Character, Integer> ERROR_SCORE_MAP = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};

    private static final Map<Character, Integer> AUTOCOMPLETE_SCORE_MAP = new HashMap<>() {{
        put('(', 1);
        put('[', 2);
        put('{', 3);
        put('<', 4);
    }};

    public static void main(String[] args) throws IOException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test10.class.getClassLoader().getResource("question10.txt")).getFile()));
        int fileLine = 94;
        String[] inputs = new String[fileLine];
        for (int i = 0; i < fileLine; i++) {
            inputs[i] = textFile.nextLine();
        }
//        System.out.println(partA(inputs));
        System.out.println(partB(inputs));
    }


    /**
     * A部分解答
     */
    private static int partA(String[] inputs) {
        int sum = 0;
        for (String line : inputs) {
            char[] chars = line.toCharArray();
            Stack<Character> openStack = new Stack<>();
            for (char c : chars) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    openStack.push(c);
                } else {
                    if (openStack.empty() || c != CORRUPTED_MAP.get(openStack.pop())) {
                        sum += ERROR_SCORE_MAP.get(c);
                        System.out.println("line: " + line + ", paris[last]: " + CORRUPTED_MAP.get(openStack.pop()) + ", token: " + c);
                        break;
                    }
                }
            }
        }
        return sum;
    }

    /**
     * B部分解答
     */
    private static long partB(String[] inputs) {
        List<Long> allScore = new ArrayList<>();
        OUT:
        for (String line : inputs) {
            char[] chars = line.toCharArray();
            Stack<Character> openStack = new Stack<>();
            for (char c : chars) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    openStack.push(c);
                } else {
                    if (openStack.empty() || c != CORRUPTED_MAP.get(openStack.pop())) {
                        continue OUT;
                    }
                }
            }
            if (!openStack.empty()) {
                long sum = 0;
                int size = openStack.size();
                for (int i = 0; i < size; i++) {
                    sum = 5 * sum + AUTOCOMPLETE_SCORE_MAP.get(openStack.pop());
                }
                allScore.add(sum);
            }
        }
        allScore.sort(Comparator.naturalOrder());
        System.out.println(allScore);
        System.out.println(allScore.size());
        return allScore.get(allScore.size() / 2);
    }
}
