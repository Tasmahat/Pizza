package org.pizza;

import java.util.List;

public class Calzone extends Pizza implements Bakeable{
    public Calzone(String name, List<Ingredients> ingredients, Sizes size) {
        super(name, ingredients, size);
    }
}
