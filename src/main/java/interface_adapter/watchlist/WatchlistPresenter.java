package interface_adapter.watchlist;

import interface_adapter.ViewManagerModel;
import use_case.watchlist.WatchlistOutputBoundary;
import use_case.watchlist.WatchlistOutputData;

/**
 * Presenter for the Watchlist use case.
 * Implements WatchlistOutputBoundary to receive results from the Interactor.
 * Directly updates the WatchlistViewModel, which the WatchlistView is observing.
 * * NOTE: This version is simplified to work with a WatchlistViewModel that holds data directly,
 * without using a separate WatchlistState class.
 */
public class WatchlistPresenter implements WatchlistOutputBoundary {
    private final WatchlistViewModel watchlistViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructor
     * @param watchlistViewModel The ViewModel holding the state for the Watchlist View.
     * @param viewManagerModel Manages which view is currently active.
     */
    public WatchlistPresenter(WatchlistViewModel watchlistViewModel,
                              ViewManagerModel viewManagerModel) {
        this.watchlistViewModel = watchlistViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    // --- Success Case ---

    /**
     * Receives the successful output data (the list of movies) from the Interactor,
     * updates the ViewModel, and notifies the View.
     * @param outputData The data (list of Media objects and username) returned by the Interactor.
     */
    @Override
    public void presentWatchlist(WatchlistOutputData outputData) {

        // 1. Format the data and update the ViewModel directly (Presentation Logic)

        // Set the successfully loaded data
        this.watchlistViewModel.setWatchlist(outputData.getMovies());
        this.watchlistViewModel.setUsername(outputData.getUsername());

        // Clear any previous error message
        this.watchlistViewModel.setErrorMessage(null);

        // 2. Notify listeners (the View) that the data has changed
        this.watchlistViewModel.firePropertyChanged();

        // 3. Switch to the Watchlist View, ensuring it is displayed
        this.viewManagerModel.setActiveView(watchlistViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

        System.out.println("Presenter: Successfully set new watchlist data and signaled view update.");
    }

    // --- Failure Case ---

    /**
     * Receives an error message from the Interactor, updates the ViewModel, and notifies the View.
     * @param error The error message string.
     */
    @Override
    public void presentError(String error) {

        // 1. Update the ViewModel directly with the error message
        this.watchlistViewModel.setErrorMessage(error);

        // 2. Notify listeners (the View) to display the error
        this.watchlistViewModel.firePropertyChanged();

        System.err.println("Presenter: Signaled view update with error: " + error);
    }
}