package org.pizza;

import java.util.ArrayList;
import java.util.List;

public abstract class BakeryFabric {
    enum Taste {
        Margaritha,
        Peperoni,
        AllIncluded,
        ByChoice
    }

    enum BakeryType {
        Pizza,
        Calzone
    }

    public static Bakeable Bakery(Taste taste, BakeryType type, int sizeChoice) {
        List<Ingredients> ingredients = new ArrayList<>();
        String name = new String();
        Sizes size = null;
        switch (taste) {
            case Margaritha -> {
                ingredients.add(Ingredients.dough);
                ingredients.add(Ingredients.cheese);
                ingredients.add(Ingredients.tomatoes);
                name = "Margaritha";
            }

            case Peperoni -> {
                ingredients.add(Ingredients.dough);
                ingredients.add(Ingredients.cheese);
                ingredients.add(Ingredients.peperoni);
                name = "Peperoni";
            }

            case AllIncluded -> {
                ingredients.add(Ingredients.dough);
                ingredients.add(Ingredients.cheese);
                ingredients.add(Ingredients.peperoni);
                ingredients.add(Ingredients.tomatoes);
                name = "All Included";
            }

            case ByChoice -> {
                int userChoice;
                ingredients.add(Ingredients.dough);
                do {
                    userChoice = Main.ingredientsMenu.chooseAction();
                    switch (userChoice) {
                        case 1 -> ingredients.add(Ingredients.cheese);
                        case 2 -> ingredients.add(Ingredients.peperoni);
                        case 3 -> ingredients.add(Ingredients.tomatoes);
                        case 4 -> ingredients.add(Ingredients.ham);
                        default -> System.out.println("An error has occurred");
                    }
                    System.out.println("Current ingredients in calzone are:");
                    System.out.println(ingredients);
                } while(userChoice!=5);
                name = type.name() + " by choice";
            }
        }

        switch (sizeChoice) {
            case 1 -> size = Sizes.Small;
            case 2 -> size = Sizes.Medium;
            case 3 -> size = Sizes.Large;
            case 4 -> size = Sizes.Average;
            default -> System.out.println("An error has occurred");
        }

        Pizza pizza = null;

        switch (type) {
            case Pizza -> {
                pizza = new Pizza(name, ingredients, size);
            }
            case Calzone -> {
                pizza = new Calzone(name, ingredients, size);
            }
        }
        return pizza;
    }
}
