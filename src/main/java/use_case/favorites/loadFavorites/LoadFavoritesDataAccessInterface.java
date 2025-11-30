package use_case.favorites.loadFavorites;

import entity.Movie;

public interface LoadFavoritesDataAccessInterface {
    Movie getMovieById(int movieId);
}
