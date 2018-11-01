package com.beantownloner.favoritetoys.popularmoviesstg1.objects;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    public String title;
    public String posterURL;
    public String backdropURL;
    public String releaseDate;
    public String rating;
    public String overview;

    public Movie() {

    }

    public Movie(String title, String imgURL, String releaseDate, String rating, String overview, String backdropURL) {
        this.title = title;
        this.posterURL = imgURL;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.overview = overview;
        this.backdropURL = backdropURL;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        posterURL = in.readString();
        releaseDate = in.readString();
        rating = in.readString();
        overview = in.readString();
        backdropURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterURL);
        dest.writeString(releaseDate);
        dest.writeString(rating);
        dest.writeString(overview);
        dest.writeString(backdropURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setTitle(String title) {

        this.title = title;
    }

    public void setPosterURL(String posterURL) {

        this.posterURL = posterURL;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public void setBackdropURL(String backdropURL) {
        this.backdropURL = backdropURL;
    }
}


