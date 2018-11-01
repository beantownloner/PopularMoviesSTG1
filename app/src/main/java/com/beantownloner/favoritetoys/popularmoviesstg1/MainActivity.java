package com.beantownloner.favoritetoys.popularmoviesstg1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.beantownloner.favoritetoys.popularmoviesstg1.adapters.MoviesAdapter;
import com.beantownloner.favoritetoys.popularmoviesstg1.objects.Movie;
import com.beantownloner.favoritetoys.popularmoviesstg1.utilities.Constant;
import com.beantownloner.favoritetoys.popularmoviesstg1.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    MoviesAdapter adapter;
    String apiKey = Constant.apkKey;
    ArrayList<Movie> movies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPopularMovies();
        Log.d(TAG, movies.size() + " movies in array");
        GridView gridView = findViewById(R.id.movies_view);

        adapter = new MoviesAdapter(this, movies);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                Movie m = movies.get(position);
                intent.putExtra("movie", m);
                startActivity(intent);
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.sortbypopularity) {

            loadPopularMovies();
        } else if (menuItemSelected == R.id.sortbyrating) {

            loadHighestRatedMovies();
        }

        return true;
    }

    public class FetchMovieTask extends AsyncTask<URL, Void, ArrayList<Movie>> {


        @Override
        protected ArrayList<Movie> doInBackground(URL... params) {


            if (!isOnline()) return null;

            if (params.length == 0) {
                return null;
            }
            URL url = params[0];

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(url);


                Log.d(TAG, jsonWeatherResponse);


                ArrayList<Movie> list = getMoviesArray(jsonWeatherResponse);
                movies = list;
                return list;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieLists) {
            if (movieLists != null) {

                adapter.setMovieData(movieLists);
            }

        }
    }

    ArrayList<Movie> getMoviesArray(String s) {
        ArrayList<Movie> results = new ArrayList<>();
        try {


            JSONObject jObj = new JSONObject(s);
            JSONArray jr = jObj.getJSONArray("results");

            for (int i = 0; i < jr.length(); i++) {
                JSONObject jb1 = jr.getJSONObject(i);
                String title = jb1.getString("title");
                String posterPath = jb1.getString("poster_path");
                String releaseDate = jb1.getString("release_date");
                String rating = jb1.getString("vote_average");
                String overview = jb1.getString("overview");
                String backdropURL = jb1.getString("backdrop_path");
                Movie m = new Movie();
                m.setTitle(title);
                m.setPosterURL(posterPath);
                m.setRating(rating);
                m.setReleaseDate(releaseDate);
                m.setOverview(overview);
                m.setBackdropURL(backdropURL);
                results.add(m);
                Log.i(TAG, title + " " + posterPath);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, results.size() + " results ");
        return results;

    }

    private void loadPopularMovies() {
        String moviesURL = Constant.popularMoviesURL + apiKey;
        this.setTitle(R.string.sort_popular);
        URL url = NetworkUtils.buildUrl(moviesURL);
        new FetchMovieTask().execute(url);


    }

    private void loadHighestRatedMovies() {

        String moviesURL = Constant.highestratedMoviesURL + apiKey;
        this.setTitle(R.string.sort_rating);
        URL url = NetworkUtils.buildUrl(moviesURL);
        new FetchMovieTask().execute(url);


    }

    public boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
