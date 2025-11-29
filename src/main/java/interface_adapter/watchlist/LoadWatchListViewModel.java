package interface_adapter.watchlist;

import interface_adapter.ViewModel;
import entity.Movie;
import java.util.ArrayList;

public class LoadWatchListViewModel extends ViewModel<LoadWatchListState> {
    public LoadWatchListViewModel() {
        super("loadWatchlist");
        setState(new LoadWatchListState());
    }
}
