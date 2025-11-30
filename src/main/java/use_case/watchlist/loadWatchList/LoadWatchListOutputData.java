package use_case.watchlist.loadWatchList;

import entity.Movie;
import java.util.ArrayList;

public class LoadWatchListOutputData {
    private final ArrayList<Movie> movies;

    public LoadWatchListOutputData(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
