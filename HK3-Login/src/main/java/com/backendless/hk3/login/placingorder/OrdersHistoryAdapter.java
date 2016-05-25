package com.backendless.hk3.login.placingorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.backendless.hk3.login.R;
import com.backendless.hk3.login.entities.DishItem;
import com.backendless.hk3.login.entities.Order;
import com.backendless.hk3.login.entities.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.List;


public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.MyViewHolder> {

    private Context context;
    List<Order> orderList;
    RelativeLayout dishTwoLayout;

    public OrdersHistoryAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;

    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView kitchenNameTextView;

        TextView dishNameOneTextView;
        TextView dishDescriptionOneTextView;
        TextView dishQuantityOneTextView;

        TextView dishNameTwoTextView;
        TextView dishDescriptionTwoTextView;
        TextView dishQuantityTwoTextView;

        ImageView dishPicOneImageView;
        ImageView dishPicTwoImageView;

        TextView totalAmountTextView;

        public MyViewHolder(View item) {

            super(item);
            kitchenNameTextView = (TextView) item.findViewById(R.id.text_view_kitchen_name);

            dishNameOneTextView = (TextView) item.findViewById(R.id.text_view_dish_name_one);
            dishDescriptionOneTextView = (TextView) item.findViewById(R.id.text_view_dish_description_one);
            dishQuantityOneTextView = (TextView) item.findViewById(R.id.text_view_dish_quantity_one);
            dishPicOneImageView = (ImageView) item.findViewById(R.id.image_view_dish_one);


            dishNameTwoTextView = (TextView) item.findViewById(R.id.text_view_dish_name_two);
            dishDescriptionTwoTextView = (TextView) item.findViewById(R.id.text_view_dish_description_two);
            dishQuantityTwoTextView = (TextView) item.findViewById(R.id.text_view_dish_quantity_two);
            dishPicTwoImageView = (ImageView) item.findViewById(R.id.image_view_dish_two);
            totalAmountTextView = (TextView) item.findViewById(R.id.text_view_total_amount);

            dishTwoLayout = (RelativeLayout) item.findViewById(R.id.layout_dish_two);



        }
        void bind(final Order order) {
            if(order.getOrderItem().size() < 2) {
                OrderItem orderItemOne = order.getOrderItem().get(0);
                DishItem dishOne = orderItemOne.getDishItem();

                kitchenNameTextView.setText(order.getKitchen_name());
                dishNameOneTextView .setText(dishOne.getName());
                dishDescriptionOneTextView.setText(dishOne.getDescription());
                dishQuantityOneTextView.setText("X" + orderItemOne.getOrderQuantity());

                dishTwoLayout.setVisibility(View.GONE);

            }else {
                OrderItem orderItemOne = order.getOrderItem().get(0);
                DishItem dishOne = orderItemOne.getDishItem();

                kitchenNameTextView.setText(order.getKitchen_name());
                dishNameOneTextView .setText(dishOne.getName());
                dishDescriptionOneTextView.setText(dishOne.getDescription());
                dishQuantityOneTextView.setText("X" + orderItemOne.getOrderQuantity());

                OrderItem orderItemTwo = order.getOrderItem().get(1);
                DishItem dishTwo = orderItemTwo.getDishItem();
                dishNameTwoTextView .setText(dishTwo.getName());
                dishDescriptionTwoTextView.setText(dishTwo.getDescription());
                dishQuantityTwoTextView.setText("X" + orderItemTwo.getOrderQuantity());
            }

            totalAmountTextView.setText(String.valueOf("Total Amount : $" + order.getTotalAmount()));
            kitchenNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,PlacingOrderActivity.class);
                    intent.putExtra("object_id_extra_key",order.getKitchen_object_id());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.order_history_item_view,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        String urlOne = order.getOrderItem().get(0).getDishItem().getPicture();
        Picasso.with(context).load(urlOne).into( holder.dishPicOneImageView);
        if(order.getOrderItem().size() > 1) {
            String urlTwo = order.getOrderItem().get(1).getDishItem().getPicture();
            Picasso.with(context).load(urlTwo).into( holder.dishPicTwoImageView);
        }
        holder.bind(order);

    }

    public void setData(List<Order> list) {
        this.orderList = list;
        notifyDataSetChanged();;

    }
}
