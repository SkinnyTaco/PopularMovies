package com.maurdan.flaco.udacitynd_project2_popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Favorite;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadAll();

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> loadMovie(int id);

    @Query("SELECT * FROM favorite")
    List<Favorite> loadFavorites();

    @Query("SELECT * FROM favorite WHERE movie_id = :id")
    Favorite loadFavoriteById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovies(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(Favorite favorite);

}
