package edu.scu.ytong.placingorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import edu.scu.ytong.homekitchen.LoginActivity;
import edu.scu.ytong.homekitchen.SettingsActivity;

public class TestActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, PlacingOrderActivity.class));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(edu.scu.ytong.homekitchen.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, edu.scu.ytong.homekitchen.R.string.navigation_drawer_open, edu.scu.ytong.homekitchen.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(edu.scu.ytong.homekitchen.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == edu.scu.ytong.homekitchen.R.id.nav_shopping_cart) {
            // Handle the camera action
        } else if (id == edu.scu.ytong.homekitchen.R.id.nav_order_history) {

        } else if (id == edu.scu.ytong.homekitchen.R.id.nav_favorite) {

        } else if (id == edu.scu.ytong.homekitchen.R.id.nav_setting) {
            Intent settingIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingIntent);


        } else if (id == edu.scu.ytong.homekitchen.R.id.nav_profile) {

        } else if (id == edu.scu.ytong.homekitchen.R.id.nav_log) {
            Intent logIntent = new Intent(this,LoginActivity.class);
            startActivity(logIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(edu.scu.ytong.homekitchen.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
