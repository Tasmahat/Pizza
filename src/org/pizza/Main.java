package org.pizza;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static final Menu actionMenu = new Menu (new String[] {
            "action",
            "Choose pizza",
            "Choose calzone",
            "\033[31mExit(unavailable)\033[0m"
    });

    public static final Menu pizzaMenu = new Menu (new String[] {
            "pizza",
            "Peperoni",
            "Margaritha",
            "Make your own pizza",
            "\033[31mReturn to main menu\033[0m"
    });

    public static final Menu calzoneMenu = new Menu (new String[] {
            "calzone",
            "All included",
            "Peperoni calzone",
            "Make your own calzone",
            "\033[31mReturn to main menu\033[0m"
    });

    public static final Menu sizeMenu = new Menu (new String[] {
            "size",
            "Small",
            "Medium",
            "Large",
            "\033[31mReturn to main menu\033[0m"
    });

    public static final Menu filterIngredientsMenu = new Menu (new String[] {
            "additional ingredients?",
            "Yes",
            "No",
            "\033[31mReturn to main menu\033[0m"
    });

    public static final Menu ingredientsMenu = new Menu (new String[] {
            "additional ingredients",
            "cheese",
            "peperoni",
            "tomatoes",
            "ham",
            "\033[31mThat's enough ingredients\033[0m",
            "\033[31mReturn to main menu\033[0m"
    });

    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        int userChoice = actionMenu.chooseAction();
        switch (userChoice) {
            case 1 -> {
                int sizeChoice = sizeMenu.chooseAction();
                userChoice = pizzaMenu.chooseAction();
                showPizza(userChoice, sizeChoice, null);
            }
            case 2 -> {
                userChoice = calzoneMenu.chooseAction();
                showCalzone(userChoice);
            }
            case 3 -> {
                System.out.println("The choice is unavailable");
                System.exit(1);
            }
            default -> System.out.println("An error has occurred");
        }
    }
    // meow
    public static void showPizza(int userChoice, int sizeChoice, int[] forTest) {
        Pizza pizza = null;
        switch (userChoice) {
            case 1 -> pizza = new Pizza("Peperoni", new ArrayList<>(){{
                add(Ingredients.dough);
                add(Ingredients.cheese);
                add(Ingredients.peperoni);
            }});
            case 2 -> pizza = new Pizza("Margaritha", new ArrayList<>(){{
                add(Ingredients.dough);
                add(Ingredients.cheese);
                add(Ingredients.tomatoes);
            }});
            case 3 -> {
                List<Ingredients> pizzaByChoiceList = new ArrayList<>();
                pizzaByChoiceList.add(Ingredients.dough);
                if (forTest == null) {
                    do {
                        userChoice = ingredientsMenu.chooseAction();
                        switch (userChoice) {
                            case 1 -> pizzaByChoiceList.add(Ingredients.cheese);
                            case 2 -> pizzaByChoiceList.add(Ingredients.peperoni);
                            case 3 -> pizzaByChoiceList.add(Ingredients.tomatoes);
                            case 4 -> pizzaByChoiceList.add(Ingredients.ham);
                        }
                        System.out.println("Current ingredients in pizza are:");
                        System.out.println(pizzaByChoiceList);
                    } while (userChoice != 5);
                }
                else {
                    for(int i=0; i<forTest.length; i++)
                    {
                        switch (forTest[i]) {
                            case 1 -> pizzaByChoiceList.add(Ingredients.cheese);
                            case 2 -> pizzaByChoiceList.add(Ingredients.peperoni);
                            case 3 -> pizzaByChoiceList.add(Ingredients.tomatoes);
                            case 4 -> pizzaByChoiceList.add(Ingredients.ham);
                        }
                    }
                }
                pizza = new Pizza("Pizza by choice", pizzaByChoiceList);
            }
            case 4 -> showMainMenu();
            default -> System.out.println("An error has occurred");
        }
        if (pizza != null) {
            switch (sizeChoice) {
                case 1 -> pizza.size = Sizes.Small;
                case 2 -> pizza.size = Sizes.Medium;
                case 3 -> pizza.size = Sizes.Large;
                case 4 -> showMainMenu();
                default -> System.out.println("An error has occurred");
            }
            if(forTest==null) {
                userChoice = filterIngredientsMenu.chooseAction();
                if (userChoice == 1) {
                    chooseIngredients(pizza);
                } else if (userChoice == 3) {
                    showMainMenu();
                }
            }
            pizza.showValues();
            if (forTest != null) {
                return;
            }
        }
        userChoice = pizzaMenu.chooseAction();
        showPizza(userChoice, sizeChoice, null);
    }

    public static void showCalzone(int userChoice) {
        Calzone calzone = null;
        switch (userChoice) {
            case 1 -> calzone = new Calzone("All included", new ArrayList<>(){{
                add(Ingredients.dough);
                add(Ingredients.cheese);
                add(Ingredients.peperoni);
                add(Ingredients.tomatoes);
            }});
            case 2 -> calzone = new Calzone("Peperoni", new ArrayList<>(){{
                add(Ingredients.dough);
                add(Ingredients.cheese);
                add(Ingredients.peperoni);
            }});
            case 3 -> {
                List<Ingredients> calzoneByChoiceList = new ArrayList<>();
                calzoneByChoiceList.add(Ingredients.dough);
                do{
                    userChoice=ingredientsMenu.chooseAction();
                    switch (userChoice) {
                        case 1 -> calzoneByChoiceList.add(Ingredients.cheese);
                        case 2 -> calzoneByChoiceList.add(Ingredients.peperoni);
                        case 3 -> calzoneByChoiceList.add(Ingredients.tomatoes);
                        case 4 -> calzoneByChoiceList.add(Ingredients.ham);
                    }
                    System.out.println("Current ingredients in calzone are:");
                    System.out.println(calzoneByChoiceList);
                }while(userChoice!=5);
                calzone = new Calzone("Calzone by choice", calzoneByChoiceList);
            }
            case 4 -> showMainMenu();
            default -> System.out.println("An error has occurred");
        }
        if (calzone != null) {
            calzone.size = Sizes.Average;
            userChoice=filterIngredientsMenu.chooseAction();
            if (userChoice == 1) {
                chooseIngredients(calzone);
            }
            else if (userChoice == 3) {
                showMainMenu();
            }
            calzone.showValues();
        }
        userChoice = calzoneMenu.chooseAction();
        showCalzone(userChoice);
    }

    public static void chooseIngredients(Pizza pizza) {
        while(true) {
            System.out.println("You have chosen:");
            System.out.println(pizza.ingredientAndQuantity);
            int userChoice = ingredientsMenu.chooseAction();
            if (userChoice > 0 && userChoice < 5) {
                pizza.ingredientAndQuantity.put(ingredientsMenu.points[userChoice], actionMenu.enterAction());
            } else if (userChoice == 5) {
                return;
            } else {
                showMainMenu();
            }
        }
    }

    public static void clearConsole() {
        System.out.println(System.lineSeparator().repeat(50));
    }
}
