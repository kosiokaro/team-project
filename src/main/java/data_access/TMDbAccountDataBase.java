package data_access;

import org.json.JSONException;
import entity.Media;
import entity.User;
import entity.TVShow;
import entity.Watchlist;


public interface TMDbAccountDataBase {
    //   Adds media to watchlist
    boolean addToWatchList(Media media, Watchlist watchlist) throws JSONException;

    //    Removes media from watchlist
    boolean removeFromWatchList(Media media, Watchlist watchlist, User user) throws JSONException;

    //  Gets all media in watchlist
    Media[] getWatchlistMovies() throws JSONException;

    //  Gets all media in favorites

    Media[] getFavorites() throws JSONException;

    //  Adds to favorites
    boolean addToFavorites(Media media) throws JSONException;

    //  Removes from favorites
    boolean removeFromFavorites(Media media) throws JSONException;

    //  Get rating
    int getRating(Media media) throws JSONExceptionl;

}