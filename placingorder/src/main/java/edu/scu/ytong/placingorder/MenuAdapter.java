package edu.scu.ytong.placingorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<DishItem> dishItemList;
    private Context context;

    public MenuAdapter(Context context,List<DishItem> dishItemList) {
        this.context = context;
        this.dishItemList = dishItemList;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImageView;
        TextView dishNameTextView;
        TextView dishDescriptionTextView;
        TextView dishPriceTextView;
        TextView dishRemainingNumberTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            dishImageView = (ImageView) itemView.findViewById(R.id.image_view_dish);
            dishNameTextView = (TextView) itemView.findViewById(R.id.text_view_dish_name);
            dishDescriptionTextView = (TextView) itemView.findViewById(R.id.text_view_dish_description);
            dishPriceTextView = (TextView) itemView.findViewById(R.id.text_view_dish_price);
            dishRemainingNumberTextView = (TextView) itemView.findViewById(R.id.text_view_remaining_number);

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

        holder.dishNameTextView.setText(dish.getName());
        holder.dishDescriptionTextView.setText(dish.getDescription());
        holder.dishPriceTextView.setText(String.valueOf(dish.getPrice()));
        holder.dishRemainingNumberTextView.setText(String.valueOf(dish.getMax_num()));

    }

    public void setData(List<DishItem> list) {
        dishItemList = list;
        notifyDataSetChanged();

    }
}
