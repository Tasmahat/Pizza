package org.pizza;
import java.util.*;

public class Main {
    private static final List<String> readyPizzasAndCalzones = new ArrayList<>();
    private static final HashMap<String,Integer> cookingPizzasAndCalzones = new HashMap<>();
    private static boolean isTimerStopped;
    private static int indexForHashMap;

    public static final Menu actionMenu = new Menu (new String[] {
            "action",
            "Choose pizza",
            "Choose calzone",
            "Ready pizzas",
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
        if(!cookingPizzasAndCalzones.isEmpty()) {
            isTimerStopped = false;
            showProgress();
        }
        int userChoice=actionMenu.chooseAction();
        isTimerStopped = true;
        switch (userChoice) {
            case 1 -> {
                int sizeChoice = sizeMenu.chooseAction();
                if (sizeChoice == 4) {
                    showMainMenu();
                } else {
                    userChoice = pizzaMenu.chooseAction();
                    createPizza(userChoice, sizeChoice, null);
                }
            }
            case 2 -> {
                userChoice = calzoneMenu.chooseAction();
                createCalzone(userChoice);
            }
            case 3 -> {
                System.out.println("Pizzas and calzones finished cooking are:");
                for (String iter : readyPizzasAndCalzones) {
                    System.out.print(iter + ", ");
                }
                System.out.println("\n");
            }
            case 4 -> {
                System.out.println("The choice is unavailable");
                System.exit(1);
            }
            default -> System.out.println("An error has occurred");
        }
    }

    public static void createPizza(int userChoice, int sizeChoice, int[] forTest) {
        Pizza pizza = null;
        switch (userChoice) {
            case 1 -> pizza = BakeryFabric.Bakery(
                    BakeryFabric.Taste.Peperoni,
                    BakeryFabric.BakeryType.Pizza,
                    sizeChoice
            );
            case 2 -> pizza = BakeryFabric.Bakery(
                    BakeryFabric.Taste.Margaritha,
                    BakeryFabric.BakeryType.Pizza,
                    sizeChoice
            );
            case 3 -> pizza = BakeryFabric.Bakery(
                    BakeryFabric.Taste.ByChoice,
                    BakeryFabric.BakeryType.Pizza,
                    sizeChoice
            );
            case 4 -> showMainMenu();
            default -> System.out.println("An error has occurred");
        }
        if (pizza != null) {
            if(forTest==null) {
                userChoice = filterIngredientsMenu.chooseAction();
                if (userChoice == 1) {
                    chooseIngredients(pizza);
                } else if (userChoice == 3) {
                    showMainMenu();
                }
            }
            showPizza(pizza);
            setPizzaForCooking(pizza);
            if (forTest != null) {
                return;
            }
        }
        userChoice = pizzaMenu.chooseAction();
        createPizza(userChoice, sizeChoice, null);
    }

    public static void createCalzone(int userChoice) {
        Pizza calzone = null;
        switch (userChoice) {
            case 1 -> calzone = BakeryFabric.Bakery(
                    BakeryFabric.Taste.AllIncluded,
                    BakeryFabric.BakeryType.Calzone,
                    4
            );
            case 2 -> calzone = BakeryFabric.Bakery(
                    BakeryFabric.Taste.Peperoni,
                    BakeryFabric.BakeryType.Calzone,
                    4
            );
            case 3 -> calzone = BakeryFabric.Bakery(
                    BakeryFabric.Taste.ByChoice,
                    BakeryFabric.BakeryType.Calzone,
                    4
            );
            case 4 -> showMainMenu();
            default -> System.out.println("An error has occurred");
        }
        if (calzone != null) {
            userChoice = filterIngredientsMenu.chooseAction();
            if (userChoice == 1) {
                chooseIngredients(calzone);
            }
            else if (userChoice == 3) {
                showMainMenu();
            }
            showPizza(calzone);
            setPizzaForCooking(calzone);
        }
        userChoice = calzoneMenu.chooseAction();
        createCalzone(userChoice);
    }

    public static void showPizza(Pizza pizza) {
        pizza.showMainInfo();
        pizza.showAdditionalIngredients();
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

    public static void setPizzaForCooking(Pizza pizza) {
        indexForHashMap++;
        cookingPizzasAndCalzones.put(
                indexForHashMap + ". " + pizza.name +
                "(" + pizza.getClass().getSimpleName() + ")",
                30
        );
        startCountdown(pizza);
    }

    public static void startCountdown(Pizza pizza) {
        String keyForHashMap = indexForHashMap + ". " + pizza.name + "(" + pizza.getClass().getSimpleName() + ")";
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int oldValue = cookingPizzasAndCalzones.get(
                        keyForHashMap
                );
                if(oldValue<=0) {
                    cookingPizzasAndCalzones.remove (
                            keyForHashMap
                    );
                    readyPizzasAndCalzones.add (
                            keyForHashMap
                    );
                    cancel();
                } else {
                    cookingPizzasAndCalzones.replace(
                            keyForHashMap,
                            oldValue - 1
                    );
                }
            }
        };
        var timer = new Timer();
        var period = 1000L;
        timer.schedule(task,0,period);
    }

    public static void showProgress() {
        var timer = new Timer();
        long period = 3*1000L;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(cookingPizzasAndCalzones.isEmpty() || isTimerStopped) {
                    timer.cancel();
                } else {
                    clearConsole();
                    System.out.println("Time left for pizza and calzone to get ready is:");
                    for (Map.Entry<String, Integer> entry : cookingPizzasAndCalzones.entrySet()) {
                        String key = entry.getKey();
                        Integer value = entry.getValue();
                        System.out.println(key + ": " + value + "sec.");
                    }
                    System.out.print("\n");
                    actionMenu.showMenu();
                }
            }
        };
        timer.schedule(task,0,period);
    }

    public static void clearConsole() {
        System.out.println(System.lineSeparator().repeat(50));
    }
}
