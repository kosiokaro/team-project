package use_case.watchlist.loadWatchList;

import entity.Movie;

public interface LoadWatchListDataAccessInterface {
    Movie getMovieById(int movieId);
}
