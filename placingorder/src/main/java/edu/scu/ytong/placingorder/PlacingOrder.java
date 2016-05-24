package edu.scu.ytong.placingorder;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.ArrayList;
import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.Kitchen;
import edu.scu.ytong.placingorder.entities.DishItem;

public class PlacingOrder extends AppCompatActivity{
    public static final String OBJECT_ID_EXTRA_KEY = "object_id_extra_key";
    ImageView kitchenThumbImageView;
    TextView kitchenNameTextView;
    Button addressButton;
    RecyclerView menuRecyclerView;
    LinearLayoutManager llm;
    MenuAdapter menuAdapter;
    List<DishItem> dishItemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placing_order);

        final String APPLICATION_ID = "6B06D541-69FC-AA24-FF52-EB6421144100";
        final String ANDROID_SECRET_KEY = "F2C00252-B60B-8048-FF2B-F2893504BD00";
        final String VERSION = "v1";


        Backendless.initApp( this, APPLICATION_ID, ANDROID_SECRET_KEY, VERSION);

        kitchenThumbImageView = (ImageView) findViewById(R.id.image_view_kitchen_thumb);
        kitchenNameTextView = (TextView) findViewById(R.id.text_view_kitchen_name);
        addressButton = (Button) findViewById(R.id.button_address);
        menuRecyclerView = (RecyclerView) findViewById(R.id.list_view_menu);


        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        menuRecyclerView.setLayoutManager(llm);

        dishItemList= new ArrayList<DishItem>();
        menuAdapter = new MenuAdapter(getApplicationContext(),dishItemList);
        menuRecyclerView.setAdapter(menuAdapter);


        Intent intent = getIntent();
        final String objectId = intent.getStringExtra(OBJECT_ID_EXTRA_KEY);



        new AsyncTask<Void,Void,Kitchen>() {
            @Override
            protected Kitchen doInBackground(Void... params) {
                Log.d("Object ID is: ", objectId);
                Kitchen kitchen  = Backendless.Persistence.of(Kitchen.class).findById(objectId);
                ArrayList<String> relationProps = new ArrayList<String>();
                relationProps.add("dish");
                relationProps.add("dish.dishItem");
                Backendless.Data.of( Kitchen.class ).loadRelations(kitchen, relationProps);
                return kitchen;
            }

            @Override
            protected void onPostExecute(Kitchen kitchen) {

                String name = kitchen.getKitchenName();
                kitchenNameTextView.setText(name);

                final String address = kitchen.getStreet() + " " + kitchen.getCity();
                addressButton.setText(address);
                addressButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

                if (kitchen.getDish() != null) {
                    List<DishItem> list = kitchen.getDish().getDishItem();
                    menuAdapter.setData(list);
                }



            }
        }.execute();

    }
}
