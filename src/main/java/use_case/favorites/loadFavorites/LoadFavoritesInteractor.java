package use_case.favorites.loadFavorites;

import entity.Movie;
import use_case.favorites.addToFavorites.AddToFavoritesDataAccessInterface;
import java.util.ArrayList;

public class LoadFavoritesInteractor implements LoadFavoritesInputBoundary{
    private final AddToFavoritesDataAccessInterface userDataAccess;
    private final LoadFavoritesDataAccessInterface movieDataAccess;
    private final LoadFavoritesOutputBoundary presenter;

    public LoadFavoritesInteractor(AddToFavoritesDataAccessInterface userDataAccess,
                                   LoadFavoritesDataAccessInterface movieDataAccess,
                                   LoadFavoritesOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.movieDataAccess = movieDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadFavorites(LoadFavoritesInputData inputData) {
        try {
            ArrayList<Integer> movieIds = userDataAccess.getFavorites(inputData.username);
            ArrayList<Movie> movies = new ArrayList<>();

            for (Integer movieId : movieIds) {
                Movie movie = movieDataAccess.getMovieById(movieId);
                if (movie != null) {
                    movies.add(movie);
                }
            }

            LoadFavoritesOutputData outputData = new LoadFavoritesOutputData(movies);
            presenter.presentFavorites(outputData);

        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
    }
}