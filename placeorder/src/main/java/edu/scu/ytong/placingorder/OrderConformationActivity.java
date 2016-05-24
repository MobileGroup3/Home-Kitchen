package edu.scu.ytong.placingorder;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.Order;
import edu.scu.ytong.placingorder.entities.OrderItem;
import edu.scu.ytong.placingorder.entities.SimpleCartItem;
import edu.scu.ytong.placingorder.utility.BackendSetting;

public class OrderConformationActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener{
    public static final String CART_LIST_EXTRA_KEY = "cart_list_extra_key";
    public static final String TOTAL_AMOUNT_EXTRA_KEY = "total_amount_extra_key";
    public static final String ADDRESS_EXTRA_KEY = "address_extra_key";
    public static final String PHONE_EXTRA_KEY = "phone_number_extra_key";

    ArrayList<SimpleCartItem> simpleCartItemList;
    List<OrderItem> orderList;
    TextView phoneTextView;
    EditText noteEditText;
    ListView orderListView;
    OrderAdapter orderAdapter;
    TextView totalAmountTextView;
    TextView addressTextView;
    String address;
    String phoneNumber;
    Button orderConformButton;
    Button timePickerButton;

    Context context;
    BackendlessUser customer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_conformation);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Backendless.initApp(this, BackendSetting.APPLICATION_ID, BackendSetting.ANDROID_SECRET_KEY, BackendSetting.VERSION);

        Backendless.UserService.login("tongying@gmail.com", "123", new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                customer = response;

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });


        context = this;
        timePickerButton = (Button) findViewById(R.id.button_time_picker);
        Intent intent = getIntent();

        double totalAmount = intent.getDoubleExtra(TOTAL_AMOUNT_EXTRA_KEY,0);
        totalAmountTextView = (TextView) findViewById(R.id.text_view_order_amount);
        totalAmountTextView.setText("$" + String.valueOf(totalAmount));

        address = intent.getStringExtra(ADDRESS_EXTRA_KEY);
        addressTextView = (TextView) findViewById(R.id.text_view_order_address);
        addressTextView.setText(address);

        phoneNumber = intent.getStringExtra(PHONE_EXTRA_KEY );
        phoneTextView = (TextView) findViewById(R.id.text_view_order_phone);
        phoneTextView.setText(phoneNumber);

        noteEditText = (EditText) findViewById(R.id.edit_text_note);


        simpleCartItemList = intent.getParcelableArrayListExtra(CART_LIST_EXTRA_KEY);
        final int cartItemNumber = simpleCartItemList.size();


        orderListView = (ListView) findViewById(R.id.list_view_order_dish);
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this,R.layout.shopping_cart_item_view, orderList);
        orderListView.setAdapter(orderAdapter);

        new AsyncTask<Void,Void,List<OrderItem>>(){
            @Override
            protected List<OrderItem> doInBackground(Void... params) {
                for(int i = 0; i < cartItemNumber; i++) {

                    String menuObjectId = simpleCartItemList.get(i).getDishObjectId();
                    DishItem dishItem  = Backendless.Persistence.of(DishItem.class).findById(menuObjectId);

                    int orderQuantity = simpleCartItemList.get(i).getOrderQuantity();

                    OrderItem orderItem = new OrderItem();
                    orderItem.setDishItem(dishItem);
                    orderItem.setOrderQuantity(orderQuantity);
                    orderList.add(orderItem);

                }
                return orderList;

            }

            @Override
            protected void onPostExecute(List<OrderItem> orderList) {

                //Log.e("orderList",String.valueOf(orderList.size()));
                orderAdapter.setData(orderList);
            }

        }.execute();


        orderConformButton = (Button) findViewById(R.id.button_order_conform);
        orderConformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conformOrder();

            }
        });


    }

    public void conformOrder() {
        new AlertDialog.Builder(this)
                .setTitle("Conform Your Order")
                .setMessage("Do you want to conform your order?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveOrderToDatabase();
                        //Test , move toast to saveOrderToDataBase later
                        Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"Order Canceled",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


    }

    public void saveOrderToDatabase() {

        final Order order = new Order();
        // May be Needs to modify
        //BackendlessUser customer = Backendless.UserService.findById();
        order.setCustomer(customer);

        String pickTime = timePickerButton.getText().toString();
        order.setPickTime(pickTime);

//        String note;
//        if(noteEditText.getText() == null) {
//            note = null;
//        }else {
//            note = noteEditText.getText().toString();
//        }

        String note = noteEditText.getText().toString();
        order.setNote(note);

        List<OrderItem> updateOrderList = new ArrayList<OrderItem>();
        for(int i = 0; i < orderList.size(); i++) {
            OrderItem orderItem = orderList.get(i);
            DishItem dishItem = orderItem.getDishItem();
            int ordered = orderItem.getOrderQuantity();
            int max_num = dishItem.getMax_num();
            max_num = max_num - ordered;
            dishItem.setMax_num(max_num);
            dishItem.getUpdated();
            orderItem.setDishItem(dishItem);

        }

        order.setOrderItem(orderList);

        order.saveAsync(new AsyncCallback<Order>() {
            @Override
            public void handleResponse(Order response) {
                Log.e("Test", "Succeed: " + response.toString());

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Test", "Failed: " + fault.toString());

            }
        });

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getSupportFragmentManager(),"timePicker");

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourDisplay = String.valueOf(hourOfDay);
        String minuteDisplay = String.valueOf(minute);
        if(minute == 0) {
            minuteDisplay = "00";
        }
        if(hourOfDay == 0) {
            hourDisplay = "00";
        }
        String time = hourDisplay + " : " + minuteDisplay;
        timePickerButton.setText(time);


    }
}
