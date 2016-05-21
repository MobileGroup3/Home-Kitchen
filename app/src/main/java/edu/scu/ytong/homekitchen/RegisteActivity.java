package edu.scu.ytong.homekitchen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;

public class RegisteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);


        String appVersion = "v1";
        String appID = "AB60D5BB-C0E0-6CED-FFFA-5A9387D13400";
        String secretKey = "8A8AECCA-94C9-706C-FF2D-2F32385FE000";
        Backendless.initApp( this, appID, secretKey, appVersion);

        BackendlessUser user = new BackendlessUser();
        user.setEmail( "michael@backendless.com" );
        user.setPassword( "my_super_password" );

        Backendless.UserService.register( user, new BackendlessCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse( BackendlessUser backendlessUser )
            {
                Log.i( "Registration", backendlessUser.getEmail() + " successfully registered" );
            }
        } );


    }
}
