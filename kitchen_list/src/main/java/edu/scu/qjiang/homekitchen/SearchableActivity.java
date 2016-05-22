package edu.scu.qjiang.homekitchen;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.squareup.picasso.Picasso;

import edu.scu.qjiang.homekitchen.utility.BackendSettings;
import edu.scu.ytong.placingorder.PlacingOrder;
import edu.scu.ytong.placingorder.entities.Kitchen;

public class SearchableActivity extends AppCompatActivity {
    private TextView kitchenNameView;
    private ImageView kitchenPicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        handleIntent(getIntent());

        kitchenNameView = (TextView) findViewById( R.id.search_kitchenName );
        kitchenPicView = (ImageView) findViewById(R.id.search_imageView);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        String whereClause = "name = '" + query + "'";
        final BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        new AsyncTask<Void,Void,Kitchen>() {
            @Override
            protected Kitchen doInBackground(Void... params) {
                BackendlessCollection<Kitchen> result = Backendless.Persistence.of(Kitchen.class).find(dataQuery);
                if (result.getTotalObjects() > 0) {
                    return result.getData().get(0);
                }
                else {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Kitchen kitchen) {
                if (kitchen != null) {
                    final String objectId = kitchen.getObjectId();
                    Log.d("object id is: ", objectId);
                    kitchenNameView.setText(kitchen.getName());
                    kitchenNameView.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext()).load(kitchen.getKitchenPic()).into(kitchenPicView);
                    kitchenPicView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent kitchenDetail = new Intent(getApplicationContext(), PlacingOrder.class);
                            kitchenDetail.putExtra("object_id_extra_key", objectId);
                            kitchenDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(kitchenDetail);
                        }
                    });
                }
                else {
                    findViewById(R.id.search_kitchenName).setVisibility(View.VISIBLE);
                }

            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
}
