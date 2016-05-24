package edu.scu.ytong.placingorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class CartToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_toolbar);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Test");

    }
}
