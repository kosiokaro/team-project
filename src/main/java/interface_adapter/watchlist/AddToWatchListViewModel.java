package interface_adapter.watchlist;

import interface_adapter.ViewModel;

public class AddToWatchListViewModel extends ViewModel<String> {

    public AddToWatchListViewModel() {
        super("addToWatchlist");
    }

    public String getMessage() {
        return getState();
    }

    public void setMessage(String message) {
        setState(message);
    }

    public void firePropertyChanged() {
        firePropertyChange();
    }
}