package interface_adapter.watchlist;

import interface_adapter.ViewModel;

public class AddToWatchListViewModel extends ViewModel<AddToWatchListState> {
    public AddToWatchListViewModel() {
        super("addToWatchlist");
        setState(new AddToWatchListState());
    }

    public AddToWatchListState getState() {
        return super.getState();
    }

    public void setState(AddToWatchListState state) {
        super.setState(state);
    }
}