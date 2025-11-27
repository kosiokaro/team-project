package interface_adapter.clicking;

import java.util.ArrayList;
import java.util.List;

public class ClickingState {

    private String title = "";
    private String overview = "";
    private String language = "";
    private String posterUrl = "";
    private int year = 0;
    private double rating = 0.0;
    private List<String> genres = new ArrayList<>();
    private String errorMessage = "";

    public String getTitle() {
        return title;
    }
    public String getOverview() {
        return overview;
    }
    public String getLanguage() {
        return language;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public int getYear() {
        return year;
    }
    public double getRating() {
        return rating;
    }
    public List<String> getGenres() {
        return genres;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setErrorMessage(String s) {
        this.errorMessage = s;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
