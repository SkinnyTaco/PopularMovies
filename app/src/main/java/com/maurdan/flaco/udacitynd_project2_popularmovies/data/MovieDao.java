package com.maurdan.flaco.udacitynd_project2_popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> loadAll();

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie loadMovie(int id);
}
