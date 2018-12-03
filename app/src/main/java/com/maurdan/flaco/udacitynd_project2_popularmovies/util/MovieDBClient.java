package com.maurdan.flaco.udacitynd_project2_popularmovies.util;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Result;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBClient {

    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<Result> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<Review> getReviews(@Path("movie_id") String movieId, @Query("api_key") String api_key);

}
