package edu.scu.qjiang.homekitchen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.files.BackendlessFile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.scu.qjiang.homekitchen.R;
import edu.scu.qjiang.homekitchen.entities.Kitchen;
import edu.scu.qjiang.homekitchen.utility.BackendSettings;

/**
 * Created by clover on 5/18/16.
 */
public class KitchenAdapter extends ArrayAdapter<Kitchen> {
    private LayoutInflater mInflater;
    private int mResource;

    /**
     * Constructor
     *
     * @param context     The current context.
     * @param resource    The resource ID for a layout file containing a TextView to use when
     *                    instantiating views.
     * @param kitchens The objects to represent in the ListView.
     */
    public KitchenAdapter(Context context, int resource, List<Kitchen> kitchens )
    {
        super( context, resource, kitchens );
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent )
    {
        View view = convertView == null ? mInflater.inflate( mResource, parent, false ) : convertView;

        TextView kitchenNameView = (TextView) view.findViewById( R.id.kitchenName );
        TextView categoryView = (TextView) view.findViewById( R.id.kitchenCategory );
        TextView dishesNumberView = (TextView) view.findViewById( R.id.dishNumber );
        ImageView kitchenPic = (ImageView) view.findViewById(R.id.imageView);

        final Kitchen item = getItem( position );
//        Log.d("what??", item.getName());

        kitchenNameView.setText( item.getKitchenName() );
        categoryView.setText( item.getCategory() );
        String dishesNumberTextTemplate = getContext().getResources().getQuantityString( R.plurals.dish_numbers, item.getDish().getDishItem().size() );
        dishesNumberView.setText( String.format( dishesNumberTextTemplate, item.getDish().getDishItem().size() ) );
//        Log.d("dish number:", String.valueOf(item.getMenu().getMenuItem().size()));

        DownloadImageTask downloadImageTask = new DownloadImageTask(kitchenPic);
        downloadImageTask.execute(item.getKitchenPic());

        return view;
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
