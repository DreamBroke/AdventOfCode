package org.dreambroke;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test2 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner textFile = new Scanner(new File(Test1.class.getClassLoader().getResource("question2.txt").getFile()));
        List<String> commands = new ArrayList<>(1000);
        while (textFile.hasNextLine()) {
            String line = textFile.nextLine();
            commands.add(line);
        }
        int forward = 0;
        int depth = 0;
        int aim = 0;
        for (String s : commands) {
            String[] command = s.split(" ");
            if (command[0].equals("forward")) {
                forward += Integer.parseInt(command[1]);
                depth += aim * Integer.parseInt(command[1]);
            } else if (command[0].equals("down")) {
                aim += Integer.parseInt(command[1]);
            } else {
                aim -= Integer.parseInt(command[1]);
            }
        }
        System.out.println(forward);
        System.out.println(depth);
        System.out.println(forward * depth);
    }
}
