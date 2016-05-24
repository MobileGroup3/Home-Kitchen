//package edu.scu.ytong.placingorder;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.MenuItem;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.backendless.Backendless;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.scu.ytong.placingorder.entities.Kitchen;
//import edu.scu.ytong.placingorder.entities.DishItem;
//
//public class PlacingOrder extends AppCompatActivity{
//    public static final String OBJECT_ID_EXTRA_KEY = "object_id_extra_key";
//    ImageView kitchenThumbImageView;
//    TextView kitchenNameTextView;
//    Button addressButton;
//    RecyclerView menuRecyclerView;
//    LinearLayoutManager llm;
//    MenuAdapter menuAdapter;
//    List<DishItem> dishItemList;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_placing_order);
//
//        final String APPLICATION_ID = "99895A50-3FE5-6905-FF3E-9C004D4E1200";
//        final String ANDROID_SECRET_KEY = "F7E0ACE0-0237-1D41-FF65-9443E0874900";
//        final String VERSION = "v1";
//
//
//        Backendless.initApp( this, APPLICATION_ID, ANDROID_SECRET_KEY, VERSION);
//
//
//        kitchenThumbImageView = (ImageView) findViewById(R.id.image_view_kitchen_thumb);
//        kitchenNameTextView = (TextView) findViewById(R.id.text_view_kitchen_name);
//        addressButton = (Button) findViewById(R.id.button_address);
//        menuRecyclerView = (RecyclerView) findViewById(R.id.list_view_menu);
//
//
//        llm = new LinearLayoutManager(getApplicationContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        menuRecyclerView.setLayoutManager(llm);
//
//        dishItemList= new ArrayList<DishItem>();
//        menuAdapter = new MenuAdapter(this, dishItemList, this);
//        menuRecyclerView.setAdapter(menuAdapter);
//
//
////        Intent intent = getIntent();
////        String objectId = intent.getStringExtra(OBJECT_ID_EXTRA_KEY);
//
//
//
//        new AsyncTask<Void,Void,Kitchen>() {
//            @Override
//            protected Kitchen doInBackground(Void... params) {
//                Kitchen kitchen  = Backendless.Persistence.of(Kitchen.class).findFirst();
//                ArrayList<String> relationProps = new ArrayList<String>();
//                relationProps.add("menu");
//                relationProps.add( "menu.menuItem");
//                Backendless.Data.of( Kitchen.class ).loadRelations(kitchen, relationProps);
//                return kitchen;
//
//            }
//
//            @Override
//            protected void onPostExecute(Kitchen kitchen) {
//
//                String name = kitchen.getKitchenName();
//                kitchenNameTextView.setText(name);
//
//                String address = kitchen.getStreet() + kitchen.getCity() + kitchen.getZipcode();
//                addressButton.setText(address);
//
//                List<DishItem> list = kitchen.getDish().getDishItem();
//                menuAdapter.setData(list);
//
//
//
//            }
//        }.execute();
//
//    }
//}
