package edu.scu.ytong.placingorder.entities;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;


public class Kitchen {
    private String phoneNumber;
    private String country;
    private String owner;
    private String ownerId;
    private String objectId;
    private String city;
    private String street;
    private String name;
    private String category;
    private java.util.Date updated;
    private String email;
    private String kitchenPic;
    private java.util.Date created;
    private Menu menu;
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner( String owner )
    {
        this.owner = owner;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory( String category )
    {
        this.category = category;
    }

    public java.util.Date getUpdated()
    {
        return updated;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getKitchenPic()
    {
        return kitchenPic;
    }

    public void setKitchenPic( String kitchenPic )
    {
        this.kitchenPic = kitchenPic;
    }

    public java.util.Date getCreated()
    {
        return created;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu( Menu menu )
    {
        this.menu = menu;
    }


    public Kitchen save()
    {
        return Backendless.Data.of( Kitchen.class ).save( this );
    }

    public Future<Kitchen> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Kitchen> future = new Future<Kitchen>();
            Backendless.Data.of( Kitchen.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Kitchen> callback )
    {
        Backendless.Data.of( Kitchen.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Kitchen.class ).remove( this );
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
            Backendless.Data.of( Kitchen.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Kitchen.class ).remove( this, callback );
    }

    public static Kitchen findById( String id )
    {
        return Backendless.Data.of( Kitchen.class ).findById( id );
    }

    public static Future<Kitchen> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Kitchen> future = new Future<Kitchen>();
            Backendless.Data.of( Kitchen.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Kitchen> callback )
    {
        Backendless.Data.of( Kitchen.class ).findById( id, callback );
    }

    public static Kitchen findFirst()
    {
        return Backendless.Data.of( Kitchen.class ).findFirst();
    }

    public static Future<Kitchen> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Kitchen> future = new Future<Kitchen>();
            Backendless.Data.of( Kitchen.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Kitchen> callback )
    {
        Backendless.Data.of( Kitchen.class ).findFirst( callback );
    }

    public static Kitchen findLast()
    {
        return Backendless.Data.of( Kitchen.class ).findLast();
    }

    public static Future<Kitchen> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Kitchen> future = new Future<Kitchen>();
            Backendless.Data.of( Kitchen.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Kitchen> callback )
    {
        Backendless.Data.of( Kitchen.class ).findLast( callback );
    }

    public static BackendlessCollection<Kitchen> find(BackendlessDataQuery query )
    {
        return Backendless.Data.of( Kitchen.class ).find( query );
    }

    public static Future<BackendlessCollection<Kitchen>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Kitchen>> future = new Future<BackendlessCollection<Kitchen>>();
            Backendless.Data.of( Kitchen.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Kitchen>> callback )
    {
        Backendless.Data.of( Kitchen.class ).find( query, callback );
    }

}
