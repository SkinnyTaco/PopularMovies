package com.maurdan.flaco.udacitynd_project2_popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movie> mMovieList;

    public GridViewAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovieList = movies;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
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

        final Movie movie = mMovieList.get(position);
        Log.i("MOVIE TIME TIME", movie.getTitle() + "");
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.view_movie_grid, parent, false);
        }

        String poster = Movie.BASE_URL + Movie.DEFAULT_WIDTH + movie.getPoster();
        Log.i("OOOOOOOOOO", poster);

        ImageView imageView = convertView.findViewById(R.id.iv_movie_poster);

        Picasso.get()
                .load(poster)
                .resize(200,200)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

        return convertView;
    }
}
