package com.backendless.hk3.login.placingorder;

import com.backendless.hk3.login.entities.DishItem;
import com.backendless.hk3.login.entities.OrderItem;


interface DishAddedListener {
    void onDishAdded(DishItem dish);
    void onDishReduced(OrderItem orderItem);
}
