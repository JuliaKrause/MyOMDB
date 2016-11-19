package com.juliakrause.greendao.generated;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MOVIE".
 */
public class Movie {

    private Long id;
    /** Not-null value. */
    private String imdbId;
    /** Not-null value. */
    private String title;
    /** Not-null value. */
    private String type;
    private int toWatch;
    private int favorite;

    public Movie() {
    }

    public Movie(Long id) {
        this.id = id;
    }

    public Movie(Long id, String imdbId, String title, String type, int toWatch, int favorite) {
        this.id = id;
        this.imdbId = imdbId;
        this.title = title;
        this.type = type;
        this.toWatch = toWatch;
        this.favorite = favorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getImdbId() {
        return imdbId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Not-null value. */
    public String getType() {
        return type;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setType(String type) {
        this.type = type;
    }

    public int getToWatch() {
        return toWatch;
    }

    public void setToWatch(int toWatch) {
        this.toWatch = toWatch;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

}
