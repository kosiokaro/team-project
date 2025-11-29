package use_case.watchlist.loadWatchList;

public interface LoadWatchListOutputBoundaryData {
   void presentWatchlist(LoadWatchListOutputData outputData);
   void presentError(String error);
}
