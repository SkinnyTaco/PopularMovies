package com.maurdan.flaco.udacitynd_project2_popularmovies.activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.adapters.GridViewAdapter;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Constants;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.MovieDBClient;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.ServiceGenerator;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle(R.string.name_movie_details);

        MovieDBClient client = ServiceGenerator.createService(MovieDBClient.class);
        int movieId = getIntent().getIntExtra(GridViewAdapter.EXTRA_MOVIE_ID, 0);
        Call<Movie> call = client.getMovieDetails(movieId, Constants.API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();

                ImageView ivBanner = findViewById(R.id.iv_movie_banner);
                String bannerUrl = Movie.BASE_URL + Movie.BANNER_WIDTH + movie.getBanner();

                int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
                int desiredHeightBanner = (int) Math.round(screenWidth * 0.563);

                Picasso.get()
                        .load(bannerUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .resize(screenWidth, desiredHeightBanner)
                        .centerInside()
                        .into(ivBanner);

                TextView tvTitle = findViewById(R.id.tv_title);
                tvTitle.setText(movie.getTitle());

                TextView tvReleaseDate = findViewById(R.id.tv_release_date);
                tvReleaseDate.setText(movie.getReleaseDate());

                ImageView ivPoster = findViewById(R.id.iv_mini_poster);
                String posterUrl = Movie.BASE_URL + Movie.DEFAULT_WIDTH + movie.getPoster();

                int posterWidth = (int) Math.round(screenWidth * 0.33);
                int posterHeight = (int) Math.round(posterWidth * 1.5);

                Picasso.get()
                        .load(posterUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .resize(posterWidth, posterHeight)
                        .centerInside()
                        .into(ivPoster);

                TextView tvVoteAverage = findViewById(R.id.tv_vote_average);
                tvVoteAverage.setText(
                        getResources().getString(R.string.average_rating, movie.getVoteAverage())
                );

                TextView tvSynopsis = findViewById(R.id.tv_plot_synopsis);
                tvSynopsis.setText(movie.getSynopsis());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, R.string.request_failed, Toast.LENGTH_SHORT)
                    .show();
            }
        });

    }
}
