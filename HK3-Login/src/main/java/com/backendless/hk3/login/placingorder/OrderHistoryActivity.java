package com.backendless.hk3.login.placingorder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.hk3.login.R;
import com.backendless.hk3.login.entities.Order;
import com.backendless.hk3.login.utility.BackendSettings;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Order> orderList;
    OrdersHistoryAdapter mAdapter;
    BackendlessUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);
        currentUser = Backendless.UserService.CurrentUser();
//        Backendless.UserService.login("tongying@gmail.com", "123", new AsyncCallback<BackendlessUser>() {
//            @Override
//            public void handleResponse(BackendlessUser response) {
//                currentUser = response;
//
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//
//            }
//        });

        orderList = new ArrayList<Order>();
        mRecyclerView = (RecyclerView) findViewById(R.id.list_order_history);
        mAdapter = new OrdersHistoryAdapter(this,orderList);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        new AsyncTask<Void,Void,List<Order>>() {
            @Override
            protected List<Order> doInBackground(Void... params) {
                String currentUserId = currentUser.getObjectId();
                String whereClause = "customer.objectId = '" + currentUserId + "'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                dataQuery.setWhereClause(whereClause);
                BackendlessCollection<Order> result = Backendless.Persistence.of(Order.class).find(dataQuery);
                orderList = result.getData();
                return orderList;

            }

            @Override
            protected void onPostExecute(List<Order> orderList) {
                mAdapter.setData(orderList);
            }
        }.execute();

    }
}
