package android.example.popularmovie2;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.widget.CheckBox;

@Entity(tableName = "tableMovie")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieName;
    private String description;
    private String image;
    private String releaseDate;
    private String badkdropImage;
    private String vote;
    @ColumnInfo(name = "favorite_bool")
    private Boolean favorite;
    /**
     * No args constructor for use in serialization
     */



    @Ignore
    public Movie(String movieName, String description, String image, String releaseDate, String badkdropImage, String vote) {
        this.movieName = movieName;
        this.description = description;
        this.image = image;
        this.releaseDate = releaseDate;
        this.badkdropImage = badkdropImage;
        this.vote = vote;
    }
   /* @Ignore
    public MovieReview() {

    }
    @Ignore
    public MovieTrailer() {

    }*/
    public Movie(String movieName, String description, String image, String releaseDate, String badkdropImage, String vote, Boolean favorite) {
        this.movieName = movieName;
        this.description = description;
        this.image = image;
        this.releaseDate = releaseDate;
        this.badkdropImage = badkdropImage;
        this.vote = vote;
        this.favorite = favorite;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBadkdropImage() {
        return this.badkdropImage;
    }

    public void setBadkdropImage(String badkdropImage) {
        this.badkdropImage = badkdropImage;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

}
