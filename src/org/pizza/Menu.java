package org.pizza;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    String[] points;
    int length;
    Scanner scan = new Scanner(System.in);

    public Menu(String[] points) {
        length = points.length;
        this.points = points;
    }

    public int chooseAction() {
        int axis = 1;
        String userLine = null;
        while (!Objects.equals(userLine, " ")) {
            if (Objects.equals(userLine, "w") && axis > 1)
                axis--;
            if (Objects.equals(userLine, "s") && axis < length - 1)
                axis++;
            System.out.println(
                    "\033[32mChoose " +
                    points[0] +
                    "\033[0m"
            );
            for (int i = 1; i < length; i++) {
                if (axis == i) {
                    System.out.print("\033[47m");
                }
                System.out.println(points[i]);
                if (axis == i) {
                    System.out.print("\033[0m");
                }
            }
            userLine = scan.nextLine();
            Main.clearConsole();
        }
        return axis;
    }

    public int enterAction() {
        System.out.println(
                "\033[32mChoose the quantity (10g of each ingredient):\033[0m"
        );
        int userInt;
        try {
            userInt=scan.nextInt();
        } catch (Exception e){
            System.out.println("Enter normal number:");
            userInt=0;
        }
        Main.clearConsole();
        return userInt;
    }
}
