package com.backendless.hk3.login;

import com.backendless.BackendlessUser;

public class HK3User extends BackendlessUser
{
  public String getEmail()
  {
    return super.getEmail();
  }

  public void setEmail( String email )
  {
    super.setEmail( email );
//    setProperty("email", email);
  }

  public String getPassword()
  {
    return super.getPassword();
  }

  public Boolean getIs_k_owner()
  {
    return (Boolean) super.getProperty( "is_k_owner" );
  }

  public void setIs_k_owner( Boolean is_k_owner )
  {
    super.setProperty( "is_k_owner", is_k_owner );
//    setProperty("is_k_owner", is_k_owner);
  }

  public String getName()
  {
    return (String) super.getProperty( "name" );
  }

  public void setName( String name )
  {
    super.setProperty( "name", name );
//    setProperty("name", name);
  }
}