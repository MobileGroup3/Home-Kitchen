package com.backendless.hk3.login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class RegisterActivity extends Activity
{
  private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat( "yyyy/MM/dd" );

  private EditText emailField;
  private Spinner is_k_ownerField;
  private EditText nameField;
  private EditText passwordField;

  private Button registerButton;

  private String email;
  private Boolean is_k_owner;
  private String name;
  private String password;

  private HK3User user;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.register );

    initUI();
  }

  private void initUI()
  {emailField = (EditText) findViewById( R.id.emailField );is_k_ownerField = (Spinner) findViewById( R.id.is_k_ownerField );nameField = (EditText) findViewById( R.id.nameField );passwordField = (EditText) findViewById( R.id.passwordField );

    registerButton = (Button) findViewById( R.id.registerButton );

    registerButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRegisterButtonClicked();
      }
    } );
  }

  private void onRegisterButtonClicked()
  {
    String emailText = emailField.getText().toString().trim();
    String is_k_ownerText = is_k_ownerField.getSelectedItem().toString();
    String nameText = nameField.getText().toString().trim();
    String passwordText = passwordField.getText().toString().trim();

    if ( emailText.isEmpty() )
    {
      showToast( "Field 'email' cannot be empty." );
      return;
    }

    if ( passwordText.isEmpty() )
    {
      showToast( "Field 'password' cannot be empty." );
      return;
    }

    if( !emailText.isEmpty() )
    {
      email = emailText;
    }

    if( !is_k_ownerText.isEmpty() )
    {
      is_k_owner = Boolean.parseBoolean( is_k_ownerText );
    }

    if( !nameText.isEmpty() )
    {
      name = nameText;
    }

    if( !passwordText.isEmpty() )
    {
      password = passwordText;
    }

    user = new HK3User();

    if( email != null )
    {
      user.setEmail( email );
    }

    if( is_k_owner != null )
    {
      user.setIs_k_owner( is_k_owner );
    }

    if( name != null )
    {
      user.setName( name );
    }

    if( password != null )
    {
      user.setPassword( password );
    }

    Backendless.UserService.register( user, new DefaultCallback<BackendlessUser>( RegisterActivity.this )
    {
      @Override
      public void handleResponse( BackendlessUser response )
      {
        super.handleResponse( response );
        startActivity( new Intent( RegisterActivity.this, RegistrationSuccessActivity.class ) );
        finish();
      }
    } );
  }

  private void showToast( String msg )
  {
    Toast.makeText( this, msg, Toast.LENGTH_SHORT ).show();
  }
}