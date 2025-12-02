package use_case.favorites.addToFavorites;

import java.util.ArrayList;

public interface AddToFavoritesDataAccessInterface {
    /**
     * Finds the user and adds the given reference number to their watchlist.
     * @param username The username of the user.
     * @param refNumber The unique ID of the media item to add.
     */

    void addMovieToFavorites(String username, int refNumber);
    /**
     * Finds the user and adds the given reference number to their watchlist.
     * @param username The username of the user.
     * @return return the favorites.
     */

    ArrayList<Integer> getFavorites(String username);
}
