package com.maurdan.flaco.udacitynd_project2_popularmovies.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Favorite;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;

@Database(entities = {Movie.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public static final String MOVIE_DATABASE_NAME = "movies";
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (new Object()) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.MOVIE_DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();

}
