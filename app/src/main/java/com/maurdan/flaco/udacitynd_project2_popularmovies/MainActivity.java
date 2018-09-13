package com.maurdan.flaco.udacitynd_project2_popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.maurdan.flaco.udacitynd_project2_popularmovies.adapters.GridViewAdapter;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Result;
import com.maurdan.flaco.udacitynd_project2_popularmovies.utils.MovieDBClient;
import com.maurdan.flaco.udacitynd_project2_popularmovies.utils.ServiceGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Movie> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieDBClient client = ServiceGenerator.createService(MovieDBClient.class);
        Call<Result> call = client.getPopularMovies(ServiceGenerator.API_KEY);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ServiceGenerator.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MovieDBClient client = retrofit.create(MovieDBClient.class);
//
//        Call<Result> call = client.getPopularMovies(ServiceGenerator.API_KEY);

        final GridView gridLayout = findViewById(R.id.grid_layout);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                data = result.getResults();
                gridLayout.setAdapter(new GridViewAdapter(MainActivity.this, data));
                Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                Log.i("OOOOOOOOO", data.toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                Log.i("FAILED REQUEST", t + "");
            }
        });

    }
}
