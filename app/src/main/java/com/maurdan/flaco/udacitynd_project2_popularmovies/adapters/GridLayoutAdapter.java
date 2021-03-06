package com.maurdan.flaco.udacitynd_project2_popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.maurdan.flaco.udacitynd_project2_popularmovies.R;
import com.maurdan.flaco.udacitynd_project2_popularmovies.activities.DetailsActivity;
import com.maurdan.flaco.udacitynd_project2_popularmovies.activities.MainActivity;
import com.maurdan.flaco.udacitynd_project2_popularmovies.model.Movie;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Constants;
import com.maurdan.flaco.udacitynd_project2_popularmovies.util.Equations;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.GridViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;

    public GridLayoutAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovieList = movies;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.view_movie_grid, parent, false);

        return new GridViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        if (mMovieList == null || mMovieList.size() == 0) {
            Toast.makeText(mContext, "No favorites, yet!", Toast.LENGTH_SHORT).show();
        } else {
            final Movie movie = mMovieList.get(position);
            int numOfColumns = Equations.getNumberOfColums();
            float dpWidth = Equations.getDpWidth();
            int desiredImageWidth = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, mContext.getResources().getDisplayMetrics()) * 1.0 / numOfColumns);
            int dpHeight = (int) (dpWidth / numOfColumns * 1.5);
            int desiredImageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, mContext.getResources().getDisplayMetrics());
            holder.imageView.getLayoutParams().width = desiredImageWidth;
            holder.imageView.getLayoutParams().height = desiredImageHeight;
            Log.i("IMAGE HEIGHT", String.valueOf(dpHeight) + " " + String.valueOf(desiredImageHeight));

            String poster = Constants.BASE_IMAGE_URL + Constants.DEFAULT_POSTER_WIDTH + movie.getPoster();

            Picasso.get()
                    .load(poster)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .centerInside()
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Constants.MOVIE_OBJECT, movie);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) {
            return 0;
        } else {
            return mMovieList.size();
        }
    }


    public class GridViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView imageView;

        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
