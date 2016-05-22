package edu.scu.qjiang.homekitchen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.scu.qjiang.homekitchen.adapter.KitchenListAdapter;
import edu.scu.qjiang.homekitchen.entities.Kitchen;
import edu.scu.qjiang.homekitchen.utility.BackendSettings;
import edu.scu.qjiang.homekitchen.utility.LoadingCallback;

public class MainActivity extends AppCompatActivity {

    private BackendlessCollection<Kitchen> kitchen;
    private List<Kitchen> totalKitchens = new ArrayList<>();
    private boolean isLoadingItems = false;
    private KitchenListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        RecyclerView recList = (RecyclerView) findViewById(R.id.kitchenList);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        adapter = new KitchenListAdapter(this, totalKitchens);
        recList.setAdapter(adapter);

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

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

/**        For test of AsyncTask */
//        hp = (TextView) findViewById(R.id.homepage);
//        hp.setText("");
//        loadDataTask = new LoadDataTask();
//        loadDataTask.execute("Start running");

//        ListView list = (ListView) findViewById(android.R.id.list);
//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (needToLoadItems(firstVisibleItem, visibleItemCount, totalItemCount)) {
//                    isLoadingItems = true;
//
//                    kitchen.nextPage(new LoadingCallback<BackendlessCollection<Kitchen>>(MainActivity.this) {
//                        @Override
//                        public void handleResponse(BackendlessCollection<Kitchen> nextPage) {
//                            kitchen = nextPage;
//
//                            addMoreItems(nextPage);
//
//                            isLoadingItems = false;
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Intent searchIntent = new Intent(MainActivity.this, SearchableActivity.class);
            startActivity(searchIntent);
//            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
        return super.onOptionsItemSelected(item);
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
