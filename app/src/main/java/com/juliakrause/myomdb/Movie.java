package com.juliakrause.myomdb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Julia on 13.11.2016.
 */

public class Movie implements Parcelable {

    private String imdbID;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String type;


    public Movie(String imdbID, String title, String year, String rated, String released, String runtime, String genre, String director, String writer, String actors, String plot, String type) {
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.type = type;
    }

    public Movie(String imdbID, String title, String year, String type) {
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.rated = "";
        this.released = "";
        this.runtime = "";
        this.genre = "";
        this.director = "";
        this.writer = "";
        this.actors = "";
        this.plot = "";
        this.type = type;
    }

    protected Movie(Parcel in) {
        imdbID = in.readString();
        title = in.readString();
        year = in.readString();
        rated = in.readString();
        released = in.readString();
        runtime = in.readString();
        genre = in.readString();
        director = in.readString();
        writer = in.readString();
        actors = in.readString();
        plot = in.readString();
    }

    public String getImdbID() {
        return imdbID;
    }

    /*public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }*/

    public String getTitle() {
        return title;
    }

    /*public void setTitle(String title) {
        this.title = title;
    }*/

    public String getYear() {
        return year;
    }

    /*public void setYear(String year) {
        this.year = year;
    }*/

    public String getRated() {
        return rated;
    }

    /*public void setRated(String rated) {
        this.rated = rated;
    }*/

    public String getReleased() {
        return released;
    }

    /*public void setReleased(String released) {
        this.released = released;
    }*/

    public String getRuntime() {
        return runtime;
    }

    /*public void setRuntime(String runtime) {
        this.runtime = runtime;
    }*/

    public String getGenre() {
        return genre;
    }

    /*public void setGenre(String genre) {
        this.genre = genre;
    }*/

    public String getDirector() {
        return director;
    }

    /*public void setDirector(String director) {
        this.director = director;
    }*/

    public String getWriter() {
        return writer;
    }

    /*public void setWriter(String writer) {
        this.writer = writer;
    }*/

    public String getActors() {
        return actors;
    }

    /*public void setActors(String actors) {
        this.actors = actors;
    }*/

    public String getPlot() {
        return plot;
    }

    /*public void setPlot(String plot) {
        this.plot = plot;
    }*/

    public String getType() {
        return type;
    }

    /*public void setType(String type) {
        this.type = type;
    }*/

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imdbID);
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(rated);
        dest.writeString(released);
        dest.writeString(runtime);
        dest.writeString(genre);
        dest.writeString(director);
        dest.writeString(writer);
        dest.writeString(actors);
        dest.writeString(plot);
    }

    public Movie createFromParcel(Parcel in) {
        return new Movie(in);
    }

    public Movie[] newArray(int size) {
        return new Movie[size];
    }

}
