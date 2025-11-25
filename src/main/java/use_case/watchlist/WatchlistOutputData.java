package use_case.watchlist;

import entity.Movie;
import java.util.List;

/**
 * Output data for Watchlist use case
 * Contains the movies and username
 */
public class WatchlistOutputData {
    private final List<Movie> movies;
    private final String username;

    /**
     * Constructor
     * @param movies List of movies in the watchlist
     * @param username The username who owns this watchlist
     */
    public WatchlistOutputData(List<Movie> movies, String username) {
        this.movies = movies;
        this.username = username;
    }

    /**
     * Get the list of movies
     * @return List of Media objects
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Get the username
     * @return The username
     */
    public String getUsername() {
        return username;
    }
}
