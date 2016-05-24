package edu.scu.ytong.placingorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.OrderItem;


public class CartAdapter extends ArrayAdapter<OrderItem>{
    DishAddedListener dishAddedListener;
    List<OrderItem> cartList;
    TextView cartDishNameTextView;
    TextView cartDishPriceTextView;
    TextView cartDishQuantityTextView;
    Button addDishInCart;
    Button reduceDishInCart;

    public CartAdapter(Context context, int resource, List<OrderItem> cartList,DishAddedListener dishAddedListener) {
        super(context,resource,cartList);
        this.cartList = cartList ;
        this.dishAddedListener = dishAddedListener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.shopping_cart_item_view, parent, false);

        }
        final OrderItem orderItem = cartList.get(position);
        final DishItem dish = orderItem.getDishItem();
        cartDishNameTextView = (TextView) convertView.findViewById(R.id.text_view_shopping_dish_name);
        cartDishNameTextView.setText(dish.getName());

        cartDishPriceTextView = (TextView) convertView.findViewById(R.id.text_view_shopping_dish_price);
        cartDishPriceTextView.setText("$" + String.valueOf(dish.getPrice()));

        cartDishQuantityTextView = (TextView) convertView.findViewById(R.id.text_view_shopping_dish_quantity);
        cartDishQuantityTextView.setText("X " + String.valueOf(cartList.get(position).getOrderQuantity()));

        addDishInCart = (Button) convertView.findViewById(R.id.button_cart_add_dish);

        addDishInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dishNumber = dish.getMax_num();

                if(dishNumber == 0) {
                    Toast.makeText(v.getContext(),"Sold Out",Toast.LENGTH_SHORT).show();

                }else {
                    dishNumber--;
                    dish.setMax_num(dishNumber);
                    dishAddedListener.onDishAdded(dish);


                }


            }
        });

        reduceDishInCart = (Button) convertView.findViewById(R.id.button_cart_reduce_dish);
        reduceDishInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishAddedListener.onDishReduced(orderItem);
            }
        });

        return convertView;



    }


}
