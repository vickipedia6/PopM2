package com.popularmovies2.vignesh.popm2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable {

    private String posterPath;
    private String overView;
    private String releaseDate;
    private int id;
    private double voteAverage;
    private String originalTitle;

    public MovieData(){}


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(overView);
        dest.writeString(releaseDate);
        dest.writeString(originalTitle);
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
    }

    private MovieData(Parcel in){
        posterPath = in.readString();
        overView = in.readString();
        releaseDate = in.readString();
        originalTitle = in.readString();
        id = in.readInt();
        voteAverage = in.readDouble();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
