package use_case.watchlist.deleteFromWatchList;

public interface DeleteFromWatchListDataAccessInterface {
    void deleteFromWatchlist(String username, int refNumber);
}
