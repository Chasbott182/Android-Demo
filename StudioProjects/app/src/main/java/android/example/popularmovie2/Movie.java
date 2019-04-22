package android.example.popularmovie2;

public class Movie {

    private String movieName;
    private String description;
    private String image;
    private String releaseDate;
    private String badkdropImage;
    private String vote;
    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    public Movie(String mainName, String description, String image, String releaseDate, String badkdropImage, String vote) {
        this.movieName = mainName;
        this.description = description;
        this.image = image;
        this.releaseDate = releaseDate;
        this.badkdropImage = badkdropImage;
        this.vote = vote;
    }

    public String getMainName() {
        return movieName;
    }

    public void setMainName(String mainName) {
        this.movieName = mainName;
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

}
