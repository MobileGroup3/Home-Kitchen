package edu.scu.qjiang.homekitchen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import edu.scu.qjiang.homekitchen.R;
import edu.scu.qjiang.homekitchen.entities.Kitchen;

/**
 * Created by clover on 5/20/16.
 */
public class KitchenListAdapter extends RecyclerView.Adapter<KitchenListAdapter.KitchenViewHolder> {
    private List<Kitchen> kitchens;

    public KitchenListAdapter(List<Kitchen> kitchens)
    {
        this.kitchens = kitchens;
    }

    public static class KitchenViewHolder extends RecyclerView.ViewHolder {
        protected TextView kitchenNameView;
        protected TextView categoryView;
        protected TextView dishesNumberView;
        protected ImageView kitchenPic;

        public KitchenViewHolder(View view) {
            super (view);
            kitchenNameView = (TextView) view.findViewById( R.id.kitchenName );
            categoryView = (TextView) view.findViewById( R.id.kitchenCategory );
            dishesNumberView = (TextView) view.findViewById( R.id.dishNumber );
            kitchenPic = (ImageView) view.findViewById(R.id.imageView);
        }
     }

    @Override
    public int getItemCount() {
        return kitchens.size();
    }

    @Override
    public void onBindViewHolder(KitchenViewHolder kitchenViewHolder, int i) {
        Kitchen item = kitchens.get(i);
        kitchenViewHolder.kitchenNameView.setText(item.getName());
        kitchenViewHolder.categoryView.setText(item.getCategory());
        kitchenViewHolder.dishesNumberView.setText(String.valueOf(item.getMenu().getMenuItem().size()));
//        kitchenViewHolder.dishesNumberView.setText("2");
        DownloadImageTask downloadImageTask = new DownloadImageTask(kitchenViewHolder.kitchenPic);
        downloadImageTask.execute(item.getKitchenPic());
    }

    @Override
    public KitchenViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row, viewGroup, false);
        return new KitchenViewHolder(itemView);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bm = null;
            try {
                InputStream is = new java.net.URL(urldisplay).openStream();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;
//                bm = BitmapFactory.decodeStream(is, null, options);
                bm = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
