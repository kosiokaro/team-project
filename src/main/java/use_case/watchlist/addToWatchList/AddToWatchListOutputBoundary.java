package use_case.watchlist.addToWatchList;

public interface AddToWatchListOutputBoundary {
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
