package com.maurdan.flaco.udacitynd_project2_popularmovies.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.adapters.GridLayoutAdapter;
import com.maurdan.flaco.udacitynd_project2_popularmovies.data.MovieDatabase;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Result;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.AppExecutors;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Constants;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.MovieDBClient;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.ServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static final String MENU_OPTION = "option";

    private int optionSelected = -1;

    private MovieDatabase mMovieDatabase;
    private List<Movie> data;
    private MovieDBClient client;
    private GridLayoutAdapter mGridLayoutAdapter;
    private Call<Result> call;
    private int numOfColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            optionSelected = savedInstanceState.getInt(MENU_OPTION);
        }

        ButterKnife.bind(this);

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        float dpWidth = screenWidth / Resources.getSystem().getDisplayMetrics().density;
        numOfColumns = (int) dpWidth / 180;
        numOfColumns = (numOfColumns > 0) ? numOfColumns : 1;

        mMovieDatabase = MovieDatabase.getInstance(this);

        client = ServiceGenerator.createService(MovieDBClient.class);
        call = client.getPopularMovies(Constants.API_KEY);
        makeCall(call);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_OPTION, optionSelected);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        if (optionSelected != -1) {
            onOptionsItemSelected(menu.getItem(optionSelected));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_top_rated:
                setTitle(R.string.name_top_rated);
                optionSelected = 0;
                call = client.getTopRatedMovies(Constants.API_KEY);
                makeCall(call);
                return true;
            case R.id.menu_most_popular:
                setTitle(R.string.app_name);
                optionSelected = 1;
                call = client.getPopularMovies(Constants.API_KEY);
                makeCall(call);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void makeCall(Call<Result> call) {
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                data = result.getResults();
                AppExecutors executors = AppExecutors.getInstance();
                executors.getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mMovieDatabase.movieDao().addMovies(data);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mGridLayoutAdapter = new GridLayoutAdapter(MainActivity.this, data);
                                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numOfColumns));
                                recyclerView.setAdapter(mGridLayoutAdapter);

                            }
                        });
                    }
                });

                Log.i("TESTING", "DID I MAKE IT HERE?????");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.request_failed, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
