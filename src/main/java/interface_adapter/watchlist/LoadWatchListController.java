package interface_adapter.watchlist;


import use_case.watchlist.loadWatchList.LoadWatchListInputData;
import use_case.watchlist.loadWatchList.LoadWatchListInputBoundaryData;

public class LoadWatchListController {

    private final LoadWatchListInputBoundaryData interactor;

    public LoadWatchListController(LoadWatchListInputBoundaryData interactor) {
        this.interactor = interactor;
    }

    public void loadWatchlist(String username) {
        LoadWatchListInputData inputData = new LoadWatchListInputData(username);
        interactor.loadWatchlist(inputData);
    }
}
