package com.backendless.hk3.login.entities;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

/**
 * Created by clover on 5/22/16.
 */
public class DishItem
{
    private String ownerId;
    private Integer max_num;
    private java.util.Date updated;
    private String description;
    private String name;
    private Double price;
    private String picture;
    private String objectId;
    private java.util.Date created;
    public String getOwnerId()
    {
        return ownerId;
    }

    public Integer getMax_num()
    {
        return max_num;
    }

    public void setMax_num( Integer max_num )
    {
        this.max_num = max_num;
    }

    public java.util.Date getUpdated()
    {
        return updated;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice( Double price )
    {
        this.price = price;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture( String picture )
    {
        this.picture = picture;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public java.util.Date getCreated()
    {
        return created;
    }


    public DishItem save()
    {
        return Backendless.Data.of( DishItem.class ).save( this );
    }

    public Future<DishItem> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<DishItem> future = new Future<DishItem>();
            Backendless.Data.of( DishItem.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<DishItem> callback )
    {
        Backendless.Data.of( DishItem.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( DishItem.class ).remove( this );
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
            Backendless.Data.of( DishItem.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( DishItem.class ).remove( this, callback );
    }

    public static DishItem findById( String id )
    {
        return Backendless.Data.of( DishItem.class ).findById( id );
    }

    public static Future<DishItem> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<DishItem> future = new Future<DishItem>();
            Backendless.Data.of( DishItem.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<DishItem> callback )
    {
        Backendless.Data.of( DishItem.class ).findById( id, callback );
    }

    public static DishItem findFirst()
    {
        return Backendless.Data.of( DishItem.class ).findFirst();
    }

    public static Future<DishItem> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<DishItem> future = new Future<DishItem>();
            Backendless.Data.of( DishItem.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<DishItem> callback )
    {
        Backendless.Data.of( DishItem.class ).findFirst( callback );
    }

    public static DishItem findLast()
    {
        return Backendless.Data.of( DishItem.class ).findLast();
    }

    public static Future<DishItem> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<DishItem> future = new Future<DishItem>();
            Backendless.Data.of( DishItem.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<DishItem> callback )
    {
        Backendless.Data.of( DishItem.class ).findLast( callback );
    }

    public static BackendlessCollection<DishItem> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( DishItem.class ).find( query );
    }

    public static Future<BackendlessCollection<DishItem>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<DishItem>> future = new Future<BackendlessCollection<DishItem>>();
            Backendless.Data.of( DishItem.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<DishItem>> callback )
    {
        Backendless.Data.of( DishItem.class ).find( query, callback );
    }
}