package edu.scu.qjiang.homekitchen;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.Persistence;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.scu.qjiang.homekitchen.adapter.KitchenAdapter;
import edu.scu.qjiang.homekitchen.entities.Kitchen;
import edu.scu.qjiang.homekitchen.utility.BackendSettings;
import edu.scu.qjiang.homekitchen.utility.LoadingCallback;

public class KitchenList extends ListActivity {

    private BackendlessCollection<Kitchen> kitchen;
    private List<Kitchen> totalKitchens = new ArrayList<>();
    private boolean isLoadingItems = false;
    private KitchenAdapter adapter;
//    private LoadDataTask loadDataTask;
//    private TextView hp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_list);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        adapter = new KitchenAdapter(KitchenList.this, R.layout.hp_item, totalKitchens);
        setListAdapter(adapter);

        QueryOptions queryOptions = new QueryOptions();
        //queryOptions.setRelated(Arrays.asList("locations"));
        queryOptions.setRelated(Arrays.asList("menu"));

        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);

        Backendless.Data.of(Kitchen.class).find(query, new LoadingCallback<BackendlessCollection<Kitchen>>(this, getString(R.string.loading_kitchens), true) {
            @Override
            public void handleResponse(BackendlessCollection<Kitchen> kitchensBackendlessCollection) {
                kitchen = kitchensBackendlessCollection;

                addMoreItems(kitchensBackendlessCollection);

                super.handleResponse(kitchensBackendlessCollection);
            }
        });

/**        For test of AsyncTask */
//        hp = (TextView) findViewById(R.id.homepage);
//        hp.setText("");
//        loadDataTask = new LoadDataTask();
//        loadDataTask.execute("Start running");

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (needToLoadItems(firstVisibleItem, visibleItemCount, totalItemCount)) {
                    isLoadingItems = true;

                    kitchen.nextPage(new LoadingCallback<BackendlessCollection<Kitchen>>(KitchenList.this) {
                        @Override
                        public void handleResponse(BackendlessCollection<Kitchen> nextPage) {
                            kitchen = nextPage;

                            addMoreItems(nextPage);

                            isLoadingItems = false;
                        }
                    });
                }
            }
        });
    }

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