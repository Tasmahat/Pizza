package org.pizza;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Pizza {
    String name;
    List<Ingredients> ingredients;
    Sizes size;
    HashMap<String, Integer> ingredientAndQuantity = new HashMap<>();

    public Pizza (String name, List<Ingredients> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public void showValues() {
        File myFile= new File("log.txt");
        PrintWriter out = null;
        HashMap<Ingredients,Integer> allIngredients= new HashMap<>();
        allIngredients.put(Ingredients.cheese,0);
        allIngredients.put(Ingredients.tomatoes,0);
        allIngredients.put(Ingredients.peperoni,0);
        allIngredients.put(Ingredients.ham,0);

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
            if(element == Ingredients.dough) {
                System.out.print("dough ");
                out.print("dough ");
            } else if(element == Ingredients.cheese) {
                System.out.print("cheese ");
                out.print("cheese ");
            } else if (element==Ingredients.peperoni) {
                System.out.print("peperoni ");
                out.print("peperoni ");
            } else if (element == Ingredients.tomatoes) {
                System.out.print("tomatoes ");
                out.print("tomatoes ");
            } else if (element == Ingredients.ham) {
                System.out.print("ham ");
                out.print("ham ");
            }
        }

        System.out.print("\n");
        out.print("\n");
        out.close();
        if(!ingredientAndQuantity.isEmpty())
        {
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
