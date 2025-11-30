package use_case.watchlist.loadWatchList;

import entity.Movie;
import use_case.watchlist.addToWatchList.AddToWatchListDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

public class LoadWatchListInteractor implements LoadWatchListInputBoundaryData {
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
            System.out.println("=== LoadWatchListInteractor.loadWatchlist called ===");
            System.out.println("Username: " + inputData.username);

            ArrayList<Integer> movieIds = userDataAccess.getWatchlist(inputData.username);
            System.out.println("Fetched movie IDs from watchlist: " + movieIds);
            System.out.println("Total movies in watchlist: " + (movieIds != null ? movieIds.size() : 0));

            ArrayList<Movie> movies = new ArrayList<>();

            if (movieIds != null && !movieIds.isEmpty()) {
                for (Integer movieId : movieIds) {
                    System.out.println("  → Fetching movie data for ID: " + movieId);
                    Movie movie = movieDataAccess.getMovieById(movieId);

                    if (movie != null) {
                        System.out.println("    ✓ Movie loaded: " + movie.getTitle());
                        System.out.println("      - Poster: " + movie.posterUrl);
                        System.out.println("      - Genres: " + java.util.Arrays.toString(movie.getGenres()));
                        movies.add(movie);
                    } else {
                        System.err.println("    ✗ Failed to load movie ID: " + movieId);
                    }
                }
            } else {
                System.out.println("No movies in watchlist");
            }

            System.out.println("Total movies loaded: " + movies.size());
            LoadWatchListOutputData outputData = new LoadWatchListOutputData(movies);
            presenter.presentWatchlist(outputData);

        } catch (Exception e) {
            System.err.println("Error in LoadWatchListInteractor: " + e.getMessage());
            e.printStackTrace();
            presenter.presentError(e.getMessage());
        }
    }

}