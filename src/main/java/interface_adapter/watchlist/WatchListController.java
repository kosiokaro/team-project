package interface_adapter.watchlist;

import use_case.watchlist.addToWatchList.AddToWatchListInputData;
import use_case.watchlist.addToWatchList.AddToWatchListInputBoundaryData;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListInputData;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListInputBoundaryData;

public class WatchListController {

    private final AddToWatchListInputBoundaryData addToWatchListInteractor;
    private final DeleteFromWatchListInputBoundaryData deleteFromWatchListInteractor;

    public WatchListController(AddToWatchListInputBoundaryData addToWatchListInteractor,
                               DeleteFromWatchListInputBoundaryData deleteFromWatchListInteractor) {
        this.addToWatchListInteractor = addToWatchListInteractor;
        this.deleteFromWatchListInteractor = deleteFromWatchListInteractor;
    }

    public void addToWatchList(String username, String refNumber) {
        AddToWatchListInputData inputData = new AddToWatchListInputData();
        inputData.username = username;
        inputData.refNumber = Integer.parseInt(refNumber);
        addToWatchListInteractor.addMovieToWatchlist(inputData);
    }

    public void removeFromWatchList(String username, String refNumber) {
        DeleteFromWatchListInputData inputData = new DeleteFromWatchListInputData();
        inputData.username = username;
        inputData.refNumber = Integer.parseInt(refNumber);
        deleteFromWatchListInteractor.deleteFromWatchlist(inputData);
    }
}
