package edu.scu.ytong.placingorder.entities;


import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Dish {
    private String objectId;
    private String ownerId;
    private java.util.Date created;
    private java.util.Date updated;
    private java.util.List<DishItem> dishItem;
    public String getObjectId()
    {
        return objectId;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public java.util.Date getCreated()
    {
        return created;
    }

    public java.util.Date getUpdated()
    {
        return updated;
    }

    public java.util.List<DishItem> getDishItem()
    {
        return dishItem;
    }

    public void setDishItem( java.util.List<DishItem> dishItem )
    {
        this.dishItem = dishItem;
    }


    public Dish save()
    {
        return Backendless.Data.of( Dish.class ).save( this );
    }

    public Future<Dish> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Dish> future = new Future<Dish>();
            Backendless.Data.of( Dish.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Dish> callback )
    {
        Backendless.Data.of( Dish.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Dish.class ).remove( this );
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
            Backendless.Data.of( Dish.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Dish.class ).remove( this, callback );
    }

    public static Dish findById( String id )
    {
        return Backendless.Data.of( Dish.class ).findById( id );
    }

    public static Future<Dish> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Dish> future = new Future<Dish>();
            Backendless.Data.of( Dish.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Dish> callback )
    {
        Backendless.Data.of( Dish.class ).findById( id, callback );
    }

    public static Dish findFirst()
    {
        return Backendless.Data.of( Dish.class ).findFirst();
    }

    public static Future<Dish> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Dish> future = new Future<Dish>();
            Backendless.Data.of( Dish.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Dish> callback )
    {
        Backendless.Data.of( Dish.class ).findFirst( callback );
    }

    public static Dish findLast()
    {
        return Backendless.Data.of( Dish.class ).findLast();
    }

    public static Future<Dish> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Dish> future = new Future<Dish>();
            Backendless.Data.of( Dish.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Dish> callback )
    {
        Backendless.Data.of( Dish.class ).findLast( callback );
    }

    public static BackendlessCollection<Dish> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Dish.class ).find( query );
    }

    public static Future<BackendlessCollection<Dish>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Dish>> future = new Future<BackendlessCollection<Dish>>();
            Backendless.Data.of( Dish.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Dish>> callback )
    {
        Backendless.Data.of( Dish.class ).find( query, callback );
    }


}
