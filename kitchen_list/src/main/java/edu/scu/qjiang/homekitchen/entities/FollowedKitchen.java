package edu.scu.qjiang.homekitchen.entities;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class FollowedKitchen
{
  private String objectId;
  private java.util.Date updated;
  private java.util.Date created;
  private String ownerId;
  private BackendlessUser customer;
  private java.util.List<Kitchen> followed_kitchen_list;
  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public BackendlessUser getCustomer()
  {
    return customer;
  }

  public void setCustomer( BackendlessUser customer )
  {
    this.customer = customer;
  }

  public java.util.List<Kitchen> getFollowed_kitchen_list()
  {
    return followed_kitchen_list;
  }

  public void setFollowed_kitchen_list( java.util.List<Kitchen> followed_kitchen_list )
  {
    this.followed_kitchen_list = followed_kitchen_list;
  }

                                                    
  public FollowedKitchen save()
  {
    return Backendless.Data.of( FollowedKitchen.class ).save( this );
  }

  public Future<FollowedKitchen> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FollowedKitchen> future = new Future<FollowedKitchen>();
      Backendless.Data.of( FollowedKitchen.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<FollowedKitchen> callback )
  {
    Backendless.Data.of( FollowedKitchen.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( FollowedKitchen.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( FollowedKitchen.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( FollowedKitchen.class ).remove( this, callback );
  }

  public static FollowedKitchen findById( String id )
  {
    return Backendless.Data.of( FollowedKitchen.class ).findById( id );
  }

  public static Future<FollowedKitchen> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FollowedKitchen> future = new Future<FollowedKitchen>();
      Backendless.Data.of( FollowedKitchen.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<FollowedKitchen> callback )
  {
    Backendless.Data.of( FollowedKitchen.class ).findById( id, callback );
  }

  public static FollowedKitchen findFirst()
  {
    return Backendless.Data.of( FollowedKitchen.class ).findFirst();
  }

  public static Future<FollowedKitchen> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FollowedKitchen> future = new Future<FollowedKitchen>();
      Backendless.Data.of( FollowedKitchen.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<FollowedKitchen> callback )
  {
    Backendless.Data.of( FollowedKitchen.class ).findFirst( callback );
  }

  public static FollowedKitchen findLast()
  {
    return Backendless.Data.of( FollowedKitchen.class ).findLast();
  }

  public static Future<FollowedKitchen> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FollowedKitchen> future = new Future<FollowedKitchen>();
      Backendless.Data.of( FollowedKitchen.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<FollowedKitchen> callback )
  {
    Backendless.Data.of( FollowedKitchen.class ).findLast( callback );
  }

  public static BackendlessCollection<FollowedKitchen> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( FollowedKitchen.class ).find( query );
  }

  public static Future<BackendlessCollection<FollowedKitchen>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<FollowedKitchen>> future = new Future<BackendlessCollection<FollowedKitchen>>();
      Backendless.Data.of( FollowedKitchen.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<FollowedKitchen>> callback )
  {
    Backendless.Data.of( FollowedKitchen.class ).find( query, callback );
  }
}