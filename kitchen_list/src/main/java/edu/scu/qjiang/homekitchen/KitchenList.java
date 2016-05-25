package edu.scu.qjiang.homekitchen;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.Persistence;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.scu.qjiang.homekitchen.adapter.KitchenAdapter;
import edu.scu.qjiang.homekitchen.adapter.KitchenListAdapter;
import edu.scu.qjiang.homekitchen.entities.FollowedKitchen;
import edu.scu.qjiang.homekitchen.entities.Kitchen;
import edu.scu.qjiang.homekitchen.utility.BackendSettings;
import edu.scu.qjiang.homekitchen.utility.LoadingCallback;
import edu.scu.ytong.placingorder.MenuAdapter;
import edu.scu.ytong.placingorder.PlacingOrderActivity;
import edu.scu.ytong.placingorder.entities.DishItem;

public class KitchenList extends ListActivity {

//    private BackendlessCollection<Kitchen> kitchen;
    private List<Kitchen> totalKitchens = new ArrayList<>();
    private boolean isLoadingItems = false;
    private KitchenAdapter adapter;
    private BackendlessUser currentUser;
    private FollowedKitchen followedKitchen;

    private RecyclerView followedRecyclerView;
    private LinearLayoutManager llm;
    private KitchenListAdapter kitchenListAdapter;

//    private LoadDataTask loadDataTask;
//    private TextView hp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_list);

        followedRecyclerView = (RecyclerView) findViewById(R.id.followed_kitchen_list);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        followedRecyclerView.setLayoutManager(llm);

        kitchenListAdapter = new KitchenListAdapter(this, totalKitchens);
        followedRecyclerView.setAdapter(kitchenListAdapter);


        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);
        Backendless.UserService.login("tongying@gmail.com", "123", new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                currentUser = response;

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        currentUser = Backendless.UserService.CurrentUser();

        new AsyncTask<Void, Void, List<Kitchen>>() {
            @Override
            protected List<Kitchen> doInBackground(Void... params) {
                BackendlessDataQuery query = new BackendlessDataQuery();
                String whereClause = "customer = '" + currentUser + "'";
                query.setWhereClause(whereClause);

                BackendlessCollection<FollowedKitchen> result = Backendless.Persistence.of(FollowedKitchen.class).find(query);
                if (result.getTotalObjects() > 0) {
                    followedKitchen = result.getCurrentPage().get(0);
                    ArrayList<String> relationProps = new ArrayList<String>();
                    relationProps.add("followed_kitchen_list");
                    relationProps.add("followed_kitchen_list.dish");
                    Backendless.Data.of(FollowedKitchen.class).loadRelations(followedKitchen, relationProps);
                    return followedKitchen.getFollowed_kitchen_list();
                } else {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Kitchen> totalKitchens) {

            }
        }.execute();
    }

//        Backendless.Data.of(FollowedKitchen.class).find(query, new LoadingCallback<BackendlessCollection<FollowedKitchen>>(this, getString(R.string.loading_kitchens), true) {
//            @Override
//            public void handleResponse(BackendlessCollection<FollowedKitchen> kitchensBackendlessCollection) {
//                totalKitchens = kitchensBackendlessCollection.getCurrentPage().get(0).getFollowed_kitchen_list();
//
//                addMoreItems(kitchensBackendlessCollection);
//
//                super.handleResponse(kitchensBackendlessCollection);
//            }
//
//            @Override
//            public void handleFault (BackendlessFault fault) {
//                Log.d("OMG", "fail");
//            }
//        });

/**        For test of AsyncTask */
//        hp = (TextView) findViewById(R.id.homepage);
//        hp.setText("");
//        loadDataTask = new LoadDataTask();
//        loadDataTask.execute("Start running");


    /**For test of AsyncTask */
//    private class LoadDataTask extends AsyncTask<String, Integer, String> {
//        private Kitchen kitchen2;
//        @Override
//        protected String doInBackground(String... params) {
//            kitchen2 = Backendless.Persistence.of(Kitchen.class).findFirst();
//            return kitchen2.getName();
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            hp.append(result);
//        }
//    }

    /**
     * Determines whether is it needed to load more items as user scrolls down.
     *
     * @param firstVisibleItem number of the first item visible on screen
     * @param visibleItemCount number of items visible on screen
     * @param totalItemCount   total number of items in list
     * @return true if user is about to reach the end of a list, else false
     */
    private boolean needToLoadItems( int firstVisibleItem, int visibleItemCount, int totalItemCount )
    {
        return !isLoadingItems && totalItemCount != 0 && totalItemCount - (visibleItemCount + firstVisibleItem) < visibleItemCount / 2;
    }

    /**
     * Adds more items to list and notifies Android that dataset has changed.
     *
     * @param nextPage list of new items
     */
    private void addMoreItems( BackendlessCollection<Kitchen> nextPage )
    {
        totalKitchens.addAll( nextPage.getCurrentPage() );
        adapter.notifyDataSetChanged();
    }
}
