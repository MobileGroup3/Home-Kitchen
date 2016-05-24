package edu.scu.ytong.placingorder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.FollowedKitchen;
import edu.scu.ytong.placingorder.entities.Kitchen;
import edu.scu.ytong.placingorder.entities.OrderItem;
import edu.scu.ytong.placingorder.entities.SimpleCartItem;
import edu.scu.ytong.placingorder.utility.BackendSetting;

public class PlacingOrderActivity extends AppCompatActivity implements DishAddedListener {

    public static final String OBJECT_ID_EXTRA_KEY = "object_id_extra_key";
    ImageView kitchenThumbImageView;
    TextView kitchenNameTextView;
    Button addressButton;

    RecyclerView menuRecyclerView;
    LinearLayoutManager llm;
    MenuAdapter menuAdapter;

    List<DishItem> dishItemList;
    TextView totalAmountTextView;

    ArrayList<OrderItem> shoppingCartList;
    CartAdapter cartAdapter;
    ListView cartListView;


    ImageView cartIconImageView;
    Button checkOutButton;

    Button phoneNumberButton;
    String phoneNumber;

    Button followKitchenButton;

    ArrayList<SimpleCartItem> simpleCartItemsList;

    Kitchen kitchen;
    BackendlessUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_placing_order);

        Backendless.initApp(this, BackendSetting.APPLICATION_ID, BackendSetting.ANDROID_SECRET_KEY, BackendSetting.VERSION);
        Backendless.UserService.login("tongying@gmail.com", "123", new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                currentUser = response;

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });


        kitchenThumbImageView = (ImageView) findViewById(R.id.image_view_kitchen_thumb);
        kitchenNameTextView = (TextView) findViewById(R.id.text_view_kitchen_name);
        addressButton = (Button) findViewById(R.id.button_address);
        phoneNumberButton = (Button) findViewById(R.id.button_phone_number);
        totalAmountTextView = (TextView) findViewById(R.id.text_view_total_amount);


        menuRecyclerView = (RecyclerView) findViewById(R.id.list_view_menu);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        menuRecyclerView.setLayoutManager(llm);
        menuRecyclerView.addItemDecoration(new SpaceItemDecoration(this, R.dimen.menu_item_space, true, false));

        dishItemList = new ArrayList<DishItem>();
        menuAdapter = new MenuAdapter(this, dishItemList, this);
        menuRecyclerView.setAdapter(menuAdapter);

        shoppingCartList = new ArrayList<OrderItem>();
        simpleCartItemsList = new ArrayList<SimpleCartItem>();
        cartListView = (ListView) findViewById(R.id.list_view_shopping_cart);
        cartAdapter = new CartAdapter(this, R.layout.shopping_cart_item_view, shoppingCartList, this);
        cartListView.setAdapter(cartAdapter);


