package com.backendless.hk3.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.hk3.login.kitchen_list.KitchenHomepage;

import edu.scu.ytong.homekitchen.MainActivity;
//import com.backendless.hk3.login.com.backendless.hk3.login.kitchen.CreateKitchen;

//import edu.scu.qjiang.homekitchen.MainActivity;

public class LoginActivity extends AppCompatActivity
{
  private TextView registerLink, restoreLink;
  private EditText identityField, passwordField;
  private Button loginButton;
  private CheckBox rememberLoginBox;

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.login );

    initUI();

    Backendless.setUrl( Defaults.SERVER_URL );
    Backendless.initApp( this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION );

    Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this )
    {
      @Override
      public void handleResponse( Boolean isValidLogin )
      {
        if( isValidLogin && Backendless.UserService.CurrentUser() == null )
        {
          String currentUserId = Backendless.UserService.loggedInUser();

          if( !currentUserId.equals( "" ) )
          {
            Backendless.UserService.findById( currentUserId, new DefaultCallback<BackendlessUser>( LoginActivity.this, "Logging in..." )
            {
              @Override
              public void handleResponse( BackendlessUser currentUser )
              {
                super.handleResponse( currentUser );
                Backendless.UserService.setCurrentUser( currentUser );
                  HK3User user = (HK3User)currentUser;
                startActivity( new Intent( getBaseContext(), KitchenHomepage.class ) );
                finish();
              }
            } );
          }
        }

        super.handleResponse( isValidLogin );
      }
    });
  }

  private void initUI()
  {
    registerLink = (TextView) findViewById( R.id.registerLink );
    restoreLink = (TextView) findViewById( R.id.restoreLink );
    identityField = (EditText) findViewById( R.id.identityField );
    passwordField = (EditText) findViewById( R.id.passwordField );
    loginButton = (Button) findViewById( R.id.loginButton );
    rememberLoginBox = (CheckBox) findViewById( R.id.rememberLoginBox );

    String tempString = getResources().getString( R.string.register_text );
    SpannableString underlinedContent = new SpannableString( tempString );
    underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
    registerLink.setText( underlinedContent );
    tempString = getResources().getString( R.string.restore_link );
    underlinedContent = new SpannableString( tempString );
    underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
    restoreLink.setText( underlinedContent );

    loginButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onLoginButtonClicked();
      }
    } );

    registerLink.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRegisterLinkClicked();
      }
    } );

    restoreLink.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRestoreLinkClicked();
      }
    } );
  }

  public void onLoginButtonClicked()
  {
    String identity = identityField.getText().toString();
    String password = passwordField.getText().toString();
    boolean rememberLogin = rememberLoginBox.isChecked();

    Backendless.UserService.login( identity, password, new DefaultCallback<BackendlessUser>( LoginActivity.this )
    {
      public void handleResponse( BackendlessUser backendlessUser )
      {
        super.handleResponse( backendlessUser );
        startActivity( new Intent( LoginActivity.this, KitchenHomepage.class ) );
        finish();
      }
    }, rememberLogin );
  }

  public void onRegisterLinkClicked()
  {
    startActivity( new Intent( this, RegisterActivity.class ) );
    finish();
  }

  public void onRestoreLinkClicked()
  {
    startActivity( new Intent( this, RestorePasswordActivity.class ) );
    finish();
  }
}