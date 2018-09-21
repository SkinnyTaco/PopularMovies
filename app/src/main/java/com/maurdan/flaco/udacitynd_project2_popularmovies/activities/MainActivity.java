package com.maurdan.flaco.udacitynd_project2_popularmovies.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.adapters.GridViewAdapter;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Result;
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

    @BindView(R.id.grid_layout) GridView gridLayout;

    private List<Movie> data;
    private MovieDBClient client;
    private GridViewAdapter mGridViewAdapter;
    private Call<Result> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        gridLayout.setColumnWidth(screenWidth/2);

        client = ServiceGenerator.createService(MovieDBClient.class);
        call = client.getPopularMovies(Constants.API_KEY);
        makeCall(call);

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
            case R.id.menu_most_popular:
                setTitle(R.string.app_name);
                call = client.getPopularMovies(Constants.API_KEY);
                makeCall(call);
                return true;
            case R.id.menu_top_rated:
                setTitle(R.string.name_top_rated);
                call = client.getTopRatedMovies(Constants.API_KEY);
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
                mGridViewAdapter = new GridViewAdapter(MainActivity.this, data);
                gridLayout.setAdapter(mGridViewAdapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.request_failed, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
