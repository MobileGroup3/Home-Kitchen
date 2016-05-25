package com.backendless.hk3.login.kitchen_list;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.hk3.login.R;
import com.backendless.persistence.BackendlessDataQuery;

import com.backendless.hk3.login.entities.Kitchen;
import com.backendless.hk3.login.entities.FollowedKitchen;
import com.backendless.hk3.login.utility.*;
import com.backendless.hk3.login.kitchen_list.adapter.KitchenListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FollowedKitchenActivity extends AppCompatActivity {
    private List<Kitchen> totalKitchens = new ArrayList<>();
    BackendlessUser currentUser;
    private FollowedKitchen followedKitchen;

    private RecyclerView followedRecyclerView;
    private LinearLayoutManager llm;
    private KitchenListAdapter kitchenListAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed_kitchen);

        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);
        currentUser = Backendless.UserService.CurrentUser();

        mContext = getApplicationContext();

        followedRecyclerView = (RecyclerView) findViewById(R.id.followed_kitchen_list);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        followedRecyclerView.setLayoutManager(llm);

        kitchenListAdapter = new KitchenListAdapter(this, totalKitchens);
        followedRecyclerView.setAdapter(kitchenListAdapter);

        new AsyncTask<Void, Void, FollowedKitchen>() {
            @Override
            protected FollowedKitchen doInBackground(Void... params) {
                String currentUserId = currentUser.getObjectId();
                String whereClause = "customer.objectId = '" + currentUserId + "'";
                BackendlessDataQuery query = new BackendlessDataQuery();
                query.setWhereClause(whereClause);

                BackendlessCollection<FollowedKitchen> result = Backendless.Persistence.of(FollowedKitchen.class).find(query);
                if (result.getTotalObjects() > 0) {
                    followedKitchen = result.getCurrentPage().get(0);
                    ArrayList<String> relationProps = new ArrayList<String>();
                    relationProps.add("followed_kitchen_list");
//                    relationProps.add("followed_kitchen_list.dish");
                    Backendless.Data.of(FollowedKitchen.class).loadRelations(followedKitchen, relationProps);
                    return followedKitchen;
                } else {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(FollowedKitchen followedKitchen) {
                if (followedKitchen != null) {
                    totalKitchens = followedKitchen.getFollowed_kitchen_list();
                    kitchenListAdapter.setData(totalKitchens);
                    Log.d("kitchen result: ", totalKitchens.get(0).getKitchenName());
                }
            }
        }.execute();
    }
}
