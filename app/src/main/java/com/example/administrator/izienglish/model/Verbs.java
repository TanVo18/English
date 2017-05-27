package com.example.administrator.izienglish.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 23/7/2016.
 */
public class Verbs implements Parcelable,Comparable<Verbs> {
    private String v1;
    private String v2;
    private String v3;
    private String ipa1;
    private String ipa2;
    private String ipa3;
    private int favorite;
    private String definition;
    private String example;

    public Verbs() {

    }

    public Verbs(String v1, String ipa1, String v2, String ipa2, String v3, String ipa3, String definition, int favorite) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.ipa1 = ipa1;
        this.ipa2 = ipa2;
        this.ipa3 = ipa3;
        this.favorite = favorite;
        this.definition = definition;
    }

    protected Verbs(Parcel in) {
        v1 = in.readString();
        v2 = in.readString();
        v3 = in.readString();
        ipa1 = in.readString();
        ipa2 = in.readString();
        ipa3 = in.readString();
        favorite = in.readInt();
        definition = in.readString();
        example = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(v1);
        dest.writeString(v2);
        dest.writeString(v3);
        dest.writeString(ipa1);
        dest.writeString(ipa2);
        dest.writeString(ipa3);
        dest.writeInt(favorite);
        dest.writeString(definition);
        dest.writeString(example);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getIpa1() {
        return ipa1;
    }

    public void setIpa1(String ipa1) {
        this.ipa1 = ipa1;
    }

    public String getIpa2() {
        return ipa2;
    }

    public void setIpa2(String ipa2) {
        this.ipa2 = ipa2;
    }

    public String getIpa3() {
        return ipa3;
    }

    public void setIpa3(String ipa3) {
        this.ipa3 = ipa3;
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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


    @Override
    public int compareTo(@NonNull Verbs verbs) {
        int cmp = this.getV1().toLowerCase().toString().compareTo(verbs.getV1().toLowerCase().toString());
        return cmp;
    }
}
