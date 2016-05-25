package edu.scu.qjiang.homekitchen;

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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.List;

import edu.scu.qjiang.homekitchen.adapter.KitchenAdapter;
import edu.scu.qjiang.homekitchen.adapter.KitchenListAdapter;
import edu.scu.qjiang.homekitchen.entities.FollowedKitchen;
import edu.scu.qjiang.homekitchen.entities.Kitchen;
import edu.scu.qjiang.homekitchen.utility.BackendSettings;

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
//        Backendless.UserService.login("tongying@gmail.com", "123", new AsyncCallback<BackendlessUser>() {
//            @Override
//            public void handleResponse(BackendlessUser response) {
//                currentUser = response;
//                Log.d("very beginning: ", currentUser.getEmail());
//
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Log.d("Fail", String.valueOf(fault));
//
//            }
//        });
        currentUser = Backendless.UserService.CurrentUser();
        //Log.d("second is:", currentUser.getEmail());

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
                BackendlessDataQuery query = new BackendlessDataQuery();
                String whereClause = "customer = '" + currentUser + "'";
                query.setWhereClause(whereClause);
                Log.d("current user is: ", currentUser.getEmail());
                Log.d("query is:", query.toString());

                BackendlessCollection<FollowedKitchen> result = Backendless.Persistence.of(FollowedKitchen.class).find(query);
                if (result.getTotalObjects() > 0) {
                    followedKitchen = result.getCurrentPage().get(0);
                    ArrayList<String> relationProps = new ArrayList<String>();
                    relationProps.add("followed_kitchen_list");
                    relationProps.add("followed_kitchen_list.dish");
                    Backendless.Data.of(FollowedKitchen.class).loadRelations(followedKitchen, relationProps);
                    return followedKitchen;
                } else {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(FollowedKitchen followedKitchen) {
                totalKitchens = followedKitchen.getFollowed_kitchen_list();
                kitchenListAdapter.setData(totalKitchens);
            }
        }.execute();
    }
}
