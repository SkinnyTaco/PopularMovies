package com.maurdan.flaco.udacitynd_project2_popularmovies.util;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBClient {

    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<Result> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") Integer id, @Query("api_key") String apiKey);

}
