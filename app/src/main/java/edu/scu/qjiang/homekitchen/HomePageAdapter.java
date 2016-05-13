package edu.scu.qjiang.homekitchen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by clover on 5/5/16.
 */
public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder> {
    private List<Dish> DishList;

    public static class HomePageViewHolder extends RecyclerView.ViewHolder {

        protected TextView dishName;
        protected ImageView dishPic;
        protected TextView kitName;
        protected ImageView kitthumbnail;

        public HomePageViewHolder (View v) {
            super(v);
            dishName = (TextView) v.findViewById(R.id.)
        }
    }

}
