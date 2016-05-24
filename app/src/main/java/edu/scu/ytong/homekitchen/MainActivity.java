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
        String appID = "6B06D541-69FC-AA24-FF52-EB6421144100";
        String secretKey = "F2C00252-B60B-8048-FF2B-F2893504BD00";
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
