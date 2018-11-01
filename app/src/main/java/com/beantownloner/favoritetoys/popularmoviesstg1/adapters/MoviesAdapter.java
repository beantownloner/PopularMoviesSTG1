package com.beantownloner.favoritetoys.popularmoviesstg1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.beantownloner.favoritetoys.popularmoviesstg1.objects.Movie;
import com.beantownloner.favoritetoys.popularmoviesstg1.R;
import com.beantownloner.favoritetoys.popularmoviesstg1.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends BaseAdapter {
    private String TAG = "MoviesAdapter";
    private ArrayList<Movie> movies = new ArrayList<>();
    private final Context context;

    private LayoutInflater mInflater;
    private ImageView posterImageView;


    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        this.mInflater = LayoutInflater.from(context);
        this.movies = movies;

        this.context = context;
    }

    @Override
    public int getCount() {
        if (movies.size() == 0) {
            return 0;
        }
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if (gridView == null) {

            gridView = mInflater.inflate(R.layout.movies_list, parent, false);


        }
        //ImageView movieIV;
        posterImageView = gridView.findViewById(R.id.posterView);

        Movie movie = getItem(position);
        String imageBaseUrl = Constant.imageBaseURL + movie.getPosterURL();
        Log.d(TAG, imageBaseUrl);
        Picasso.with(context)  //
                .load(imageBaseUrl)
                //  .fit()
                .tag(context)//
                .into(posterImageView);

        return gridView;
    }

    public void setMovieData(ArrayList<Movie> movieData) {
        movies = movieData;
        notifyDataSetChanged();
    }
}
