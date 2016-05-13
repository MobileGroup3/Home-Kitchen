package edu.scu.qjiang.homekitchen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by clover on 5/5/16.
 */
public class HPAdapter extends RecyclerView.Adapter<HPAdapter.ViewHolder> {
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById();
        }
    }

    public HPAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public HPAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hp_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public  void onBindViewHolder(ViewHolder holder, int position) {
        //get element from dataset at this position, then replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);
    }

    //Returns the size of your dataset (invoked by the layout manager)
    public int getItemCount() {
        return mDataset.length;
    }
}
