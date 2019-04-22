package android.example.popularmovie2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MenuItem item;
    private RecyclerView mMovieRecyclerView;
    private ArrayList<Movie> mMovieList=new ArrayList<>();
    private MovieAdapter movieAdapter;
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovieRecyclerView = findViewById(R.id.rv_movies);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mMovieRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(mMovieList);
        mMovieRecyclerView.setAdapter(movieAdapter);
        makeMovieSortQuery(item);

    }


    void makeMovieSortQuery(MenuItem mItem){
        item = mItem;
        URL movieDBURL;
        if (item == null || item.equals("")){
            movieDBURL = NetworkUtils.buildUrl("popular");
        }else {
            movieDBURL = NetworkUtils.buildUrl((String) item.getTitle());
        }
        new MovieQueryTask().execute(movieDBURL);
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls){
            URL searchUrl = urls[0];
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            }catch (IOException e){
                e.printStackTrace();
            }

            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    mMovieList=JsonUtils.parseMovie(s);
                    mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mMovieRecyclerView.setHasFixedSize(true);
                    movieAdapter = new MovieAdapter(mMovieList);
                    mMovieRecyclerView.setAdapter(movieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = MainActivity.this;
        switch (item.getItemId()) {
            case R.id.sort_popular:
                makeMovieSortQuery(item);
                String message = "Popular Movies Selected";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return true;
            case R.id.sort_top_rated:
                makeMovieSortQuery(item);
                String message2 = "Top Rated Movies Selected";
                Toast.makeText(context, message2, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
