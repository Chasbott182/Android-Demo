package android.example.popularmovie2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.example.popularmovie2.Database.AppDatabase;
import android.example.popularmovie2.Settings.SettingActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.example.popularmovie2.MainActivity.DATE;
import static android.example.popularmovie2.MainActivity.DESCRIPTION;
import static android.example.popularmovie2.MainActivity.IMAGE_URL;
import static android.example.popularmovie2.MainActivity.TITLE;
import static android.example.popularmovie2.MainActivity.VOTE;

public class Detail extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    private AppDatabase mDB;
    private ImageView imageView;
    private TextView textViewDescrption;
    private TextView textViewReleaseDate;
    private TextView textViewTitle;
    private TextView textViewVote;
    private CheckBox favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDB= AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
         String image = intent.getStringExtra(IMAGE_URL);
        final String description = intent.getStringExtra(DESCRIPTION);
        final String releaseDate = intent.getStringExtra(DATE);
        final String title = intent.getStringExtra(TITLE);
        final String vote = intent.getStringExtra(VOTE);
        imageView = findViewById(R.id.iv_movie_display);
        textViewDescrption = findViewById(R.id.description_tv);
        textViewReleaseDate = findViewById(R.id.tv_release);
        textViewTitle = findViewById(R.id.tv_big_title);
        textViewVote = findViewById(R.id.tv_vote);
        favorite = findViewById(R.id.cb_favorite);


        String link = "https://image.tmdb.org/t/p/w185";
        image = link+image;

        Picasso.get()
                .load(image)
                .fit()
                .centerInside()
                .into(imageView);

        textViewDescrption.setText(description);
        textViewReleaseDate.setText("Release Date: "+releaseDate);
        textViewTitle.setText(title);
        textViewVote.setText("Vote Average: "+ vote);
        final String finalImage = image;
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavClicked(title, finalImage,description,releaseDate,vote);
            }
        });

        setupSharedPreferences();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setupSharedPreferences();
    }
    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean favoriteCheckBox = sharedPreferences.getBoolean("star_favorites", false);
        favorite.setChecked(favoriteCheckBox);
    }
    public void onFavCheck(boolean boolfav){


    }
    public void onFavClicked(String mTitle, String mImage, String mDescription, String mReleaseDate, String mVote){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        CheckBox favorite = findViewById(R.id.cb_favorite);

        if(favorite.isChecked()) {
            String mQuery = "Select * from tableMovie where movieName =\'"+ mTitle+'\'';
            editor.putBoolean("star_favorites",favorite.isChecked());
            editor.apply();
            Movie movie = new Movie(mTitle, mDescription, mImage, mReleaseDate, mImage, mVote, favorite.isChecked());
            Cursor cursor = mDB.query(mQuery,null);
            if (cursor.getCount()>0){
                mDB.movieDao().updateMovie(movie);
            }else {
                mDB.movieDao().insertMovie(movie);
            }
            Toast.makeText(Detail.this, "Movie is selected as favorite.", Toast.LENGTH_LONG).show();

        }else{
            editor.putBoolean("star_favorites",favorite.isChecked());
            editor.apply();
            Movie movie = new Movie(mTitle, mDescription, mImage, mReleaseDate, mImage, mVote, favorite.isChecked());
            mDB.movieDao().deleteMovie(movie);
            Toast.makeText(Detail.this, "Movie is removed as favorite.", Toast.LENGTH_LONG).show();
        }
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
