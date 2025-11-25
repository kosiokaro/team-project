package use_case.watchlist;


public interface WatchlistInputBoundary {
    /**
     * Load a user's watchlist
     * @param username The username whose watchlist to load
     */
    void loadWatchlist(String username);

    /**
     * Remove a movie from a user's watchlist
     * @param username The username whose watchlist to modify
     * @param movieId The ID of the movie to remove
     */
    void removeFromWatchlist(String username, int movieId);
}
