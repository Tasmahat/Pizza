package org.pizza;
import java.util.*;

public class Main {
    private static final List<String> readyPizzasAndCalzones = new ArrayList<>();
    private static final HashMap<String,Integer> cookingPizzasAndCalzones = new HashMap<>();
    private static boolean stop;

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
            stop=false;
            showProgress();
        }
        int userChoice=actionMenu.chooseAction();
        switch (userChoice) {
            case 1 -> {
                stop = true;
                int sizeChoice = sizeMenu.chooseAction();
                userChoice = pizzaMenu.chooseAction();
                showPizza(userChoice, sizeChoice, null);
            }
            case 2 -> {
                stop = true;
                userChoice = calzoneMenu.chooseAction();
                showCalzone(userChoice);
            }
            case 3 -> {
                stop = true;
                System.out.println("Pizzas and calzones finished cooking are:");
                for (String iter : readyPizzasAndCalzones) {
                    System.out.print(iter + ", ");
                }
                System.out.println("\n");
                showMainMenu();
            }
            case 4 -> {
                stop = true;
                System.out.println("The choice is unavailable");
                System.exit(1);
            }
            default -> System.out.println("An error has occurred");
        }
    }

    public static void showPizza (int userChoice, int sizeChoice, int[] forTest) {
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
            cookingPizzasAndCalzones.put(pizza.name + "(Pizza)",30);
            setTimer(pizza);
            startCountdown(pizza);
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
            cookingPizzasAndCalzones.put(calzone.name + "(Calzone)",30);
            setTimer(calzone);
            startCountdown(calzone);
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

    public static void setTimer(Pizza pizza) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                cookingPizzasAndCalzones.remove (
                        pizza.name +
                        "(" + pizza.getClass().getSimpleName() + ")"
                );
                readyPizzasAndCalzones.add (
                        pizza.name +
                        "(" + pizza.getClass().getSimpleName() + ")"
                );
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 30*1000L;
        timer.schedule(task,delay);
    }

    public static void startCountdown(Pizza pizza) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int oldValue = cookingPizzasAndCalzones.get(
                        pizza.name +
                                "(" + pizza.getClass().getSimpleName() + ")"
                );
                if(oldValue<=1) {
                    cancel();
                }
                cookingPizzasAndCalzones.replace(
                        pizza.name +
                        "(" + pizza.getClass().getSimpleName() + ")",
                        oldValue-1
                );
            }
        };
        Timer timer = new Timer();
        long period = 1000L;
        timer.schedule(task,0,period);
    }

    public static void showProgress() {
        Timer timer = new Timer();
        long period = 3*1000L;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(cookingPizzasAndCalzones.isEmpty() || stop) {
                    timer.cancel();
                }
                clearConsole();
                if(!cookingPizzasAndCalzones.isEmpty()){
                    System.out.println("Time left for pizza and calzone to get ready is:");
                }
                for (Map.Entry<String, Integer> entry : cookingPizzasAndCalzones.entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println(key + ": " + value + "sec.");
                }
                System.out.print("\n");
                actionMenu.showMenu();
            }
        };
        timer.schedule(task,0,period);
    }

    public static void clearConsole() {
        System.out.println(System.lineSeparator().repeat(50));
    }
}
