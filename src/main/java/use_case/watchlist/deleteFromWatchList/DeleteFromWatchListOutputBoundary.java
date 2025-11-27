package use_case.watchlist.deleteFromWatchList;

import use_case.watchlist.addToWatchList.AddToWatchListOutputData;

public interface DeleteFromWatchListOutputBoundary {
    void presentSuccess(DeleteFromWatchListOutputData outputData);
    void presentError(String error);
}
