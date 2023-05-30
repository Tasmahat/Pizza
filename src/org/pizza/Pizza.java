package org.pizza;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pizza {
    public final String name;
    private final List<Ingredients> ingredients;
    public final Sizes size;
    public final Map<String, Integer> ingredientAndQuantity = new HashMap<>();

    public Pizza (String name, List<Ingredients> ingredients, Sizes size) {
        this.name = name;
        this.ingredients = ingredients;
        this.size=size;
    }

    public void showMainInfo() {
        var myFile= new File("log.txt");
        PrintWriter out;

        try {
            out = new PrintWriter(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        out.print(
                this.getClass().getSimpleName() + "'s name is: " + name + "\n" +
                this.getClass().getSimpleName() + "'s size is: " + size + "\n" +
                this.getClass().getSimpleName() + " contains: ");
        System.out.print(
                this.getClass().getSimpleName() + "'s name is: " + name + "\n" +
                this.getClass().getSimpleName() + "'s size is: " + size + "\n" +
                this.getClass().getSimpleName() + " contains: ");

        for (Ingredients element : ingredients) {
            switch (element) {
                case dough -> {
                    System.out.print("dough ");
                    out.print("dough ");
                }

                case cheese -> {
                    System.out.print("cheese ");
                    out.print("cheese ");
                }

                case peperoni -> {
                    System.out.print("peperoni ");
                    out.print("peperoni ");
                }

                case tomatoes -> {
                    System.out.print("tomatoes ");
                    out.print("tomatoes ");
                }

                case ham -> {
                    System.out.print("ham ");
                    out.print("ham ");
                }

                default -> {
                    System.out.println("An error has occurred");
                    out.print("An error has occurred");
                }
            }
        }

        System.out.print("\n");
        out.print("\n");
        out.close();
    }

    public void showAdditionalIngredients() {
        if(!ingredientAndQuantity.isEmpty()) {
            System.out.println (
                    "Additional ingredients:"
            );
            for(Map.Entry<String,Integer> entry: ingredientAndQuantity.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.print(key + " x" + value +", ");
            }
        }
        System.out.println("\n");
    }
}
