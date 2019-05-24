package android.example.popularmovie2;

import android.content.Context;
import android.database.Cursor;
import android.example.popularmovie2.Database.AppDatabase;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovieList;
    private OnItemClickListener mListener;
    private AppDatabase mDB;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public MovieAdapter(ArrayList<Movie> movieList,OnItemClickListener listener){
        mMovieList = movieList;
        mListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context mContext = viewGroup.getContext();
        int layoutForListItem = R.layout.image_item_list;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutForListItem, viewGroup,shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        mDB= AppDatabase.getInstance(context);
        Log.d(TAG, "onBindViewHolder: called.");
        Movie moviePoster = mMovieList.get(i);

        String imageURL =  moviePoster.getImage();

        String link = "https://image.tmdb.org/t/p/w185";

        if (imageURL == null){

            String mQuery = "Select image from tableMovie where movieName =\'"+ moviePoster.getMovieName()+'\'';
            Cursor cursor = mDB.query(mQuery,null);
            System.out.println("Log 1: "+cursor.getString(cursor.getColumnIndex("image")));
            if (cursor.getCount()>0){
                if(cursor.getString(cursor.getColumnIndex("image")) == null) {
                    imageURL = "";
                }else {
                    imageURL = cursor.getString(cursor.getColumnIndex("image"));
                }
            }
        }
        if (imageURL.contains("https://image.tmdb.org/t/p/w185")) {

        }else {
            imageURL = link + imageURL;
        }
        Picasso.get()
                .load(imageURL)
                .fit()
                .centerInside()
                .into(movieViewHolder.listItemImageView);

    }

    public void setmMovieList(ArrayList<Movie> movieList){
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

  public class MovieViewHolder extends RecyclerView.ViewHolder {
       ImageView listItemImageView;


       public MovieViewHolder(View imageView){
            super(imageView);
            listItemImageView = imageView.findViewById(R.id.iv_image_item_list);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }


                }
            });




      }
  }
}
