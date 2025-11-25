package interface_adapter.watchlist;

import use_case.watchlist.WatchlistInputBoundary;

public class WatchlistController {
    private final WatchlistInputBoundary watchlistInteractor;

    public WatchlistController(WatchlistInteractor watchlistInteractor) {
        this.watchlistInteractor = watchlistInteractor;
    }
    // Delegate the request to load the watchlist
    public void loadWatchlist(String username) {
        watchlistInteractor.loadWatchlist(username);
    }

    // Delegate the request to remove a movie
    public void removeFromWatchlist(String username, int movieId) {
        watchlistInteractor.removeFromWatchlist(username, movieId);
    }
}

