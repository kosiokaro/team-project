package interface_adapter.watchlist;

import interface_adapter.ViewModel;
import entity.Movie; // Assuming Media is your entity for movies/shows

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for the Watchlist use case.
 * Holds the display data directly and provides the mechanism
 * for the Presenter to update the View.
 */
public class WatchlistViewModel extends ViewModel {
    // The name of the view for the ViewManagerModel
    public static final String VIEW_NAME = "WATCHLIST";


    private List<Movie> watchlist = new ArrayList<>();

    private String username = "";

    private String errorMessage = null;

    public WatchlistViewModel() {
        super(VIEW_NAME);
    }

    // --- Getters and Setters (Used by Presenter and View) ---

    public List<Movie> getWatchlist() {
        return watchlist;
    }

    // Used by the Presenter
    public void setWatchlist(List<Movie> watchlist) {
        this.watchlist = watchlist;
    }

    public String getUsername() {
        return username;
    }

    // Used by the Presenter
    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Used by the Presenter
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // --- Property Change Mechanism (Essential for decoupling) ---

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Called by the Presenter when data is ready
    public void firePropertyChanged() {
        // Tells the WatchlistView (which is listening) to update itself.
        // We fire a change event for the entire ViewModel to signal a full refresh.
        support.firePropertyChange("watchlist_update", null, this);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
