package com.maurdan.flaco.udacitynd_project2_popularmovies.utils;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/popular")
    Call<List<Movie>> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<List<Movie>> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<List<Movie>> getMovieDetails(@Path("movie_id") Integer id, @Query("api_key") String apiKey);

}
