package use_case.watchlist.loadWatchList;

import entity.Movie;
import use_case.watchlist.addToWatchList.AddToWatchListDataAccessInterface;
import java.util.ArrayList;

public class LoadWatchListInteractor implements LoadWatchListInputBoundaryData{
    private final AddToWatchListDataAccessInterface userDataAccess;
    private final LoadWatchListDataAccessInterface movieDataAccess;
    private final LoadWatchListOutputBoundaryData presenter;

    public LoadWatchListInteractor(AddToWatchListDataAccessInterface userDataAccess,
                                   LoadWatchListDataAccessInterface movieDataAccess,
                                   LoadWatchListOutputBoundaryData presenter) {
        this.userDataAccess = userDataAccess;
        this.movieDataAccess = movieDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadWatchlist(LoadWatchListInputData inputData) {
        try {
            ArrayList<Integer> movieIds = userDataAccess.getWatchlist(inputData.username);
            ArrayList<Movie> movies = new ArrayList<>();

            for (Integer movieId : movieIds) {
                Movie movie = movieDataAccess.getMovieById(movieId);
                if (movie != null) {
                    movies.add(movie);
                }
            }

            LoadWatchListOutputData outputData = new LoadWatchListOutputData(movies);
            presenter.presentWatchlist(outputData);

        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
    }

}