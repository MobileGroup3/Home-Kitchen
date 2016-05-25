package com.backendless.hk3.login.kitchen_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.hk3.login.R;
import com.backendless.hk3.login.entities.Kitchen;
import com.backendless.hk3.login.kitchen_list.adapter.KitchenListAdapter;
import com.backendless.hk3.login.placingorder.OrderHistoryActivity;
import com.backendless.hk3.login.utility.BackendSettings;
import com.backendless.hk3.login.utility.LoadingCallback;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//import edu.scu.ytong.homekitchen.SettingsActivity;


public class KitchenHomepageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BackendlessCollection<Kitchen> kitchen;
    private List<Kitchen> totalKitchens = new ArrayList<>();
    private boolean isLoadingItems = false;
    private KitchenListAdapter adapter;
    private GridLayoutManager glm;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_homepage);

        /** Navigation Drawer  */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("Where", drawer.toString());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /** Set adapter */
        RecyclerView recList = (RecyclerView) findViewById(R.id.kitchenList);
//        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recList.setLayoutManager(llm);

//        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, 1);
//        recList.setLayoutManager(sglm);
        glm = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0 ? 2 : 1);
            }
        });
        recList.setLayoutManager(glm);


        adapter = new KitchenListAdapter(this, totalKitchens);
        recList.setAdapter(adapter);

        /** Connect to Backendless to do database manipulations */
        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        QueryOptions queryOptions = new QueryOptions();
        //queryOptions.setRelated(Arrays.asList("locations"));
        queryOptions.setRelated(Arrays.asList("dish"));

        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);

        Backendless.Data.of(Kitchen.class).find(query, new LoadingCallback<BackendlessCollection<Kitchen>>(this, getString(R.string.loading_kitchens), true) {
            @Override
            public void handleResponse(BackendlessCollection<Kitchen> kitchensBackendlessCollection) {
                kitchen = kitchensBackendlessCollection;

                addMoreItems(kitchensBackendlessCollection);

                super.handleResponse(kitchensBackendlessCollection);
            }
        });

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1698 Hostetter Rd San Jose 95131");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                Intent intent = new Intent(KitchenHomepageActivity.this, SearchableActivity.class);
//                startActivity(intent);
//                startActivity(mapIntent);
//                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
//            }
//        });

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
//                    kitchen.nextPage(new LoadingCallback<BackendlessCollection<Kitchen>>(KitchenHomepageActivity.this) {
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

        recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = glm.getChildCount();
                    totalItemCount = glm.getItemCount();
                    firstVisibleItem = glm.findFirstVisibleItemPosition();

                    if (needToLoadItems(firstVisibleItem, visibleItemCount, totalItemCount)) {
                        isLoadingItems = true;

                        kitchen.nextPage(new LoadingCallback<BackendlessCollection<Kitchen>>(KitchenHomepageActivity.this) {
                            @Override
                            public void handleResponse(BackendlessCollection<Kitchen> nextPage) {
                                kitchen = nextPage;

                                addMoreItems(nextPage);

                                isLoadingItems = false;
                            }
                        });
                    }

                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_shopping_cart) {
            // Handle the camera action
        } else if (id == R.id.nav_order_history) {
            Intent intent = new Intent(KitchenHomepageActivity.this, OrderHistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(KitchenHomepageActivity.this, FollowedKitchenActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {
//            Intent settingIntent = new Intent(this, SettingsActivity.class);
//            startActivity(settingIntent);


        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_log) {
             // Add Log Out
//            Intent logIntent = new Intent(this, edu.scu.ytong.homekitchen.LoginActivity.class);
//            startActivity(logIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            Intent searchIntent = new Intent(KitchenHomepageActivity.this, SearchableActivity.class);
            startActivity(searchIntent);
//            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
        if (id == R.id.likes) {
            Intent likedKitchen = new Intent(KitchenHomepageActivity.this, FollowedKitchenActivity.class);
            startActivity(likedKitchen);
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