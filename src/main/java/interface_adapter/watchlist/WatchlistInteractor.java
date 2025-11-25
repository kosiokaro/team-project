package interface_adapter.watchlist;

import data_access.BrowseDataAccess;
import data_access.FileUserDataAccessObject;
import entity.Media;
import use_case.watchlist.WatchlistInputBoundary; // <-- Interface from use_case layer
import use_case.watchlist.WatchlistOutputBoundary; // <-- Interface from use_case layer
import use_case.watchlist.WatchlistOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for Watchlist use case
 * Handles business logic for loading and managing user's watchlist
 */
public class WatchlistInteractor implements WatchlistInputBoundary {
    private final FileUserDataAccessObject userDataAccess;
    private final WatchlistOutputBoundary presenter;
    private final BrowseDataAccess browseDataAccess;

    /**
     * Constructor
     * @param userDataAccess DAO for user data (reading/writing JSON files)
     * @param presenter Presenter to format and display data
     * @param browseDataAccess DAO for fetching movie details from API
     */
    public WatchlistInteractor(FileUserDataAccessObject userDataAccess,
                               WatchlistOutputBoundary presenter,
                               BrowseDataAccess browseDataAccess) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
        this.browseDataAccess = browseDataAccess;
    }

    /**
     * Load a user's watchlist
     * 1. Gets list of movie IDs from user's JSON file
     * 2. Fetches full movie details for each ID
     * 3. Sends data to presenter to display
     */
    @Override
    public void loadWatchlist(String username) {
        try {
            // Step 1: Get the list of movie IDs from the user's watchlist
            // Assuming FileUserDataAccessObject.getWatchlist returns List<Integer>
            List<Integer> movieIds = userDataAccess.getWatchlist(username);

            // Step 2: Fetch the full movie data for each ID
//            List<Media> movies = new ArrayList<>();
//            for (Integer movieId : movieIds) {
//                Media movie = browseDataAccess.getMovieById(movieId); // <-- Use injected DAO
//                if (movie != null) {
//                    movies.add(movie);
//                }
//            }

            // Step 3: Send to presenter
            // NOTE: WatchlistOutputData expects List<Movie>, but Interactor uses List<Media>.
            // Assuming Movie extends Media or the types are compatible.
            WatchlistOutputData outputData = new WatchlistOutputData((List)movies, username);
            presenter.presentWatchlist(outputData);

        } catch (Exception e) {
            presenter.presentError("Failed to load watchlist: " + e.getMessage());
        }
    }

    /**
     * Remove a movie from the user's watchlist
     * 1. Fetches movie details
     * 2. Removes from user's watchlist
     * 3. Reloads the entire watchlist to refresh the view
     */
    @Override
    public void removeFromWatchlist(String username, int movieId) {
        try {
            // Step 1: Get the movie object
            // FIX: Use the injected movieDataAccess field, not a static call
            Media movie = browseDataAccess.getMovieById(movieId);

            if (movie != null) {
                // Step 2: Remove from user's watchlist
                userDataAccess.deleteFromWatchlist(username, movie);

                // Step 3: Reload the watchlist to update the view
                loadWatchlist(username);
            } else {
                presenter.presentError("Could not remove movie: Movie not found");
            }

        } catch (Exception e) {
            presenter.presentError("Failed to remove from watchlist: " + e.getMessage());
        }
    }
}
