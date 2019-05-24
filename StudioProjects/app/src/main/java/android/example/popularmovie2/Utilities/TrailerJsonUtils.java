package android.example.popularmovie2.Utilities;

import android.example.popularmovie2.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrailerJsonUtils {


    public static ArrayList<Movie> parseMovie(String json) throws JSONException{

        ArrayList<Movie> movieList = new ArrayList<>();
        try{

            JSONObject movieDetails = new JSONObject(json);
            JSONArray movieArray = movieDetails.getJSONArray("results");

            for (int i=0;i<movieArray.length();i++) {
                movieDetails = movieArray.getJSONObject(i);



                String mainName = movieDetails.optString("title");
                String description = movieDetails.optString("overview");
                String image = movieDetails.optString("poster_path");
                String releaseDate = movieDetails.optString("release_date");
                String badkdropImage = movieDetails.optString("backdrop_path");
                String vote = movieDetails.optString("vote_average");


                movieList.add(new Movie(mainName,description,image,releaseDate,badkdropImage,vote));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return movieList;
    }
}
