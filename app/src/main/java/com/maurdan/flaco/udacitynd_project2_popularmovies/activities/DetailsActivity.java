package com.maurdan.flaco.udacitynd_project2_popularmovies.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.data.MovieDatabase;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Favorite;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Review;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.ReviewResults;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.AppExecutors;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Constants;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.MovieDBClient;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_movie_banner)
    ImageView ivBanner;

    @BindView(R.id.iv_mini_poster)
    ImageView ivPoster;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;

    @BindView(R.id.tv_plot_synopsis)
    TextView tvSynopsis;

    @BindView(R.id.tv_fab)
    TextView tvFab;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.review)
    TextView tvReview;

    MovieDatabase movieDb;

    MovieDBClient client;

    List<ReviewResults> reviewResults;

    Favorite favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setTitle(R.string.name_movie_details);

        final Movie movie = getIntent().getParcelableExtra(Constants.MOVIE_OBJECT);

        client = ServiceGenerator.createService(MovieDBClient.class);

        movieDb = MovieDatabase.getInstance(DetailsActivity.this);
        populateUi(movie);
//        final LiveData<Movie> databaseMovie = movieDb.movieDao().loadMovie(movie.getId());
//        databaseMovie.observe(this, new Observer<Movie>() {
//            @Override
//            public void onChanged(@Nullable Movie movie) {
//                databaseMovie.removeObserver(this);
//                populateUi(movie);
//
//            }
//        });


    }

    private void getReview(Movie movie) {
        Call<Review> call = client.getReviews(Integer.toString(movie.getId()), Constants.API_KEY);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Review review = response.body();
                reviewResults = review.getReviews();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!reviewResults.isEmpty()) {

                            tvReview.setText((CharSequence) reviewResults.get(0).getContent());
                        } else {
                            tvReview.setText("No reviews yet.");
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error getting reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUi(final Movie movie) {
        String bannerUrl = Constants.BASE_IMAGE_URL + Constants.BANNER_WIDTH + movie.getBanner();

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.get()
                    .load(bannerUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .centerCrop()
                    .into(ivBanner);
        } else {

            Picasso.get()
                    .load(bannerUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .resize(screenWidth, 0)
                    .centerInside()
                    .into(ivBanner);
        }
        tvTitle.setText(movie.getTitle());

        tvReleaseDate.setText(movie.getReleaseDate());

        String posterUrl = Constants.BASE_IMAGE_URL + Constants.DEFAULT_POSTER_WIDTH + movie.getPoster();

        int posterWidth = (int) Math.round(screenWidth * 0.33);
        int posterHeight = (int) Math.round(posterWidth * 1.5);

        Picasso.get()
                .load(posterUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .resize(posterWidth, posterHeight)
                .centerInside()
                .into(ivPoster);

        tvVoteAverage.setText(
                getResources().getString(R.string.average_rating, movie.getVoteAverage())
        );

        tvSynopsis.setText(movie.getSynopsis());

        getReview(movie);

        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                favorite = movieDb.movieDao().loadFavoriteById(movie.getId());
                if (favorite == null || !favorite.isFavorite()) {
                    tvFab.setText(R.string.fab_fav);
                } else {
                    tvFab.setText(R.string.fav_remove_fav);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        favorite = movieDb.movieDao().loadFavoriteById(movie.getId());
                        if (favorite == null) {
                            Favorite newFavorite = new Favorite(movie);
                            movieDb.movieDao().addFavorite(newFavorite);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvFab.setText(R.string.fav_remove_fav);
                                }
                            });
                        } else {
                            favorite.setFavorite(!favorite.isFavorite());
                            movieDb.movieDao().addFavorite(favorite);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (favorite.isFavorite()) {
                                        tvFab.setText(R.string.fav_remove_fav);
                                    } else {
                                        tvFab.setText(R.string.fab_fav);
                                    }

                                }
                            });
                        }
                    }
                });
            }
        });
    }

}
