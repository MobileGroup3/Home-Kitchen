package com.backendless.hk3.login.entities;

import android.os.Parcel;
import android.os.Parcelable;


public class SimpleCartItem implements Parcelable{
    String dishObjectId;
    int orderQuantity;

    public SimpleCartItem(String dishObjectId, int orderQuantity) {
        this.dishObjectId = dishObjectId;
        this.orderQuantity = orderQuantity;
    }

    public String getDishObjectId() { return dishObjectId;}
    public int getOrderQuantity() { return  orderQuantity; }

    public void setDishObjectId(String dishObjectId) {
        this.dishObjectId = dishObjectId;
    }

    public  void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dishObjectId);
        dest.writeInt(orderQuantity);

    }

    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SimpleCartItem createFromParcel(Parcel in) {
            return  new SimpleCartItem(in);
        }
        public SimpleCartItem[] newArray(int size) {
            return new SimpleCartItem[size];
        }
    };

    public SimpleCartItem(Parcel in) {
        dishObjectId = in.readString();
        orderQuantity = in.readInt();
    }
}

