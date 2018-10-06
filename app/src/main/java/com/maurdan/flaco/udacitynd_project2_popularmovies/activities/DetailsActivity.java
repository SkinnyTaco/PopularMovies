package com.maurdan.flaco.udacitynd_project2_popularmovies.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.data.MovieDatabase;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.AppExecutors;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setTitle(R.string.name_movie_details);

        final Movie movie = getIntent().getParcelableExtra(Constants.BOOK);
        MovieDatabase movieDb = MovieDatabase.getInstance(DetailsActivity.this);
        final LiveData<Movie> databaseMovie = movieDb.movieDao().loadMovie(movie.getId());
        databaseMovie.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                databaseMovie.removeObserver(this);
                populateUi(movie);

            }
        });


    }

    private void populateUi(Movie movie) {
        String bannerUrl = Constants.BASE_IMAGE_URL + Constants.BANNER_WIDTH + movie.getBanner();

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        Log.i("SCREEN WIDTH", String.valueOf(screenWidth));
        int desiredHeightBanner = (int) Math.round(screenWidth * 0.563);

        Picasso.get()
                .load(bannerUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .resize(screenWidth, desiredHeightBanner)
                .centerInside()
                .into(ivBanner);

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
    }
}
