package edu.scu.ytong.placingorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.OrderItem;


public class OrderAdapter extends ArrayAdapter<OrderItem> {

    List<OrderItem> orderList;
    TextView orderDishNameTextView;
    TextView orderDishPriceTextView;
    TextView orderDishQuantityTextView;


    public OrderAdapter(Context context, int resource, List<OrderItem> orderList) {
        super(context,resource,orderList);
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.order_conformation_item_view, parent, false);

        }

        final DishItem dish = orderList.get(position).getDishItem();
        orderDishNameTextView = (TextView) convertView.findViewById(R.id.text_view_order_dish_name);
        orderDishNameTextView.setText(dish.getName());

        orderDishPriceTextView = (TextView) convertView.findViewById(R.id.text_view_order_dish_price);
        orderDishPriceTextView.setText("$" + String.valueOf(dish.getPrice()));

        orderDishQuantityTextView = (TextView) convertView.findViewById(R.id.text_view_order_quantity);
        orderDishQuantityTextView.setText("X " +String.valueOf(orderList.get(position).getOrderQuantity()));

        return convertView;
    }

    public void setData(List<OrderItem> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();


    }



}
