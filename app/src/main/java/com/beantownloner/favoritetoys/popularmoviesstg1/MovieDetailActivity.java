package com.beantownloner.favoritetoys.popularmoviesstg1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.beantownloner.favoritetoys.popularmoviesstg1.objects.Movie;
import com.beantownloner.favoritetoys.popularmoviesstg1.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        TextView titleTV = findViewById(R.id.titleTV);
        TextView overviewTV = findViewById(R.id.overviewTV);
        TextView ratingTV = findViewById(R.id.ratingTV);
        TextView releasedateTV = findViewById(R.id.releasedateTV);
        ImageView posterIV = findViewById(R.id.posterIV);
        ImageView backdropIV = findViewById(R.id.backdropIV);
        Intent intent = getIntent();

        if (intent != null) {

            if (intent.getExtras() != null) {

                String title;
                String posterURL;
                String backdropURL;
                String releaseDate;

                Movie movie = getIntent().getParcelableExtra("movie");
                if (movie != null) {
                    title = movie.getTitle();
                    posterURL = Constant.imageBaseURL + movie.getPosterURL();
                    backdropURL = Constant.imageBaseURL + movie.getBackdropURL();
                    releaseDate = movie.getReleaseDate();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
                    String ds2 = "";
                    try {
                        ds2 = sdf2.format(sdf1.parse(releaseDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    titleTV.setText(title);
                    releasedateTV.setText(ds2);
                    String rating = movie.getRating() +"/10";
                    ratingTV.setText(rating);
                    overviewTV.setText(movie.getOverview());

                    Context context = getApplicationContext();
                    Picasso.with(context)  //
                            .load(posterURL)
                            .fit()
                            .tag(context)//
                            .into(posterIV);
                    Picasso.with(context)  //
                            .load(backdropURL)
                            .fit()
                            .tag(context)//
                            .into(backdropIV);

                }


            }
        }

    }
}
