package interface_adapter.watchlist.load;

import interface_adapter.ViewModel;

public class LoadWatchListViewModel extends ViewModel<LoadWatchListState> {
    public LoadWatchListViewModel() {
        super("loadWatchlist");
        setState(new LoadWatchListState());
    }

    public LoadWatchListState getState() {
        return super.getState();
    }

    public void setState(LoadWatchListState state) {
        super.setState(state);
    }
}