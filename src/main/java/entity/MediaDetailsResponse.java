package entity;

import java.util.List;

public class MediaDetailsResponse {
    public String title;
    public String overview;
    public String language;
    public String posterUrl;
    public int releaseYear;
    public double rating;
    public List<String> genres;
    public MediaDetailsResponse(String title, int releaseYear, String language,
                                double rating, List<String> genres, String overview,
                                String posterUrl) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.language = language;
        this.rating = rating;
        this.genres = genres;
        this.overview = overview;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }


    public String getLanguage() {
        return language;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}