//        Intent intent = getIntent();
//        final String objectId = intent.getStringExtra(OBJECT_ID_EXTRA_KEY);


        new AsyncTask<Void, Void, Kitchen>() {
            @Override
            protected Kitchen doInBackground(Void... params) {
                kitchen = Backendless.Persistence.of(Kitchen.class).findById("A18BF3E1-D4EC-156C-FF21-C6CC65065600");

                //Kitchen kitchen  = Backendless.Persistence.of(Kitchen.class).findById(objectId);
                ArrayList<String> relationProps = new ArrayList<String>();
                relationProps.add("dish");
                relationProps.add("dish.dishItem");
                Backendless.Data.of(Kitchen.class).loadRelations(kitchen, relationProps);
                return kitchen;

            }

            @Override
            protected void onPostExecute(Kitchen kitchen) {

                String name = kitchen.getKitchenName();
                kitchenNameTextView.setText(name);

                String address = kitchen.getStreet() + ", " + kitchen.getCity() + ", " + kitchen.getZipcode();
                addressButton.setText(address);

                Picasso.with(PlacingOrderActivity.this).load(kitchen.getKitchenPic()).into(kitchenThumbImageView);

                phoneNumber = kitchen.getPhoneNumber();
                phoneNumberButton.setText(phoneNumber);

                // Modify the menu of null situation
                List<DishItem> list = kitchen.getDish().getDishItem();
                menuAdapter.setData(list);


            }
        }.execute();

        cartIconImageView = (ImageView) findViewById(R.id.image_view_cart_icon);
        cartIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartListView.getVisibility() == View.GONE) {

                    cartListView.setVisibility(View.VISIBLE);
                } else {
                    cartListView.setVisibility(View.GONE);
                }

            }
        });



        followKitchenButton = (Button) findViewById(R.id.image_view_follow_kitchen);
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                String clause = "customer = '" + currentUser + "'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                BackendlessCollection<FollowedKitchen> result = Backendless.Persistence.of(FollowedKitchen.class).find(dataQuery);

                if(result.getData().size() == 0) {
                    return false;
                }else {
                    ArrayList<String> relationProps = new ArrayList<String>();
                    relationProps.add("followed_kitchen_list");
                    FollowedKitchen followedKitchen = result.getData().get(0);
                    Backendless.Data.of(FollowedKitchen.class).loadRelations(followedKitchen, relationProps);
                    List<Kitchen> followed_list = followedKitchen.getFollowed_kitchen_list();

                    if(containItem(followed_list,kitchen)) {
                        Log.e("Hit","hellllll");
                        return true;
                    }else {
                        return false;
                    }


                }


            }


            @Override
            protected void onPostExecute(Boolean flag) {
                if(flag) {
                    followKitchenButton.setText("Unfollow");
                }else {
                    followKitchenButton.setText("Follow");
                }



            }
        }.execute();



        // Set follow and unfollow listener
        followKitchenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (followKitchenButton.getText().toString() == "Unfollow") {
                    followKitchenButton.setText("Follow");
                    deleteFollowedKitchen();
                    //followKitchenButton.setBackgroundColor(getResources().getColor(R.color.blue));

                } else {
                    followKitchenButton.setText("Unfollow");

                    addFollowedKitchen();
                    //followKitchenButton.setBackgroundColor(getResources().getColor(R.color.white));
                }

            }
        });

        checkOutButton = (Button) findViewById(R.id.button_checkout);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = totalAmountTextView.getText().toString();
                double totalAmount = Double.parseDouble(totalAmountTextView.getText().toString());
                if (totalAmount == 0.0) {
                    Toast.makeText(
                            PlacingOrderActivity.this, "Please Add Dish Into Cart First", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(PlacingOrderActivity.this, OrderConformationActivity.class);

                    intent.putParcelableArrayListExtra("cart_list_extra_key", simpleCartItemsList);
                    intent.putExtra("total_amount_extra_key", totalAmount);
                    intent.putExtra("address_extra_key", addressButton.getText());
                    intent.putExtra("phone_number_extra_key", phoneNumber);

                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onDishReduced(OrderItem orderItem) {
        DishItem dish = orderItem.getDishItem();
        double price = dish.getPrice();
        double totalAmountPre = Double.parseDouble(totalAmountTextView.getText().toString());
        double totalAmountCur = totalAmountPre - price;
        totalAmountTextView.setText(String.valueOf(totalAmountCur));
        reduceInShoppingCart(orderItem);

    }

    public void reduceInShoppingCart(OrderItem orderItem) {
        int orderQuantity = orderItem.getOrderQuantity();
        DishItem dish = orderItem.getDishItem();
        int position = getCartPosition(dish);

        if (orderQuantity == 1) {
            shoppingCartList.remove(position);
            simpleCartItemsList.remove(position);

        } else {
            orderItem.setOrderQuantity(orderItem.getOrderQuantity() - 1);

            SimpleCartItem simpleCartItem = simpleCartItemsList.get(position);
            simpleCartItem.setOrderQuantity(simpleCartItem.getOrderQuantity() - 1);

        }
        dish.setMax_num(dish.getMax_num() + 1);
        updateAdapters();
    }

    @Override
    public void onDishAdded(DishItem dish) {
        double price = dish.getPrice();
        double totalAmountPre = Double.parseDouble(totalAmountTextView.getText().toString());
        double totalAmountCur = totalAmountPre + price;
        totalAmountTextView.setText(String.valueOf(totalAmountCur));
        addInShoppingCart(dish);

    }

    public void addInShoppingCart(DishItem dish) {
        if (getCartPosition(dish) < 0) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderQuantity(1);
            orderItem.setDishItem(dish);
            shoppingCartList.add(orderItem);

            SimpleCartItem simpleCartItem = new SimpleCartItem(dish.getObjectId(), 1);
            simpleCartItemsList.add(simpleCartItem);

        } else {
            int position = getCartPosition(dish);
            OrderItem orderItem = shoppingCartList.get(position);
            orderItem.setOrderQuantity(orderItem.getOrderQuantity() + 1);

            SimpleCartItem simpleCartItem = simpleCartItemsList.get(position);
            simpleCartItem.setOrderQuantity(simpleCartItem.getOrderQuantity() + 1);

        }

        updateAdapters();


    }
    // Find the position of the dishItem in the cartList, if not exist, return -1;
    public int getCartPosition(DishItem dish) {
        if (shoppingCartList == null) {
            return -1;
        } else {
            int length = shoppingCartList.size();
            for (int i = 0; i < length; i++) {
                DishItem item = shoppingCartList.get(i).getDishItem();
                if (item.equals(dish)) {
                    return i;
                }

            }
            return -1;
        }
    }


    public void updateAdapters() {
        cartAdapter.notifyDataSetChanged();
        menuAdapter.notifyDataSetChanged();

    }

    // Add Followed Kitchen and update database
    public void addFollowedKitchen() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                String clause = "customer = '" + currentUser + "'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                BackendlessCollection<FollowedKitchen> result = Backendless.Persistence.of(FollowedKitchen.class).find(dataQuery);

                if(result.getData().size() == 0){
                    FollowedKitchen followedKitchen = new FollowedKitchen();
                    List<Kitchen> followedList = new ArrayList<Kitchen>();
                    followedList.add(kitchen);
                    followedKitchen.setFollowed_kitchen_list(followedList);
                    followedKitchen.save();

                }else {
                    ArrayList<String> relationProps = new ArrayList<String>();
                    relationProps.add("followed_kitchen_list");
                    FollowedKitchen followedKitchen = result.getData().get(0);
                    Backendless.Data.of(FollowedKitchen.class).loadRelations(followedKitchen, relationProps);

                    List<Kitchen> followed_list = followedKitchen.getFollowed_kitchen_list();
//                    Log.e("add_pre", String.valueOf(followed_list.size()));
                    followed_list.add(kitchen);
                    followedKitchen.setFollowed_kitchen_list(followed_list);

                    followedKitchen.save();
//                    Log.e("add_after", String.valueOf(a));

                }



                //Log.e("total",String.valueOf(followedKitchen.getFollowed_kitchen_list().get(0).getKitchenName()));
                return null;
            }
        }.execute();

        Toast.makeText(this, "Successfully Followed Us", Toast.LENGTH_SHORT).show();
    }

    // Delete Followed Kitchen and update database
    public void deleteFollowedKitchen() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                String clause = "customer = '" + currentUser + "'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                BackendlessCollection<FollowedKitchen> result = Backendless.Persistence.of(FollowedKitchen.class).find(dataQuery);


                ArrayList<String> relationProps = new ArrayList<String>();
                relationProps.add("followed_kitchen_list");
                FollowedKitchen followedKitchen = result.getData().get(0);
                Backendless.Data.of(FollowedKitchen.class).loadRelations(followedKitchen, relationProps);
                List<Kitchen> followed_list = followedKitchen.getFollowed_kitchen_list();

                if(followed_list.size() == 1) {
                    followedKitchen.remove();

                }else {
//                    Log.e("delete_pre", String.valueOf(followed_list.size()));
                    List<Kitchen> updatedList = removeItem(followed_list,kitchen);
//                    Log.e("delete_after", String.valueOf(updatedList.size()));
                    followedKitchen.setFollowed_kitchen_list(updatedList);
                    followedKitchen.save();

                }

                return null;
            }

        }.execute();

        Toast.makeText(this, "Successfully Unfollowed Us", Toast.LENGTH_SHORT).show();
    }
    // Delete kitchen in the list
    public List<Kitchen> removeItem(List<Kitchen> list, Kitchen kitchen) {
       List<Kitchen> updateList = new ArrayList<Kitchen>();
        int length = list.size();
        for(int i = 0; i < length; i++) {

            Kitchen kitchenInDb = list.get(i);
            String objectId = kitchenInDb.getObjectId();
            if(!objectId.equals(kitchen.getObjectId())) {
                updateList.add(kitchenInDb);
            }

        }
        return updateList;


    }

    public boolean containItem(List<Kitchen> list, Kitchen kitchen) {
        for(int i = 0; i < list.size(); i++) {
            String objectId = list.get(i).getObjectId();
            if (objectId.equals(kitchen.getObjectId())) {
                return  true;
            }
        }
        return false;
    }


}