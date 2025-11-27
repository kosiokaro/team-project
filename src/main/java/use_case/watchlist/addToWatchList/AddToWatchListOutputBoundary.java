package use_case.watchlist.addToWatchList;

public interface AddToWatchListOutputBoundary {
    void presentSuccess(AddToWatchListOutputData outputData);
    void presentError(String error);
}
