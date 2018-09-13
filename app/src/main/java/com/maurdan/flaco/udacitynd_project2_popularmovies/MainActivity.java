package com.maurdan.flaco.udacitynd_project2_popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import com.maurdan.flaco.udacitynd_project2_popularmovies.adapters.GridViewAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridLayout = findViewById(R.id.grid_layout);
        gridLayout.setAdapter(new GridViewAdapter(this));

    }
}
