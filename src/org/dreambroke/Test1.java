package org.dreambroke;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Test1 {

    public static void main(String[] args) throws IOException {
        Scanner textFile = new Scanner(new File(Test1.class.getClassLoader().getResource("question1.txt").getFile()));
        List<String> strArr = new ArrayList<>(2000);
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            strArr.add(line);
        }
        int[] intArr = strArr.stream().flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).toArray();
        int count = 0;
        for (int i = 3; i < intArr.length; i++) {
            int before = intArr[i - 3] + intArr[i - 2] + intArr[i - 1];
            int now = intArr[i - 2] + intArr[i - 1] + intArr[i];
            if (now > before) {
                count++;
            }
        }
        System.out.println(count);
    }
}
