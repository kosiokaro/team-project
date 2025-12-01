package use_case.watchlist.addToWatchList;
import java.util.ArrayList;
import java.util.List;

public interface AddToWatchListDataAccessInterface {
    /**
     * Finds the user and adds the given reference number to their watchlist.
     * @param username The username of the user.
     * @param refNumber The unique ID of the media item to add.
     */
    void addMovieToWatchlist(String username, int refNumber);


    ArrayList<Integer> getWatchlist(String username);
//    void updateWatchlist(String username, List<Integer> watchlist);
}
