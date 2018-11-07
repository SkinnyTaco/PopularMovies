package com.maurdan.flaco.udacitynd_project2_popularmovies.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Equations;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.MovieDBClient;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.ServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static final String PREFERENCE = "preference";

    private SharedPreferences sharedPreferences;
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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        if (savedInstanceState != null) {
        }

        ButterKnife.bind(this);

        numOfColumns = Equations.getNumberOfColums();

        mMovieDatabase = MovieDatabase.getInstance(this);

        client = ServiceGenerator.createService(MovieDBClient.class);
        getCall();
        makeCall(call);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PREFERENCE, sharedPreferences
                .getString(getString(R.string.pref_sort_order_key),
                        getString(R.string.pref_sort_order_value_popularity)));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.menu_top_rated:
//                setTitle(R.string.name_top_rated);
//                optionSelected = 0;
//                call = client.getTopRatedMovies(Constants.API_KEY);
//                makeCall(call);
//                return true;
//            case R.id.menu_most_popular:
//                setTitle(R.string.app_name);
//                optionSelected = 1;
//                call = client.getPopularMovies(Constants.API_KEY);
//                makeCall(call);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

    private void getCall() {
        String preference = sharedPreferences.getString(getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_value_popularity));
        if (preference.equals(getString(R.string.pref_sort_order_value_popularity))) {
            call = client.getPopularMovies(Constants.API_KEY);
        }
        if (preference.equals(getString(R.string.pref_sort_order_value_rating))) {
            call = client.getTopRatedMovies(Constants.API_KEY);
        }
        if (preference.equals(getString(R.string.pref_sort_order_value_favorites))) {
            call = null;
        }
    }

    private void makeCall(Call<Result> call) {
        if (call != null) {
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
                                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,
                                            numOfColumns));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getCall();
        makeCall(call);
    }
}
