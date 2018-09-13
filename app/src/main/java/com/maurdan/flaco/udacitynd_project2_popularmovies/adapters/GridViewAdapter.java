package com.maurdan.flaco.udacitynd_project2_popularmovies.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movie> mMovieList;

    public GridViewAdapter(Context context) {
        mContext = context;
//        mMovieList = movieList;
    }

    @Override
    public int getCount() {
//        return mMovieList.size();
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText("I'm text, weee");
        return textView;
    }
}
