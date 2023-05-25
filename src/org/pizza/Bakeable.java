package org.pizza;

import java.util.Map;

public interface Bakeable {
    void showMainInfo();
    void showAdditionalInfo(Map<String, Integer> ingredientAndQuantity);

}
