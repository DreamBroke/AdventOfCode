package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Test4 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test1.class.getClassLoader().getResource("question4.txt")).getFile()));
        List<int[][]> boardList = new ArrayList<>(100);
        String markInputLine = textFile.nextLine();
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            int[][] board = new int[5][5];
            if (line.length() < 5) {
                continue;
            }
            for (int i = 0; i < 5; i++) {
                int[] boardLine = new int[5];
                board[i] = boardLine;
                String[] numStr = line.split(" ");
                for (int j = 0, k = 0; j < numStr.length; j++, k++) {
                    if (numStr[j].equals("")) {
                        k--;
                        continue;
                    }
                    boardLine[k] = Integer.parseInt(numStr[j]);
                }
                if (textFile.hasNextLine()) {
                    line = textFile.nextLine();
                }
            }
            boardList.add(board);
        }
        int[] markInput = Arrays.stream(markInputLine.split(",")).flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).toArray();
//        System.out.println(partA(markInput, boardList));
        System.out.println(partB(markInput, boardList));
    }

    /**
     * A部分解答
     */
    private static int partA(int[] markInput, List<int[][]> boardList) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < markInput.length; i++) {
            set.add(markInput[i]);
            if (i < 5) {
                continue;
            }
            for (int[][] board : boardList) {
                if (isContainsArray(set, board)) {
                    int result = getResult(set, board);
                    return result * markInput[i];
                }
            }
        }
        return 0;
    }

    private static int getResult(Collection<Integer> set, int[][] board) {
        int result = 0;
        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 5; k++) {
                int num = board[j][k];
                if (!set.contains(num)) {
                    result += num;
                }
            }
        }
        return result;
    }

    private static boolean isContainsArray(Collection<Integer> set, int[][] board) {
        for (int i = 0; i < 5; i++) {
            if ((set.contains(board[i][0]) && set.contains(board[i][1]) && set.contains(board[i][2]) && set.contains(board[i][3]) && set.contains(board[i][4]))
                    || (set.contains(board[0][i]) && set.contains(board[1][i]) && set.contains(board[2][i]) && set.contains(board[3][i]) && set.contains(board[4][i]))) {
                return true;
            }
        }
        return false;
    }

    /**
     * B部分解答
     */
    private static int partB(int[] markInput, List<int[][]> boardList) {
        List<Integer> list = new ArrayList<>();
        int[][] lastWinBoard = null;
        int lastWinNum = 0;
        for (int i = 0; i < markInput.length; i++) {
            list.add(markInput[i]);
            if (i < 5) {
                continue;
            }
            Iterator<int[][]> iterator = boardList.listIterator();
            while (iterator.hasNext()) {
                int[][] board = iterator.next();
                if (isContainsArray(list, board)) {
                    lastWinBoard = board;
                    lastWinNum = markInput[i];
                    iterator.remove();
                }
            }
        }
        Iterator<Integer> iterator = list.listIterator(list.indexOf(lastWinNum) + 1);
        while (iterator.hasNext()) {
            if (iterator.next() != null) {
                iterator.remove();
            }
        }
        int result = getResult(list, lastWinBoard);
        return result * lastWinNum;
    }
}
