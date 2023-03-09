package org.pizza;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

class PizzaTest {

    private static final String successful = " was successful";
    private static final String failed = " has failed";

    @Test
    void testNameRepetition () throws IOException {
        int[] cheese = new int[]{1,1};
        Main.showPizza(3,1,cheese);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("log.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int countLines = 0;
        Scanner scan = new Scanner(new File("log.txt"));
        while (scan.hasNext()) {
            String word = scan.next();
            if(word.equals("cheese")) {
                countLines++;
            }
        }
        if (countLines == cheese.length) {
            System.out.println("The test on name repetition" + successful);
        } else {
            System.out.println("The test on name repetition" + failed);
            System.out.println(cheese.length + " " + countLines);
        }
    }
}
