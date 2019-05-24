package android.example.popularmovie2.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.example.popularmovie2.Movie;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM tableMovie WHERE favorite_bool = 1")
    List<Movie> loadMovies();
    @Insert
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
