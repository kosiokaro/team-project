package interface_adapter.watchlist;

import interface_adapter.ViewModel;

public class DeleteFromWatchListViewModel extends ViewModel<String> {

    public DeleteFromWatchListViewModel() {
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
