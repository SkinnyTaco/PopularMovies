package com.maurdan.flaco.udacitynd_project2_popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.maurdan.flaco.udacitynd_project2_popularmovies.activities.DetailsActivity;
import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Constants;
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
        return mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = mMovieList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.view_movie_grid, parent, false);
        }

        String poster = Constants.BASE_IMAGE_URL + Constants.DEFAULT_POSTER_WIDTH + movie.getPoster();

        ImageView imageView = convertView.findViewById(R.id.iv_movie_poster);

        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerInside()
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(Constants.BOOK, movie);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
