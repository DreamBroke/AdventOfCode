package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Test3 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Objects.requireNonNull(Test3.class.getClassLoader().getResource("question3.txt")).getFile()));
        List<String> strArr = new ArrayList<>(1000);
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            strArr.add(line);
        }
        String oxygenRate = "";
        String co2Rate = "";
        int lastOxygenCount = 0;
        int lastCo2Count = 0;
        boolean oxygenFlag = true;
        boolean co2Flag = true;
        for (int i = 0; i < strArr.get(0).length(); i++) {
            int firstIs1Count = 0;
            int oxygenCount = 0;
            int co2Count = 0;
            String lastOxygenRate = "";
            String lastCo2Rate = "";
            for (String str : strArr) {
                if (i == 0) {
                    if (str.charAt(i) == '1') {
                        firstIs1Count++;
                    }
                } else {
                    if (str.startsWith(oxygenRate) && str.charAt(i) == '1') {
                        oxygenCount++;
                        lastOxygenRate = str;
                    }
                    if (str.startsWith(co2Rate)) {
                        lastCo2Rate = str;
                        if (str.charAt(i) == '1') {
                            co2Count++;
                        }
                    }
                }
            }
            if (i == 0) {
                if (firstIs1Count > strArr.size() / 2) {
                    oxygenRate += "1";
                    co2Rate += "0";
                    lastOxygenCount = firstIs1Count;
                    lastCo2Count = strArr.size() - firstIs1Count;
                } else {
                    oxygenRate += "0";
                    co2Rate += "1";
                    lastCo2Count = firstIs1Count;
                    lastOxygenCount = strArr.size() - firstIs1Count;
                }
            } else {
                if (oxygenFlag) {
                    if (lastOxygenCount == 1) {
                        oxygenRate += lastOxygenRate.substring(i);
                        oxygenFlag = false;
                    } else if (oxygenCount > ((double) lastOxygenCount) / 2) {
                        oxygenRate += "1";
                        lastOxygenCount = oxygenCount;
                    } else if (oxygenCount < ((double) lastOxygenCount) / 2) {
                        oxygenRate += "0";
                        lastOxygenCount = lastOxygenCount - oxygenCount;
                    } else {
                        oxygenRate += "1";
                        lastOxygenCount = oxygenCount;
                    }
                }
                if (co2Flag) {
                    if (lastCo2Count == 1) {
                        co2Rate += lastCo2Rate.substring(i);
                        co2Flag = false;
                    } else if (co2Count == 0) {
                        co2Rate += "1";
                    } else if (co2Count > ((double) lastCo2Count) / 2) {
                        co2Rate += "0";
                        lastCo2Count = lastCo2Count - co2Count;
                    } else if (co2Count < ((double) lastCo2Count) / 2) {
                        co2Rate += "1";
                        lastCo2Count = co2Count;
                    } else {
                        co2Rate += "0";
                        lastCo2Count = co2Count;
                    }
                }
            }
        }
        System.out.println(oxygenRate);
        System.out.println(co2Rate);
        System.out.println(Integer.parseInt(oxygenRate, 2) * Integer.parseInt(co2Rate, 2));
    }
}
