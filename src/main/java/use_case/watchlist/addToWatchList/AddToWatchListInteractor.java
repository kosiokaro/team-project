package use_case.watchlist.addToWatchList;

public class AddToWatchListInteractor implements AddToWatchListInputBoundaryData {

    private final AddToWatchListDataAccessInterface dataAccessObject;
    private final AddToWatchListOutputBoundary presenter;

    public AddToWatchListInteractor(AddToWatchListDataAccessInterface dataAccessObject,
                                    AddToWatchListOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    public void addMovieToWatchlist(AddToWatchListInputData input) {
        try {
            System.out.println("=== AddToWatchListInteractor.addMovieToWatchlist called ===");
            System.out.println("Username: " + input.username);
            System.out.println("Movie ID: " + input.refNumber);

            String refNumber = input.refNumber;
            String username = input.username;

            int movieId = Integer.parseInt(refNumber);
            System.out.println("Parsed movie ID: " + movieId);

            // Add to watchlist
            this.dataAccessObject.addMovieToWatchlist(username, movieId);
            System.out.println("✓ Movie added to watchlist successfully");

            AddToWatchListOutputData outputData = new AddToWatchListOutputData(
                    username, movieId, "Movie added successfully");
            presenter.presentSuccess(outputData);

        } catch (NumberFormatException e) {
            System.err.println("✗ Invalid movie ID format: " + input.refNumber);
            presenter.presentError("Invalid movie ID: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("✗ Error adding movie to watchlist: " + e.getMessage());
            e.printStackTrace();
            presenter.presentError(e.getMessage());
        }
    }
}