package use_case.favorites.loadFavorites;

import entity.Movie;
import java.util.ArrayList;

public class LoadFavoritesOutputData {
    private final ArrayList<Movie> movies;

    public LoadFavoritesOutputData(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}