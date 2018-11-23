package com.maurdan.flaco.udacitynd_project2_popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Favorite {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @Embedded(prefix = "movie_")
    private Movie movie;

    public Favorite(boolean isFavorite, Movie movie) {
        this.id = movie.getId();
        this.movie = movie;
        this.isFavorite = isFavorite;
    }

    @Ignore
    public Favorite(Movie movie) {
        this.movie = movie;
        this.id = movie.getId();
        this.isFavorite = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
