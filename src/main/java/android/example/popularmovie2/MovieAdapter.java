package android.example.popularmovie2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovieList;
    private OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public MovieAdapter(ArrayList<Movie> movieList){

        mMovieList = movieList;
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
        Movie moviePoster = mMovieList.get(i);

        String imageURL =  moviePoster.getImage();


        Picasso.get()
                .load(imageURL)
                .fit()
                .centerInside()
                .into(movieViewHolder.listItemImageView);
        movieViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

  class MovieViewHolder extends RecyclerView.ViewHolder{
       ImageView listItemImageView;


       public MovieViewHolder(View imageView){
            super(imageView);
            listItemImageView = imageView.findViewById(R.id.iv_image_item_list);

        }


       void bind(int i) {
           Movie moviePoster = mMovieList.get(i);
           String imageURL =  moviePoster.getImage();
           moviePoster.setImage(imageURL);
        }
    }
}
