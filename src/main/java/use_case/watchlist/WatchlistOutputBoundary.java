package use_case.watchlist;

/**
 * Output boundary for Watchlist use case
 * Defines how results are presented to the user
 */
public interface WatchlistOutputBoundary {
    /**
     * Present the watchlist data
     * @param outputData The data to present
     */
    void presentWatchlist(WatchlistOutputData outputData);

    /**
     * Present an error message
     * @param error The error message
     */
    void presentError(String error);
}
