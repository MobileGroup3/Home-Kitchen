package edu.scu.ytong.placingorder;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.OrderItem;

/**
 * Created by yizhou on 5/21/16.
 */
interface DishAddedListener {
    void onDishAdded(DishItem dish);
    void onDishReduced(OrderItem orderItem);
}
