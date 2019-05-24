package android.example.popularmovie2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.example.popularmovie2.Database.AppDatabase;
import android.example.popularmovie2.Settings.SettingActivity;
import android.example.popularmovie2.Utilities.JsonUtils;
import android.example.popularmovie2.Utilities.NetworkUtils;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String IMAGE_URL = "poster_path";
    public static final String DESCRIPTION = "overview";
    public static final String DATE = "release_date";
    public static final String TITLE = "title";
    public static final String VOTE = "vote_average";



    private MenuItem item;
    private URL movieDBURL;
    private String sortMovies;
    private CheckBox favorites;
    private CheckBoxPreference mCheckboxP;
    private CheckBoxPreference mCheckboxT;
    private CheckBoxPreference mCheckboxF;
    private RecyclerView mMovieRecyclerView;
    private ArrayList<Movie> mMovieList=new ArrayList<>();
    private MovieAdapter movieAdapter;
    private AppDatabase mDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovieRecyclerView = findViewById(R.id.rv_movies);
        mMovieRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(mMovieList,this);
        mMovieRecyclerView.setAdapter(movieAdapter);
        movieAdapter.setOnItemClickListener(MainActivity.this);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mDB = AppDatabase.getInstance((getApplicationContext()));
        setupSharedPreferences();

    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,Detail.class);
        Movie clickedPoster = mMovieList.get(position);

        detailIntent.putExtra(IMAGE_URL, clickedPoster.getImage());
        detailIntent.putExtra(DESCRIPTION, clickedPoster.getDescription());
        detailIntent.putExtra(DATE, clickedPoster.getReleaseDate());
        detailIntent.putExtra(TITLE, clickedPoster.getMovieName());
        detailIntent.putExtra(VOTE, clickedPoster.getVote());
        startActivity(detailIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setupSharedPreferences();
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
                    mMovieList= JsonUtils.parseMovie(s);
                    mMovieRecyclerView = findViewById(R.id.rv_movies);
                    mMovieRecyclerView.setHasFixedSize(true);
                    movieAdapter = new MovieAdapter(mMovieList,MainActivity.this);
                    mMovieRecyclerView.setAdapter(movieAdapter);
                    movieAdapter.setOnItemClickListener(MainActivity.this);
                    mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                   // mDB = AppDatabase.getInstance((getApplicationContext()));
                    //movieAdapter.setmMovieList((ArrayList<Movie>) mDB.movieDao().loadMovies());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean popularCheckBox = sharedPreferences.getBoolean(getString(R.string.pref_popular),false);
        boolean topratedCheckBox = sharedPreferences.getBoolean(getString(R.string.pref_top_rated), false);
        boolean favoriteCheckBox = sharedPreferences.getBoolean(getString(R.string.pref_favorites), false);
        sortMovies = checkeSharedPreferences(editor,popularCheckBox,topratedCheckBox,favoriteCheckBox);

        if (sortMovies == null || sortMovies.equals("")){
            movieDBURL = NetworkUtils.buildUrl("popular");
        }else {
            switch(sortMovies){
                case "Popular":
                    movieDBURL = NetworkUtils.buildUrl("popular");
                    new MovieQueryTask().execute(movieDBURL);
                    break;

                case "Top Rated":
                    movieDBURL = NetworkUtils.buildUrl("top_rated");
                    new MovieQueryTask().execute(movieDBURL);
                    break;

                case "Favorites":
                    favorites = findViewById(R.id.cb_favorite);
                    mMovieList = (ArrayList<Movie>) mDB.movieDao().loadMovies();
                    movieAdapter = new MovieAdapter(mMovieList,this);
                    mMovieRecyclerView.setAdapter(movieAdapter);
                    break;
                default:
                    movieDBURL = NetworkUtils.buildUrl("popular");
                    new MovieQueryTask().execute(movieDBURL);
            }

        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private String  checkeSharedPreferences(SharedPreferences.Editor editor,boolean popularCheckBox, boolean topratedCheckBox, boolean favoriteCheckBox){

        if (popularCheckBox){
            editor.putBoolean(getString(R.string.pref_top_rated),false);
            editor.putBoolean(getString(R.string.pref_favorites),false);
            return getString(R.string.popular);
        }
        if (topratedCheckBox){
            editor.putBoolean(getString(R.string.pref_popular),false);
            editor.putBoolean(getString(R.string.pref_favorites),false);
            return getString(R.string.top_rated);
        }
        if (favoriteCheckBox){
            editor.putBoolean(getString(R.string.pref_popular),false);
            editor.putBoolean(getString(R.string.pref_top_rated),false);
            return getString(R.string.favorites);
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            Intent startSettings = new Intent(this, SettingActivity.class);
            startActivity(startSettings);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
