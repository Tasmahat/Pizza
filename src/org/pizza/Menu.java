package org.pizza;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    public final String[] points;
    private final int length;
    private final Scanner scan = new Scanner(System.in);
    private int selected;

    public Menu(String[] points) {
        length = points.length;
        this.points = points;
    }

    public int chooseAction() {
        selected = 1;
        String userLine = null;
        while (!Objects.equals(userLine, " ")) {
            if (Objects.equals(userLine, "w") && selected > 1) {
                selected--;
            } else if (Objects.equals(userLine, "s") && selected < length - 1) {
                selected++;
            }
            System.out.println(
                    "\033[32mChoose " +
                    points[0] +
                    "\033[0m"
            );
            for (var i = 1; i < length; i++) {
                if (selected == i) {
                    System.out.print("\033[47m");
                }
                System.out.println(points[i]);
                if (selected == i) {
                    System.out.print("\033[0m");
                }
            }
            userLine = scan.nextLine();
            Main.clearConsole();
        }
        return selected;
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

    public void showMenu() {
        System.out.println(
                "\033[32mChoose " +
                        points[0] +
                        "\033[0m"
        );
        for (int i = 1; i < length; i++) {
            if (selected == i) {
                System.out.print("\033[47m");
            }
            System.out.println(points[i]);
            if (selected == i) {
                System.out.print("\033[0m");
            }
        }
    }
}
