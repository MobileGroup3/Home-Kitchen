package com.backendless.hk3.login.placingorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.hk3.login.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.backendless.hk3.login.entities.DishItem;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{


    private List<DishItem> dishItemList;
    private Context context;
    private DishAddedListener dishAddedListener;

    public MenuAdapter(Context context, List<DishItem> dishItemList, DishAddedListener dishAddedListener) {
        this.context = context;
        this.dishItemList = dishItemList;
        this.dishAddedListener = dishAddedListener;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImageView;
        TextView dishNameTextView;
        TextView dishDescriptionTextView;
        TextView dishPriceTextView;
        TextView dishRemainingNumberTextView;
        Button dishAddButton;

        public MyViewHolder(final View itemView) {
            super(itemView);
            dishImageView = (ImageView) itemView.findViewById(R.id.image_view_dish);
            dishNameTextView = (TextView) itemView.findViewById(R.id.text_view_dish_name);
            dishDescriptionTextView = (TextView) itemView.findViewById(R.id.text_view_dish_description);
            dishPriceTextView = (TextView) itemView.findViewById(R.id.text_view_dish_price);
            dishRemainingNumberTextView = (TextView) itemView.findViewById(R.id.text_view_remaining_number);
            dishAddButton = (Button) itemView.findViewById(R.id.button_add_dish);
        }

        void bind(final DishItem dish) {
            dishNameTextView.setText(dish.getName());
            dishDescriptionTextView.setText(dish.getDescription());
            dishPriceTextView.setText(String.valueOf(dish.getPrice()));
            dishRemainingNumberTextView.setText(String.valueOf(dish.getMax_num()));

            dishAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    double dishPrice = dish.getPrice();
                    int dishNumber = dish.getMax_num();

                    if(dishNumber == 0) {
                        Toast.makeText(itemView.getContext(),"Sold Out",Toast.LENGTH_SHORT).show();

                    }else {
                        dishNumber--;
                        dish.setMax_num(dishNumber);
                        dishAddedListener.onDishAdded(dish);

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dishItemList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.menu_view,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DishItem dish = dishItemList.get(position);
        Picasso.with(context).load(dish.getPicture()).into( holder.dishImageView);
        holder.bind(dish);

    }

    public void setData(List<DishItem> list) {
        dishItemList = list;
        notifyDataSetChanged();

    }
}
