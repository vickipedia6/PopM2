package com.popularmovies2.vignesh.popm2.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Trailers implements Parcelable {
    private final String videoUrl;
    private String key;
    private String name;

    public Trailers(){
        videoUrl = "https://www.youtube.com/watch?v=";
    }

    public String getVideoUrl() {
        return videoUrl + getKey();
    }

    private String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(videoUrl);
    }

    private Trailers(Parcel in){
        key = in.readString();
        name = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel source) {
            return new Trailers(source);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };
}
