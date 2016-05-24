package edu.scu.ytong.placingorder.entities;

public class OrderItemSimple{

    private Integer orderQuantity;
    private DishItem dishItem;

    public OrderItemSimple(DishItem dishItem, int orderQuantity) {
        this.dishItem = dishItem;
        this.orderQuantity = orderQuantity;
    }



    public Integer getOrderQuantity()
    {
        return orderQuantity;
    }

    public void setOrderQuantity( Integer orderQuantity )
    {
        this.orderQuantity = orderQuantity;
    }

    public DishItem getDishItem()
    {
        return dishItem;
    }

    public void setDishItem(DishItem dishItem)
    {
        this.dishItem = dishItem;
    }
}
