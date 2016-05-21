package edu.scu.ytong.homekitchen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String appVersion = "v1";
        String appID = "99895A50-3FE5-6905-FF3E-9C004D4E1200";
        String secretKey = "F7E0ACE0-0237-1D41-FF65-9443E0874900";
        Backendless.initApp(this, appID, secretKey, appVersion);

        BackendlessUser user = new BackendlessUser();
        user.setEmail( "michael@backendless.com" );
        user.setPassword( "my_super_password" );

        Backendless.UserService.register(user, new BackendlessCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse( BackendlessUser backendlessUser )
            {
                Log.i( "Registration", backendlessUser.getEmail() + " successfully registered" );
            }
        } );
    }
}
