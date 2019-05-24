package android.example.popularmovie2.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ReviewUtils {

    final static String THEMOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    final static String APIKEY = "8d93b21d46b32209f68e585f3dc07a4f";
    final static String QUERY = "api_key";
    final static String REVIEW = "reviews";


    public static URL buildUrl(String movieID) {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath(REVIEW)
                .appendQueryParameter(QUERY,APIKEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
