package use_case.clicking;

import java.util.List;

public class ClickingOutputData {

    private final String title;
    private final String overview;
    private final String language;
    private final String posterUrl;
    private final int releaseYear;
    private final double rating;
    private final List<String> genres;

    public ClickingOutputData(String title, String overview, String language,
                              double rating, int releaseYear, String posterUrl, List<String> genres) {
        this.title = title;
        this.overview = overview;
        this.language = language;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.posterUrl = posterUrl;
        this.genres = genres;
    }

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

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getGenres() {
        return genres;
    }
}
