package com.example.administrator.izienglish.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 23/7/2016.
 */
public class Verbs implements Parcelable {
    private String v1;
    private String v2;
    private String v3;
    private int favorite;
    private String definition;

    public Verbs() {

    }

    public Verbs(String v1, String v2, String v3, String definition, int favorite) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.favorite = favorite;
        this.definition = definition;
    }


    protected Verbs(Parcel in) {
        v1 = in.readString();
        v2 = in.readString();
        v3 = in.readString();
        favorite = in.readInt();
        definition = in.readString();
    }

    public static final Creator<Verbs> CREATOR = new Creator<Verbs>() {
        @Override
        public Verbs createFromParcel(Parcel in) {
            return new Verbs(in);
        }

        @Override
        public Verbs[] newArray(int size) {
            return new Verbs[size];
        }
    };

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String toString() {
        return this.v1 + " - " + this.v2 + " - " + this.v3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(v1);
        parcel.writeString(v2);
        parcel.writeString(v3);
        parcel.writeInt(favorite);
        parcel.writeString(definition);
    }
}
