package entity;

import java.util.Arrays;

public class Movie extends Media {
    public String overview;
    public double rating;
    public final String releaseDate;
    public final int runtime;


    public Movie(String title, int referenceNumber, String[] genreNames, String overview, double rating, String releaseDate, int runtime,String posterURL,String language) {
        super(title,referenceNumber, genreNames,posterURL,language);
        this.genreIDs = genreIDs;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.runtime = runtime;

    }

    public String[] getGenres() {
        return genreNames;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", referenceNumber=" + getReferenceNumber() +
                ", genreNames=" + Arrays.toString(genreNames) +
                ", genreIDs=" + Arrays.toString(genreIDs) +
                ", posterUrl='" + posterUrl + '\'' +
                ", language='" + language + '\'' +
                ", overview='" + overview + '\'' +
                ", rating=" + rating +
                ", releaseDate='" + releaseDate + '\'' +
                ", runtime=" + runtime +
                '}';
    }
}
